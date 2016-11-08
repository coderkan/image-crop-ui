package cheetatech.ucropcustomui.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cheetatech.ucropcustomui.ChangeBackground;
import cheetatech.ucropcustomui.fileutil.FileUtilz;

/**
 * Created by erkan on 31.10.2016.
 */

public class ImageController {




    private Context context = null;
    private ImageView backgroundImageView = null;
    private ImageView[] cubeSideImageViews = new ImageView[6];

    private Bitmap currentBitmap = null;

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

    public Bitmap getBackgroundBitmap(){
        File pictureFile = FileUtilz.getOutputMediaFile(context, ChangeBackground.cubeBackgroundPath);

        if(pictureFile == null)
            Log.e("TAG","Error Load BackgroundImage : NULL");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
        return bitmap;
    }

    public void addCubeSideImageViews(ImageView[] imageViews)
    {
        int i = 0;
        for (ImageView im: imageViews
             ) {
            this.cubeSideImageViews[i++] = im;
        }
    }
    public void addCubeSideImageViews(int index, ImageView imageView)
    {
        if(index < this.cubeSideImageViews.length && index >= 0){
            this.cubeSideImageViews[index] = imageView;
        }
    }


    public void loadBitmap(ImageView imageView,int side)
    {
        int len = Side.cubeSidePath.length;
        if( !(side < len && side >= 0))
            return;
        String path = Side.cubeSidePath[side];
        File pictureFile = FileUtilz.getOutputMediaFile(context, path);

        if(pictureFile == null)
            Log.e("TAG","Error Load BackgroundImage : NULL");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
        imageView.setImageBitmap(bitmap);
    }
    public void loadBitmap(ImageView imageView,Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public void loadBitmap(ImageView imageView, Uri uri)
    {
        try{
            Log.e("TAG","In Function " );
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){
            Log.e("TAG","Error "+ e.getMessage());
        }

    }

    public void loadBitmapCubeSideFromPicture(int side)
    {
        int len = Side.cubeSidePath.length;
        if( !(side < len && side >= 0))
            return;
        String path = Side.cubeSidePath[side];
        File pictureFile = FileUtilz.getOutputMediaFile(context, path);

        if(pictureFile == null)
            Log.e("TAG","Error Load BackgroundImage : NULL");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
        this.cubeSideImageViews[side].setImageBitmap(bitmap);
    }

    public void loadAllBitmapCubeSideFromPicture()
    {

        for(int i = 0; i < 6; i++){
            String path = Side.cubeSidePath[i];
            File pictureFile = FileUtilz.getOutputMediaFile(context, path);

            if(pictureFile == null)
                Log.e("TAG","Error Load BackgroundImage : NULL");

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
            this.cubeSideImageViews[i].setImageBitmap(bitmap);
        }

    }

    public void saveCubeSidesImage()
    {
        for(int i = 0; i < 6;i++)
        {
            String path = Side.cubeSidePath[i];
            ImageView imageView = this.cubeSideImageViews[i];

            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

            File pictureFile = FileUtilz.getOutputMediaFile(this.context.getApplicationContext(),path);
            if(pictureFile == null)
            {
                Log.e("TAG","Error creating media file , check permissions...");
                return;
            }
            try{
                FileOutputStream fos = new FileOutputStream(pictureFile);
                bitmap.compress(Bitmap.CompressFormat.PNG,90,fos);
                fos.flush();
                fos.close();
            }catch (FileNotFoundException e){
                Log.e("TAG","Error File not found " + e.getMessage());
            }catch (IOException e){
                Log.e("TAG","Error accessing file "+ e.getMessage());
            }

            Log.e("TAG","Path is "+ pictureFile.toString());
        }



    }

    public void setCurrentBitmap(Bitmap bitmap){
        this.currentBitmap = bitmap;
    }
    public Bitmap getCurrentBitmap(){
        return this.currentBitmap;
    }



}
