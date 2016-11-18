package cheetatech.ucropcustomui;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.backgroundactivity.BackView;
import cheetatech.ucropcustomui.backgroundactivity.BackgroundPresenter;
import cheetatech.ucropcustomui.backgroundactivity.ImageModel;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.gallery.GalleryController;

public class ChangeBackground extends BaseActivity implements View.OnClickListener , BackView {



    private String cubeString = Side.CUBE1;

    @BindView(R.id.iconOk) ImageView iconOk;
    @BindView(R.id.fabCamera) ImageView fabCamera;
    @BindView(R.id.fabGallery) ImageView fabGallery;
    @BindView(R.id.backgroundImView) ImageView backgroundImageView;
    @BindView(R.id.galleryLayout) LinearLayout galleryLayout;
    @BindView(R.id.fabApply)
    FloatingActionButton fabApply;



    private static final String TAG = "ChangeBackgroun";

    private GalleryController controller = null;
    private ArrayList<Integer> idList = new ArrayList<Integer>();

    private Uri mUri = null;
    public static String cubeBackgroundPath = "cube_background.jpg";




    private static final int DOWNLOAD_NOTIFICATION_ID_DONE = 911;




    private BackgroundPresenter presenter = null;
    private ArrayList<ImageModel> models = new ArrayList<ImageModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(presenter == null){
            presenter = new BackgroundPresenter(getApplicationContext(),this,Side.CUBE1);
        }

