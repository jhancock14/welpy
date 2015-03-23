package com.welpy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
* Created by johhan on 3/2/15.
*/
public class RawDuckResponse {

    List<SearchResult> relatedTopics;

    static class SearchResult {
        String firstURL;
        Icon icon;
        String result;
        String text;

        String name;
        List<SearchResult> topics;
    }

    static class Icon {
        String height, width;
        @SerializedName("URL")
        String url;
    }
}
