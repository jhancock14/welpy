package com.welpy;

import android.os.AsyncTask;

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

/**
 * Created by johhan on 3/2/15.
 */
class FetchDucksTask extends AsyncTask<Void, Void, List<RawDuckResponse.SearchResult>> {

    //    String searchUrl = "http://api.duckduckgo.com/?q=World+News&format=json&t=welpy";
    String searchUrl = "http://api.duckduckgo.com/?q=Doge&format=json&t=welpy";
    HttpGet get = new HttpGet(searchUrl);
    HttpClient httpClient = new DefaultHttpClient();
    HttpResponse httpResponse = null;
    String searchResult = null;
    private DuckListAdapter mAdapter;

    FetchDucksTask(DuckListAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    protected List<RawDuckResponse.SearchResult> doInBackground(Void... params) {
        ArrayList<RawDuckResponse.SearchResult> searchResults = new ArrayList<RawDuckResponse.SearchResult>();
        try {
            httpResponse = httpClient.execute(get);
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
            List<RawDuckResponse.SearchResult> relatedTopics = duck.relatedTopics;
            searchResults.addAll(relatedTopics);
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(List<RawDuckResponse.SearchResult> searchResults) {
        super.onPostExecute(searchResults);
        mAdapter.addAll(searchResults);
        mAdapter.addAll(searchResults);
        mAdapter.addAll(searchResults);
    }
}
