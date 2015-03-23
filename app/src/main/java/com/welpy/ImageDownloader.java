package com.welpy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by johhan on 3/2/15.
 */
class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final ImageView imageView;

    public ImageDownloader(ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("ImageView can't be null!");
        }
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... imageSrc) {

        String urlDisplay = imageSrc[0];
        Bitmap myBitmap = null;
        InputStream input = null;
        if (!urlDisplay.isEmpty()) {
            try {
                input = new URL(urlDisplay).openStream();
            } catch (MalformedURLException e) {
                Log.e("ImageDownloader", String.format("MalformedURLException for url: \"%s\"", urlDisplay));
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myBitmap = BitmapFactory.decodeStream(input);
        return myBitmap;
    }
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
