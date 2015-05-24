package com.example.dat.camerauploader;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by DAT on 5/23/2015.
 */
public class CustomAdapterGridview extends BaseAdapter {
    private Context mContext;
    private int resource;
    private ArrayList<Animal> listAnimal = null;

    public CustomAdapterGridview(Context context, int resource, ArrayList<Animal> listAnimal) {
        this.resource = resource;
        this.mContext = context;
        this.listAnimal = listAnimal;
    }

    @Override
    public int getCount() {
        return listAnimal.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        Holder holder = null;
//            check to see if we have a view
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);

            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.textView = (TextView) convertView.findViewById(R.id.textView);


            convertView.setTag(holder);
        } else {
//                use the recycled view object
            holder = (Holder) convertView.getTag();
        }
        //Animal animal = listAnimal.get(position);
        holder.textView.setText(listAnimal.get(position).getPk() + "");
        Picasso.with(mContext)
                .load(listAnimal.get(position).getImageURL())
                .placeholder(R.drawable.placeholder).transform(new RoundedTransformation(10, 10))
                .noFade()
                .fit()
                .centerInside()
                .error(R.drawable.missing).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.imageView);


        return convertView;
    }

    static class Holder {
        ImageView imageView;
        TextView textView;
    }


}

