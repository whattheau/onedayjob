package com.final254.onedayjob_4present;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;


//class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;
//public String execute;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    public void getImage() {
// TODO Auto-generated method stub

    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

    public void execute(TextView tvImage) {
// TODO Auto-generated method stub

    }

}
