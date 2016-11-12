package cheetatech.ucropcustomui.picasso;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cheetatech.ucropcustomui.R;

/**
 * Created by erkan on 12.11.2016.
 */

public class PicassoClient {

    public static void downloadImage(Context context, String url, ImageView imageView){
        if(url != null && url.length() > 0 )
        {
            Picasso.with(context).load(url).placeholder(R.drawable.bg1).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.bg1).into(imageView);
        }
    }
}
