package cheetatech.ucropcustomui.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yalantis.ucrop.task.BitmapCropTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cheetatech.ucropcustomui.ChangeBackground;
import cheetatech.ucropcustomui.R;
import cheetatech.ucropcustomui.backgroundactivity.ImageModel;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.gallery.ViewIdGenerator;

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


    public void save(File pictureFile,Bitmap bitmap)
    {
            //File pictureFile = FileUtilz.getOutputMediaFile(this.context.getApplicationContext(),path);
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

    public Bitmap getBackgroundBitmap()
    {
        File pictureFile = FileUtilz.getOutMediaFile(context, ChangeBackground.cubeBackgroundPath);
        if(!pictureFile.exists()){
            save(pictureFile,BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im5));
        }
        pictureFile = FileUtilz.getOutMediaFile(context, ChangeBackground.cubeBackgroundPath);

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if(!pictureFile.exists())
        {
            Log.e("TAG","Error Load BackgroundImage : NULL");
            bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im5);

        }else{
            bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
        }
        return bitmap;
    }
    // MainActivity
    public Bitmap[] getBitmapsCubeSidesFromPicture()
    {
        Bitmap[] bitmaps = new Bitmap[6];
        for(int i = 0; i < 6; i++){
            String path = Side.cubeSidePath[i];
            int[] rand = new int[]{R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,R.drawable.im6};
            File pictureFile = FileUtilz.getOutMediaFile(context, path);
            if(!pictureFile.exists())
                save(pictureFile,BitmapFactory.decodeResource(this.context.getResources(), rand[i++%6]));

            pictureFile = FileUtilz.getOutMediaFile(context,path);
            Bitmap bitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            if(!pictureFile.exists())
            {
                bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im3);
                Log.e("TAG","Error Load CubeSides : NULL");
            }else{
                bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
            }
            bitmaps[i] = bitmap;
        }
        return bitmaps;
    }


    // GalleryFiles are Ok
    public Bitmap[] getAllGalleryFile()
    {
        Bitmap[] bitmaps = new Bitmap[10];
        for(int i = 0; i < 10; i++){
            String path = Side.cubeSidePath[0];
            String refpath = "reference_image_"+i+".png";
            File pictureFile = FileUtilz.getOutMediaFile(context, refpath);

            if(!pictureFile.exists())
            {
                int[] ints = new int[]{R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,R.drawable.im6};
                Bitmap bitmap_ = BitmapFactory.decodeResource(this.context.getResources(), ints[i%6]);
                save(pictureFile,bitmap_);
            }

            pictureFile = FileUtilz.getOutMediaFile(context, refpath);

            Bitmap bitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            if(!pictureFile.exists())
            {
                int[] ints = new int[]{R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,R.drawable.im6};
                bitmap = BitmapFactory.decodeResource(this.context.getResources(), ints[i%6]);
                Log.e("TAG","Error Load CubeSides : NULL");
            }else{
                bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
            }
            bitmaps[i] = bitmap;
        }
        return bitmaps;
    }

    public Bitmap[] getAllBitmapCubeSideFromPicture()
    {

        Bitmap[] bitmaps = new Bitmap[6];
        for(int i = 0; i < 6; i++){
            String path = Side.cubeSidePath[i];
            File pictureFile = FileUtilz.getOutputMediaFile(context, path);

            Bitmap bitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            if(pictureFile == null)
            {
                bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im2);
                Log.e("TAG","Error Load CubeSides : NULL");
            }else{
                bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
            }
            bitmaps[i] = bitmap;
        }
        return bitmaps;
    }



    public ImageModel getImageModel(Bitmap bitmap)
    {
        ImageModel imageModel = new ImageModel();
        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(250,250));
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(220,220));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bitmap);

        int uniqueId = ViewIdGenerator.generateViewId();
        imageView.setId(uniqueId);
        linearLayout.addView(imageView);

        imageModel.setIndex(ImageModel.counter++);
        imageModel.setId(uniqueId);
        imageModel.setBitmap(bitmap);
        imageModel.setView(linearLayout);

        Log.e("TAG","Id ler : " + uniqueId + " index : "+ ImageModel.counter);
        return imageModel;
    }



    public void setBackgroundImageView(ImageView imageView)
    {
        this.backgroundImageView = imageView;
    }
    public void loadBackgroundImage()
    {
        File pictureFile = FileUtilz.getOutputMediaFile(context, ChangeBackground.cubeBackgroundPath);

        Bitmap bitmap = null;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if(pictureFile == null)
        {
            bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im1);
            Log.e("TAG","Error Load BackgroundImage : NULL");
        }else{
            bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
        }
        this.backgroundImageView.setImageBitmap(bitmap);
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

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if(pictureFile == null)
        {
            bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im1);
            Log.e("TAG","Error Load BackgroundImage : NULL");
        }else{
            bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
        }
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

            Bitmap bitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            if(pictureFile == null)
            {
                bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.im2);
                Log.e("TAG","Error Load CubeSides : NULL");
            }else{
                bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),options);
            }
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


    public void saveCubeSidesImage(ImageView[] cubeSideImageViews)
    {
        for(int i = 0; i < 6;i++)
        {
            String path = Side.cubeSidePath[i];
            ImageView imageView = cubeSideImageViews[i];

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