        loadElements();

    }
    private void loadElements()
    {
        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.e(TAG,"LoadElements");
        presenter.init();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        presenter.loadBackground();
    }


    @OnClick(R.id.fabGallery) void openGallery(){
        pickFromGallery();
    }
    @OnClick(R.id.fabCamera) void openCamera(){
        pickFromCamera();
    }
    @OnClick(R.id.iconOk) void saveImages(){
        saveImage();
        onBackPressed();
    }
    @OnClick(R.id.fabApply)void saveImageWithFab(){
        saveImage();
        onBackPressed();
    }


    @Override
    public void onClick(View view) {
        for(int i=0; i<this.models.size(); i++) {
            if(view.getId() == this.models.get(i).getId()) {
                Toast.makeText(this, "Index "+(i+1), Toast.LENGTH_SHORT).show();
                presenter.setSelectedImageModel(this.models.get(i));
            }
        }
    }



    private void saveImage() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        }else{

            File selectedFile = presenter.getCurrentFile();
            if(selectedFile.exists()){
                File pictureFile = FileUtilz.getOutMediaFile( getApplicationContext(),FileUtilz.accomplish(this.cubeString,cubeBackgroundPath));
                if(pictureFile.exists()) {
                    try{
                        if(selectedFile.getAbsolutePath().toString() == pictureFile.getAbsolutePath().toString()){
                            Log.e(TAG,"Dosyalar eşit");
                            return;
                        }
                        Log.e(TAG,"DOSYALAR1 "+ selectedFile.getAbsolutePath());
                        Log.e(TAG,"DOSYALAR2 "+ pictureFile.getAbsolutePath());
                        final FileOutputStream fos = new FileOutputStream(pictureFile);
                        Picasso.with(getApplicationContext()).load(selectedFile).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                bitmap.compress(Bitmap.CompressFormat.JPEG,90,fos);
                                try {
                                    fos.flush();
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }catch (IOException e){
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    //
    private void pickFromGallery()
    {
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ){
            requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE,getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }else{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent,getString(R.string.label_select_picture)),REQUEST_SELECT_PICTURE);
        }
    }


    private void pickFromCamera()
    {

        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ){
            requestPermission(android.Manifest.permission.CAMERA,getString(R.string.permission_camera_rationale),
                    REQUEST_CAMERA_ACCESS_PERMISSION);

        }else{
            // Camera intent

            Intent takePictureIntent = new Intent();
            takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);


            File photoFile = null;
            try{
                photoFile = FileUtilz.createImageFile(getApplicationContext()); //createImageFile();
            }catch (IOException e){
                Toast.makeText(ChangeBackground.this, "ErrorCreateImageFile", Toast.LENGTH_SHORT).show();
            }

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                try {
                    if(photoFile != null) {
                        Uri photoUri = Uri.fromFile(photoFile); //FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        mUri = photoUri;
                        startActivityForResult(takePictureIntent, REQUEST_START_CAMERA_APP);
                    }

                }catch (Exception ex){
                    Toast.makeText(ChangeBackground.this, "PickImage " +ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "Hata : "+ ex.getMessage());
                }

            }
        }

    }

    private void startCropActivityFromCamera(/*@NonNull Uri uri*/) {
        Uri uri = mUri;
        Toast.makeText(ChangeBackground.this, "Uri Uri "+uri.toString(), Toast.LENGTH_SHORT).show();
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));



        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);

        uCrop.start(ChangeBackground.this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickFromGallery();
                }
                break;
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveImage();

                }
                break;

            case REQUEST_CAMERA_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickFromCamera();
                }
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_SELECT_PICTURE){
                final Uri selectedUri = data.getData();
                if(selectedUri!= null) {
                    startCropActivity(data.getData());
                }else {
                    Toast.makeText(ChangeBackground.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }

            }else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }else if(requestCode == REQUEST_START_CAMERA_APP){
                // save images
                try {
                    startCropActivityFromCamera();
                }catch (Exception ex){
                    Log.e("TAG","Cannot Start CropActivity");
                    Toast.makeText(ChangeBackground.this, "Exception Error "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }

    }

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_BACKGROUND_IMAGE_NAME;
        destinationFileName += ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);

        uCrop.start(ChangeBackground.this);

    }

    /**
     *
     * Aspect Ratio is square
     * */
    private UCrop basisConfig(@NonNull UCrop uCrop) {

        uCrop = uCrop.withAspectRatio(9, 16);
        return uCrop;
    }

    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);

        options.withMaxResultSize(1080,1920);
        return uCrop.withOptions(options);
    }
    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            saveCroppedImage(resultUri);
        } else {
            Toast.makeText(ChangeBackground.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }




    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(ChangeBackground.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ChangeBackground.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCroppedImage(Uri uri) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            Uri imageUri = uri;//getIntent().getData();
            if (imageUri != null && imageUri.getScheme().equals("file")) {
                try {
                    copyFileToDownloads(imageUri);
                } catch (Exception e) {
                    Toast.makeText(ChangeBackground.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, imageUri.toString(), e);
                }
            } else {
                Toast.makeText(ChangeBackground.this, getString(R.string.toast_unexpected_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void copyFileToDownloads(Uri croppedFileUri) throws Exception {
        String npath = FileUtilz.accomplish(this.cubeString,Side.REF_BACKGROUND);
        File saveFile = FileUtilz.getOutMediaFile(getApplicationContext(), npath);

        FileInputStream inStream = new FileInputStream(new File(croppedFileUri.getPath()));
        FileOutputStream outStream = new FileOutputStream(saveFile);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();

        File file = new File(croppedFileUri.getPath());
        if(file != null)
            file.delete();
        presenter.setCurrentFile(saveFile);
        presenter.loadBackground();
    }



    @Override
    public void onLoadGalleryViews(ArrayList<ImageModel> models) {
        for (ImageModel model:models
             ) {
            this.galleryLayout.addView(model.getView());
        }
    }

    @Override
    public void onSetClickListeners(ArrayList<ImageModel> models) {
        this.models.clear();
        for (ImageModel model: models
             ) {
            this.models.add(model);
            ((ImageView)findViewById(model.getId())).setOnClickListener(this);
        }
    }

    @Override
    public void onLoadBackgroundImage(Bitmap bitmap) {
        backgroundImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onLoadBackgroundImage(File file) {
        Picasso.with(getApplicationContext()).load(file).into(backgroundImageView);
    }
}
