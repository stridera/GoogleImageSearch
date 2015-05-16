package com.stridera.googleimagesearch.GoogleImageSearchModel.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoogleImageSearchResults {
    // Text Details
    public String title;
    public String snippet;
    public String contextLink;
    // Image Properties
    public String imageUrl;
    public int imageWidth;
    public int imageHeight;
    // Thumbnail Properties
    public String thumbnailUrl;
    public int thumbnailWidth;
    public int thumbnailHeight;

    public static ArrayList<GoogleImageSearchResults> parseJSON(JSONObject response) throws JSONException {
        ArrayList<GoogleImageSearchResults> results = new ArrayList<>();

        JSONArray itemsJSON = response.optJSONArray("items");
        if (itemsJSON != null) {
            for (int i = 0; i < itemsJSON.length(); i++) {
                JSONObject imageJSON = itemsJSON.getJSONObject(i);
                GoogleImageSearchResults image = new GoogleImageSearchResults();
                image.title = imageJSON.getString("htmlTitle");
                image.snippet = imageJSON.getString("snippet");
                image.imageUrl = imageJSON.getString("link");
                image.imageWidth = imageJSON.getJSONObject("image").getInt("width");
                image.imageHeight = imageJSON.getJSONObject("image").getInt("height");
                image.contextLink = imageJSON.getJSONObject("image").getString("contextLink");
                image.thumbnailUrl = imageJSON.getJSONObject("image").getString("thumbnailLink");
                image.thumbnailWidth = imageJSON.getJSONObject("image").getInt("thumbnailWidth");
                image.thumbnailHeight = imageJSON.getJSONObject("image").getInt("thumbnailHeight");
                results.add(image);
            }
        }

        return results;
    }
}
