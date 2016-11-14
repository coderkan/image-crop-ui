package cheetatech.ucropcustomui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import cheetatech.ucropcustomui.R;

/**
 * Created by erkan on 11.11.2016.
 */

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.ViewHolder>{


    private ArrayList<String> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image_card_view);
        }
    }

    public void add(int position, String item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }


    public MyCardAdapter(ArrayList<String> myDataset){
        this.mDataset = myDataset;
    }



    @Override
    public MyCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyCardAdapter.ViewHolder holder, int position) {
        final String name = mDataset.get(position);

        if(context != null)
            Picasso.with(context).load(mDataset.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
