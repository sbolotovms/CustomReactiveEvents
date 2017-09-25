package com.cepgamer.testapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetWebImageTask extends AsyncTask<URL, Void, Bitmap> {
    private OnDownloadedCallback _onDownloadedCallback;

    public interface OnDownloadedCallback {
        void result(Bitmap result);
    }

    public GetWebImageTask(OnDownloadedCallback onDownloadedCallback) {

        _onDownloadedCallback = onDownloadedCallback;
    }

    @Override
    protected Bitmap doInBackground(URL... params) {
        if (params.length > 0) {
            URL url = params[0];
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            } catch (Exception e) {
                Log.e(GetWebImageTask.class.getName(), "Error getting image", e);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        _onDownloadedCallback.result(bitmap);
    }
}
