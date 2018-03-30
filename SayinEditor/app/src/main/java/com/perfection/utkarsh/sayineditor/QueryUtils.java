package com.perfection.utkarsh.sayineditor;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by utkarsh on 25/3/18.
 */

public class QueryUtils {

    public static ArrayList<Snippet> extractFeatureFromJson(String JsonString) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JsonString)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Snippet> snippets = new ArrayList<Snippet>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(JsonString);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray snippetArray = baseJsonResponse.getJSONArray("snippet");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < snippetArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentSnippet = snippetArray.getJSONObject(i);

                // Extract the value for the key called "place"
                String voice = currentSnippet.getString("code");

                // Extract the value for the key called "url"
                String code = currentSnippet.getString("voice");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
                Snippet snippet = new Snippet(voice,code);
                snippets.add(snippet);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        return snippets;
    }
}
