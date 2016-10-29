package cheetatech.ucropcustomui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by erkan on 29.10.2016.
 */

public class GalleryController {

    private ImageHub imageHub = null;
    private LinearLayout galleryLinearLayout = null;
    private Context context = null;

    public GalleryController(){}


    public GalleryController(Context context)
    {
        this.context = context;
    }
    public GalleryController(ImageHub imageHub)
    {
        this.imageHub = imageHub;

    }
    public GalleryController(Context context, ImageHub imageHub,LinearLayout linearLayout)
    {
        this.context = context;
        this.imageHub = imageHub;
        this.galleryLinearLayout = linearLayout;

    }
    public GalleryController(ImageHub imageHub,LinearLayout linearLayout)
    {
        this.imageHub = imageHub;
        this.galleryLinearLayout = linearLayout;

    }

    public void loadImages()
    {
        ArrayList<Integer> list = imageHub.getImageList();
        for (int image: list
             ) {
            this.galleryLinearLayout.addView(insertImage(image));
        }
    }

    private View insertImage(int image)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(),image);
        if(bitmap == null)
            return null;

        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(250,250));
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(220,220));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bitmap);

        linearLayout.addView(imageView);
        return linearLayout;
    }

    private void setGalleryLinearLayout(LinearLayout linearLayout)
    {
        this.galleryLinearLayout = linearLayout;
    }
    public LinearLayout getGalleryLinearLayout()
    {
        return this.galleryLinearLayout;
    }



}
