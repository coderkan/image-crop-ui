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

public class MyWebAdapter extends RecyclerView.Adapter<MyWebAdapter.ViewHolder>{


    private ArrayList<File> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            imageView = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, File item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }


    public MyWebAdapter (ArrayList<File> myDataset){
        this.mDataset = myDataset;
    }



    @Override
    public MyWebAdapter .ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(MyWebAdapter.ViewHolder holder, int position) {
        final String name = mDataset.get(position).getName();
        holder.txtHeader.setText(mDataset.get(position).getName());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });

        if(context != null)
            Picasso.with(context).load(mDataset.get(position)).into(holder.imageView);
        //Glide.with(context).load(mDataset.get(position)).into(holder.imageView);

        holder.txtFooter.setText("Footer: " + mDataset.get(position).getAbsolutePath());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
