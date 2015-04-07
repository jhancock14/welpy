package com.welpy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
* Created by johhan on 3/2/15.
*/
class DuckListAdapter extends ArrayAdapter<RawDuckResponse.SearchResult> {

    DuckListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.row, null);

        RawDuckResponse.SearchResult searchResult = getItem(position);

        // ==========
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox_meat);
        checkBox.setText(searchResult.text);

        ImageView viewById = (ImageView) v.findViewById(R.id.search_icon_image);
        RawDuckResponse.Icon icon = searchResult.icon;

        if (icon != null) {
            String url = icon.url;
            new ImageDownloader(viewById).execute(url);
        }


        // =========
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String checkedState = isChecked ? "checked" : "unchecked";
                Toast.makeText(getContext(), "Box is " + checkedState, Toast.LENGTH_SHORT).show();
            }
        });

//      checkBox.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//          //TODO-XXX
//        }
//      });
        return v;
    }
}
