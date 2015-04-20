package com.welpy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPageActivity extends Activity {

    TextView detailTextView;
    ImageView detailImageView;
    TextView nameDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        final Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String text = intent.getStringExtra("text");
        String imageUrl = intent.getStringExtra("image");
        boolean isChecked = intent.getBooleanExtra("isChecked", false);
        final String id = intent.getStringExtra("id");

        nameDetailTextView = (TextView) findViewById(R.id.nametext);
        nameDetailTextView.setText(name);

        detailTextView = (TextView) findViewById(R.id.toptext);
        detailTextView.setText(text);

        detailImageView = (ImageView) findViewById(R.id.search_icon_image);
        new ImageDownloader(detailImageView).execute(imageUrl);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_meat);
        checkBox.setChecked(isChecked);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(DetailPageActivity.this);
                Intent updateIntent = new Intent("UPDATE_CHECK_STATE");
                updateIntent.putExtra("id", id);
                updateIntent.putExtra("isChecked", isChecked);

                lbm.sendBroadcast(updateIntent);
            }
        });
    }
}
