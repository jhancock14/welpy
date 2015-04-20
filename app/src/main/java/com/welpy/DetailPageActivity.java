package com.welpy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPageActivity extends Activity {

    TextView detailTextView;
    ImageView detailImageView;
    String url;
    TextView nameDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        Intent intent = getIntent();

        nameDetailTextView = (TextView) findViewById(R.id.nametext);
        nameDetailTextView.setText(intent.getStringExtra("name"));

        detailTextView = (TextView) findViewById(R.id.toptext);
        detailTextView.setText(intent.getStringExtra("text"));

        url = intent.getStringExtra("image");
        detailImageView = (ImageView) findViewById(R.id.search_icon_image);
        new ImageDownloader(detailImageView).execute(url);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_meat);
        boolean isChecked = intent.getBooleanExtra("isChecked", false);
        checkBox.setChecked(isChecked);
    }
}




