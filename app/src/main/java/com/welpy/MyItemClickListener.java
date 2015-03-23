package com.welpy;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by johhan on 3/23/15.
 */
public class MyItemClickListener implements AdapterView.OnItemClickListener {

    private Activity activity;

    public MyItemClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, DetailPageActivity.class);
        activity.startActivity(intent);
    }
}
