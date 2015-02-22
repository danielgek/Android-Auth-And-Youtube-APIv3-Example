package com.example.danielleal.androidauthexample;

/**
 * Created by danielgek on 22/02/15.
 */

import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;

public class Playlists {

    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    public static String apiKey = "AIzaSyA4DFS8YBl9EKu7nU3YeYMBW2MBncvqeD4";

    private static YouTube youtube;

    static {
        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("SoundSync").build();
    }

    public static PlaylistListResponse getPlaylist(String token) {

        try {

            YouTube.Playlists.List playlists = youtube.playlists()
                    .list("snippet")
                    .setOauthToken(token)
                    .setMine(true)
                    .setKey(apiKey);
            PlaylistListResponse response = playlists.execute();

            Log.e("asd",response.getItems().get(0).getSnippet().toString());
            YouTube.PlaylistItems.List plalistMusics = youtube.playlistItems()
                    .list("snippet")
                    .setOauthToken(token)
                    .setPlaylistId(response.getItems().get(0).getId());
            PlaylistItemListResponse responseMusics = plalistMusics.execute();
            Log.e("asd",responseMusics.getItems().get(0).getSnippet().getTitle());
            return playlists.execute();
        } catch (GoogleJsonResponseException e) {
            Log.d("asd","GoogleJsonResponseException e"+e);
        } catch (IOException e) {
            Log.d("asd","IOException e"+e);
        } catch (Throwable t) {
            Log.d("asd","Throwable t"+t);
        }

        return null;
    }
}