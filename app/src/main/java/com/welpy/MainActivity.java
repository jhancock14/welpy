package com.welpy;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

// http://api.duckduckgo.com/?q=World+News&format=json&t=welpy&pretty=1
// TODO

// checkbox
// potentially putting a click listener in the adapter

// item_view detail page

// switch over to tumblr api
// api key in email or setup dev account


public class MainActivity extends Activity {
    private DuckListAdapter mAdapter;

    /**
     * Called when the activity is first created.
     */
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
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Item clicked!", Toast.LENGTH_SHORT).show();
            }
        };

//        onItemClickListener = new MyItemClickListener(MainActivity.this);
listview_layout.setItemsCanFocus(false);
        listview_layout.setOnItemClickListener(onItemClickListener);

        new FetchDucksTask(mAdapter).execute();
    }



}
