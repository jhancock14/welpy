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

import java.util.List;

class DuckListAdapter extends ArrayAdapter<DuckListAdapter.StateContainer> {

    DuckListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: use view recycling and a view holder

        View v;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.row, null);
        final StateContainer item = getItem(position);
        RawDuckResponse.SearchResult searchResult = item.searchResult;

        // ==========
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox_meat);
        checkBox.setText(searchResult.text);
        checkBox.setChecked(item.isChecked);

        ImageView viewById = (ImageView) v.findViewById(R.id.search_icon_image);
        RawDuckResponse.Icon icon = searchResult.icon;

        if (icon != null) {
            new ImageDownloader(viewById).execute(icon.url);
        }

        // =========
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String checkedState = isChecked ? "checked" : "unchecked";
                Toast.makeText(getContext(), "Box is " + checkedState, Toast.LENGTH_SHORT).show();
                item.isChecked = isChecked;
            }
        });

        return v;
    }

    public void addSearchResults(List<RawDuckResponse.SearchResult> searchResults) {
        setNotifyOnChange(false);
        for (RawDuckResponse.SearchResult searchResult : searchResults) {
            add(new StateContainer(searchResult));
        }
        setNotifyOnChange(true);
        notifyDataSetChanged();
    }

    public static class StateContainer {
        final RawDuckResponse.SearchResult searchResult;
        Boolean isChecked = false;

        public StateContainer(RawDuckResponse.SearchResult searchResult) {
            this.searchResult = searchResult;
        }
    }
}
