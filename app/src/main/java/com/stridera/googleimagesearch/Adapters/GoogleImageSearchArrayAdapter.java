package com.stridera.googleimagesearch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.stridera.googleimagesearch.Activities.ImageDetailsActivity;
import com.stridera.googleimagesearch.GoogleImageSearchModel.Response.GoogleImageSearchResults;
import com.stridera.googleimagesearch.R;

import java.util.List;

public class GoogleImageSearchArrayAdapter extends ArrayAdapter {

    private static class ViewHolder {
        ImageView image;
    }

    public GoogleImageSearchArrayAdapter(Context context, List<GoogleImageSearchResults> objects) {
        super(context, R.layout.gis_item, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;
        final GoogleImageSearchResults item = (GoogleImageSearchResults) getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.gis_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.gis_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Lets load the thumbnail first.  This will show the image very quickly but at diminished quality
        Picasso.with(getContext())
                .load(item.thumbnailUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Now we load the full image, and display it when it's ready, overwriting the thumbnail.
                        Picasso.with(getContext())
                                .load(item.imageUrl)
                                .placeholder(viewHolder.image.getDrawable())
                                .into(viewHolder.image);
                    }

                    @Override
                    public void onError() {

                    }
                });


        viewHolder.image.setTag(item);
        viewHolder.image.setOnClickListener(ivImageSelected);

        return view;
    }

    private View.OnClickListener ivImageSelected = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GoogleImageSearchResults item = (GoogleImageSearchResults) v.getTag();
            Log.d("BLAH", String.format("Handle onclick. caption: %s  URL: %s", item.title, item.imageUrl));

            Context ctx = getContext();
            Intent i = new Intent(ctx, ImageDetailsActivity.class);

            i.putExtra("title", item.title);
            i.putExtra("url", item.imageUrl);
            i.putExtra("height", item.imageHeight);
            i.putExtra("width", item.imageWidth);
            i.putExtra("context_link", item.contextLink);
            i.putExtra("snippet", item.snippet);
            ctx.startActivity(i);
        }
    };
}
