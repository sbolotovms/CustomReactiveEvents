package com.cepgamer.testapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWebImageTask extends AsyncTask<URL, Void, Bitmap>
{
    private OnDownloadedCallback _onDownloadedCallback;

    public interface OnDownloadedCallback
    {
        void result(Bitmap result);
    }

    public GetWebImageTask(OnDownloadedCallback onDownloadedCallback)
    {

        _onDownloadedCallback = onDownloadedCallback;
    }

    @Override
    protected Bitmap doInBackground(URL... params)
    {
        if (params.length > 0)
        {
            URL url = params[0];
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try
            {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                inputStream = new BufferedInputStream(connection.getInputStream());
                return BitmapFactory.decodeStream(inputStream);

            } catch (Exception e)
            {
                Log.e(GetWebImageTask.class.getName(), "Error getting image", e);
            } finally
            {
                if (inputStream != null)
                {
                    try
                    {
                        inputStream.close();
                    } catch (final IOException e)
                    {
                        // do nothing
                    }
                }

                if (outputStream != null)
                {
                    try
                    {
                        inputStream.close();
                    } catch (final IOException e)
                    {
                        // do nothing
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        _onDownloadedCallback.result(bitmap);
    }
}
