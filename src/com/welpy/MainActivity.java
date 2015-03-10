package com.welpy;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE;

// TODO
// android studio
// include image in results


public class MainActivity extends ListActivity {
  private DuckListAdapter mAdapter;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mAdapter = new DuckListAdapter(this);
    setListAdapter(mAdapter);
    new FetchDucksTask().execute();
  }

  public static class RawDuckResponse {

    private List<SearchResult> relatedTopics;

    private static class SearchResult {
      String firstURL;
      Icon icon;
      String result;
      String text;

      String name;
      List<SearchResult> topics;
    }

    private static class Icon {
      String height, width;
      String url;
    }
  }

  class FetchDucksTask extends AsyncTask<Void, Void, List<RawDuckResponse.SearchResult>> {

    HttpClient http_client = new DefaultHttpClient();
    HttpGet get = new HttpGet("http://api.duckduckgo.com/?q=World+News&format=json&t=welpy");
    HttpResponse httpResponse = null;
    String searchResult = null;

    @Override
    protected List<RawDuckResponse.SearchResult> doInBackground(Void... params) {
      ArrayList<RawDuckResponse.SearchResult> searchResults = new ArrayList<RawDuckResponse.SearchResult>();
      try {
        httpResponse = http_client.execute(get);
      } catch (IOException e) {
        e.printStackTrace(); //TODO-XXX
      }

      if (httpResponse.getStatusLine().getStatusCode() == 200) {
        try {
          searchResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
          e.printStackTrace(); //TODO-XXX
        }
        Gson gson = new GsonBuilder().setFieldNamingPolicy(UPPER_CAMEL_CASE).create();
        RawDuckResponse duck = gson.fromJson(searchResult, RawDuckResponse.class);
//        searchResults.add(duck); // searchResults.add( requires SearchResult, not RawDuckResponse)
//        searchResults.add(duck.relatedTopics); // searchResults.add( requires SearchResult, not List<SearchResult>)
        List<RawDuckResponse.SearchResult> relatedTopics = duck.relatedTopics;

        RawDuckResponse.SearchResult singleResult = relatedTopics.get(0);
        searchResults.add(singleResult);
        searchResults.addAll(relatedTopics);
      }

      return searchResults;
    }

    @Override
    protected void onPostExecute(List<RawDuckResponse.SearchResult> ducks) {
      super.onPostExecute(ducks);
      mAdapter.addAll(ducks);
    }
  }

  private class DuckListAdapter extends ArrayAdapter<RawDuckResponse.SearchResult> {

    public DuckListAdapter(Context context) {
      super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View v = convertView;
      if (v == null) {
        LayoutInflater vi = (LayoutInflater) getSystemService
            (Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.main, null);
      }
      RawDuckResponse.SearchResult searchResult = getItem(position);
//      TextView termText = (TextView) v.findViewById(R.id.toptext);
//      termText.setText(o.text);

      CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox_meat);
      checkBox.setText(searchResult.text);

      checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          String checkedState = isChecked ? "checked" : "unchecked";
          Toast.makeText(MainActivity.this, "Box is " + checkedState, Toast.LENGTH_SHORT).show();
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
}

