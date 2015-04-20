package com.welpy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// http://api.duckduckgo.com/?q=World+News&format=json&t=welpy&pretty=1
// TODO
// checkbox
// potentially putting a click listener in the adapter
// item_view detail page - in progress
// switch over to tumblr api
// api key in email or setup dev account


public class MainActivity extends Activity {
    private DuckListAdapter mAdapter;
    private BroadcastReceiver checkUpdateReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DuckListAdapter(this);
        setContentView(R.layout.main);
        ListView listview_layout = (ListView) findViewById(R.id.listview);
        listview_layout.setAdapter(mAdapter);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailPageActivity.class);

                DuckListAdapter.StateContainer stateContainer = (DuckListAdapter.StateContainer) parent.getItemAtPosition(position);
                RawDuckResponse.SearchResult searchResult = stateContainer.searchResult;

                // our name detail
                TextView nameView = (TextView) view.findViewById(R.id.nametext);
                intent.putExtra("name", searchResult.name);

                // our text detail
                TextView textView = (TextView) view.findViewById(R.id.toptext);
                intent.putExtra("text", searchResult.text);

                // our icon detail
                ImageView imageView = (ImageView) view.findViewById(R.id.search_icon_image);
                intent.putExtra("image", searchResult.icon.url);

                intent.putExtra("isChecked", stateContainer.isChecked);
                intent.putExtra("id", searchResult.firstURL);

                startActivity(intent);
                Toast.makeText(MainActivity.this, "Item clicked!", Toast.LENGTH_SHORT).show();
            }
        };

        listview_layout.setOnItemClickListener(onItemClickListener);

        new FetchDucksTask(mAdapter).execute();

        checkUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isChecked = intent.getBooleanExtra("isChecked", false);
                String id = intent.getStringExtra("id");

                for (int i=0; i<mAdapter.getCount(); i++) {
                    DuckListAdapter.StateContainer item = mAdapter.getItem(i);
                    if (TextUtils.equals(item.searchResult.firstURL, id)) {
                        item.isChecked = isChecked;
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("UPDATE_CHECK_STATE");
        LocalBroadcastManager.getInstance(this).registerReceiver(checkUpdateReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(checkUpdateReceiver);
        checkUpdateReceiver = null;
    }
}
