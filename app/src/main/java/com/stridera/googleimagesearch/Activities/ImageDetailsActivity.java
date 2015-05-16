package com.stridera.googleimagesearch.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stridera.googleimagesearch.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageDetailsActivity extends ActionBarActivity {
    ImageView wvImage;
    TextView tvTitle;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        wvImage = (ImageView) findViewById(R.id.wvImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        String snippet = intent.getStringExtra("snippet");
        link = intent.getStringExtra("context_link");

        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(wvImage);

        tvTitle.setText(Html.fromHtml(title));
        setTitle(snippet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            onShareItem();
            return true;
        } else if (id == R.id.action_save) {
            onSaveItem();
            return true;
        } else if (id == R.id.action_open) {
            onOpenItem();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onOpenItem() {
        Log.d("blah", "Opening Link: " + link);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(link));
        startActivity(i);
    }

    public void onSaveItem() {
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(wvImage);
        if (bmpUri != null) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(bmpUri);
            sendBroadcast(mediaScanIntent);
            Toast.makeText(ImageDetailsActivity.this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } else {
            // Todo: Handle Error
        }
    }

    public void onShareItem() {
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(wvImage);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            // Todo: Handle Error
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
