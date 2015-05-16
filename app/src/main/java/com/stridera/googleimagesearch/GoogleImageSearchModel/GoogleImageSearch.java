package com.stridera.googleimagesearch.GoogleImageSearchModel;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.stridera.googleimagesearch.GoogleImageSearchModel.Filters.GoogleImageSearchFilters;
import com.stridera.googleimagesearch.GoogleImageSearchModel.Response.GoogleImageSearchResults;
import com.stridera.googleimagesearch.Adapters.GoogleImageSearchArrayAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class GoogleImageSearch {
    // Constant
    private static final String BASE_URL = "https://www.googleapis.com/customsearch/v1";
    private static final String API_KEY = "PUT_API_KEY_HERE";
    private static final String API_CX = "PUT_CX_HERE";

    // Private Vars
    private String query;
    private GoogleImageSearchArrayAdapter formArrayAdapter;
    private TextView noResultsMessage;
    private ProgressBar progressBar;

    // Public Vars
    public GoogleImageSearchFilters filters;
    public ArrayList<GoogleImageSearchResults> stream;


    // Constructors
    public GoogleImageSearch() {
        this("");
    }

    public GoogleImageSearch(String query) {
        this.query = query;
        this.filters = new GoogleImageSearchFilters();
        this.stream = new ArrayList<>();
    }

    // Setters
    public void setQuery(String query) {
        this.query = query;
    }

    // Member Functions
    private String generateQueryString() {
        return generateQueryString(0);
    }

    private String generateQueryString(int page) {
        String uri = "";
        String start = "";

        if (page > 0) {
            start = String.format("&start=%d", page * 10); // TODO: Make this use the nextpage value from the json
        }

        try {
            uri = String.format("%s?key=%s&cx=%s&searchType=image&q=%s%s%s",
                    BASE_URL,
                    API_KEY,
                    API_CX,
                    URLEncoder.encode(query, "UTF-8"),
                    start,
                    filters.getFilterParams()
            );
        } catch (UnsupportedEncodingException exception) {
            // TODO: Handle this
        }

        return uri;
    }

    public void do_search() {
        if (!query.isEmpty()) {
            formArrayAdapter.clear();
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
            stream.clear();
            this.do_search(0);
        }
    }


    public void do_search(int page) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        String url = generateQueryString(page);



        Log.d("blah", url);
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("blah", response.toString());
                    formArrayAdapter.addAll(GoogleImageSearchResults.parseJSON(response));
                    Log.d("blah", "Stream Size: " + stream.size());
                    formArrayAdapter.notifyDataSetChanged();

                    if (progressBar != null && progressBar.getVisibility() == View.VISIBLE)
                        progressBar.setVisibility(View.INVISIBLE);

                    if (noResultsMessage != null) {
                        if (stream.isEmpty()) {
                            noResultsMessage.setVisibility(View.VISIBLE);
                        } else {
                            noResultsMessage.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (JSONException ex) {
                    Log.d("GIS JSONException", "JSON Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //TODO: Handle This
                Log.d("blah", "Failed  Status: " + statusCode);
                if (errorResponse != JSONObject.NULL)
                    Log.d("blah", "Obj: " + errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("blah", "Failed  Status: " + statusCode);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("blah", "Failed  Status: " + statusCode);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void setAdapter(GoogleImageSearchArrayAdapter arrayAdapter) {
        formArrayAdapter = arrayAdapter;
    }

    public void setNoResults(TextView textView) {
        noResultsMessage = textView;
    }

    public void setFilters(GoogleImageSearchFilters filters) {
        this.filters = filters;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
