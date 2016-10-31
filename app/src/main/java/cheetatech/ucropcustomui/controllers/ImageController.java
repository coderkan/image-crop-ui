package cheetatech.ucropcustomui.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import cheetatech.ucropcustomui.ChangeBackground;
import cheetatech.ucropcustomui.fileutil.FileUtilz;

/**
 * Created by erkan on 31.10.2016.
 */

public class ImageController {

    private Context context = null;
    private ImageView backgroundImageView = null;
    public ImageController()
    {

    }

    public ImageController(Context context)
    {
        this.context = context;
    }

    public void setBackgroundImageView(ImageView imageView)
    {
        this.backgroundImageView = imageView;
    }
    public void loadBackgroundImage()
    {
        File pictureFile = FileUtilz.getOutputMediaFile(context, ChangeBackground.cubeBackgroundPath);

        if(pictureFile == null)
            Log.e("TAG","Error Load BackgroundImage : NULL");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
        this.backgroundImageView.setImageBitmap(bitmap);
    }

}
