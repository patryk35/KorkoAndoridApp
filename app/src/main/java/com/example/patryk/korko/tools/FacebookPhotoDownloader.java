package com.example.patryk.korko.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Patryk on 2017-05-09.
 */

public class FacebookPhotoDownloader extends AsyncTask<String, Void, Bitmap> {
    private ImageView image;

    public FacebookPhotoDownloader(ImageView image){
        this.image = image;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {
        String imageURL = "https://graph.facebook.com/" + URL[0] + "/picture?type=large";
        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        image.setImageBitmap(result);
    }
}
