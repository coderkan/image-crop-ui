package cheetatech.ucropcustomui;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.IntChange;
import cheetatech.ucropcustomui.controllers.onChangeBackground;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.gallery.GalleryController;
import cheetatech.ucropcustomui.gallery.ImageHub;

public class ChangeBackground extends BaseActivity implements View.OnClickListener, onChangeBackground{


    private static final String TAG = "ChangeBackgroun";

    private GalleryController controller = null;
    private ArrayList<Integer> idList = new ArrayList<Integer>();
    private ImageView backgroundImageView = null;

    private Uri mUri = null;
    private String mCurrentPhotoPath = null;
    public static String cubeBackgroundPath = "cube_background.png";
    private boolean isImage = false;

    private ImageController imageController = null;

    private static final int DOWNLOAD_NOTIFICATION_ID_DONE = 911;


    public static void startWithUri(@NonNull Context context, @NonNull Uri uri) {
        Intent intent = new Intent(context, ChangeBackground.class);
        intent.setData(uri);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        IntChange.getInstance().setListener(this);

        if(imageController == null)
            imageController = new ImageController(getApplicationContext());

        loadElements();
        loadGallery();

    }

    private void loadGallery()
    {
        int[] images = new int[]{
                R.drawable.im1,
                R.drawable.im2 ,
                R.drawable.im3,
                R.drawable.im4,
                R.drawable.im5,
                R.drawable.im6,
                R.drawable.im7,
                R.drawable.im8,
                R.drawable.im9,
                R.drawable.im10,
                R.drawable.im11
        };
        ImageHub imageHub = new ImageHub();
        imageHub.add(images);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.galleryLayout);

        controller = new GalleryController(getApplicationContext(),imageHub,linearLayout);

        controller.loadImages();

        idList = controller.getIdList();

        for(int i=0; i<idList.size(); i++) {
            ((ImageView)findViewById(idList.get(i))).setOnClickListener(this);
        }


    }

    private void loadElements()
    {
        ((FloatingActionButton) findViewById(R.id.fabCamera)).setOnClickListener(this);
        ((FloatingActionButton) findViewById(R.id.fabGallery)).setOnClickListener(this);
        backgroundImageView = ((ImageView)findViewById(R.id.backgroundImView));
        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ((ImageView)findViewById(R.id.iconOk)).setOnClickListener(this);

        imageController.setBackgroundImageView(backgroundImageView);
        imageController.loadBackgroundImage();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        imageController.loadBackgroundImage();
        //imageController.loadBitmap(this.backgroundImageView,mUri);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.fabCamera:
                pickFromCamera();
                break;
            case R.id.fabGallery:
                pickFromGallery();
                break;
            case R.id.backgroundImView:

                break;
            case R.id.iconOk:
                // background imgesi kabul edilecek....
                saveImage();
                onBackPressed();
                break;
        }

        for(int i=0; i<idList.size(); i++) {
            if(view.getId() == idList.get(i)) {
                Toast.makeText(this, "Index "+(i+1), Toast.LENGTH_SHORT).show();
                backgroundImageView.setImageBitmap(controller.getBitmap(i));
                controller.setSelectedIndex(i);
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

            Bitmap bitmap = controller.getBitmap(controller.getSelectedIndex());
            File pictureFile = FileUtilz.getOutputMediaFile(getApplicationContext(),cubeBackgroundPath);
            if(pictureFile == null)
            {
                Log.e(TAG,"Error creating media file , check permissions...");
                return;
            }
            try{
                FileOutputStream fos = new FileOutputStream(pictureFile);
                bitmap.compress(Bitmap.CompressFormat.PNG,90,fos);
                fos.flush();
                fos.close();
            }catch (FileNotFoundException e){
                Log.e(TAG,"Error File not found " + e.getMessage());
            }catch (IOException e){
                Log.e(TAG,"Error accessing file "+ e.getMessage());
            }

            Log.e(TAG,"Path is "+ pictureFile.toString());

//            String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//
//            File image = null;
//            Bitmap bitmap = controller.getBitmap(controller.getSelectedIndex());
//            FileOutputStream fos = null;
//            try {
//                image = new File(downloadsDirectoryPath, cubeBackgroundPath+".png");
//                fos = new FileOutputStream(image);
//                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
//                fos.flush();
//                fos.close();
//            }catch (IOException e){
//                Log.e(TAG,"Exception "+e.getMessage());
//                Toast.makeText(this, "Index "+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }


//            File image = null;
//            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            Bitmap bitmap = controller.getBitmap(controller.getSelectedIndex());
//            FileOutputStream fos = null;
//            try {
//                image = File.createTempFile(
//                        mCurrentPhotoPath,  /* prefix */
//                        ".png",         /* suffix */
//                        storageDir      /* directory */
//                );
//                fos = new FileOutputStream(image);
//                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
//                fos.flush();
//                fos.close();
//            }catch (IOException e){
//                Log.e(TAG,"Exception "+e.getMessage());
//                Toast.makeText(this, "Index "+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
            //Log.e(TAG,"Path is "+ image.toString());
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
        destinationFileName += ".png";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));


        //FileUtilz.getOutputMediaFile(getApplicationContext(),"camera_crop_image.png");
        //UCrop uCrop = UCrop.of(uri, Uri.fromFile(FileUtilz.getOutputMediaFile(getApplicationContext(),"camera_crop_image.png")));


        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);

        uCrop.start(ChangeBackground.this);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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
        destinationFileName += ".png";
//        switch (mRadioGroupCompressionSettings.getCheckedRadioButtonId()) {
////            case R.id.radio_png:
////                destinationFileName += ".png";
////                break;
////            case R.id.radio_jpeg:
////                destinationFileName += ".jpg";
////                break;
//        }

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

        uCrop = uCrop.withAspectRatio(1, 1);
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

        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setCompressionQuality(90);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);

        return uCrop.withOptions(options);
    }
    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            saveCroppedImage(resultUri);
            //CropResult.startWithUri(ChangeBackground.this, resultUri);
            //ChangeBackground.startWithUri(ChangeBackground.this, resultUri);
            Log.e("TAG","result uri "+ resultUri.getPath());


            //imageController.loadBitmap(this.backgroundImageView,mUri);
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


    @Override
    public void onChangeBackground() {
        Toast.makeText(ChangeBackground.this, "onChangeBackground()", Toast.LENGTH_LONG).show();
        Log.e(TAG,"onChangeBackground()");
    }

    @Override
    public void onChangeBackground(Uri uri) {
        Toast.makeText(ChangeBackground.this, "onChangeBackground(Uri uri)", Toast.LENGTH_LONG).show();
        Log.e(TAG,"onChangeBackground(Uri uri)");
    }

    @Override
    public void onChangeBackground(Bitmap bitmap) {
        Toast.makeText(ChangeBackground.this, "onChangeBackground(Bitmap bitmap)", Toast.LENGTH_LONG).show();
        Log.e(TAG,"onChangeBackground(Bitmap bitmap)");
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
        String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String filename = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), croppedFileUri.getLastPathSegment());

        File saveFile = FileUtilz.getOutputMediaFile(getApplicationContext(), ChangeBackground.cubeBackgroundPath); //new File(downloadsDirectoryPath, filename);

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

        showNotification(saveFile);
    }

    private void showNotification(@NonNull File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "image/*");

        NotificationCompat.Builder mNotification = new NotificationCompat.Builder(this);

        mNotification
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_image_saved_click_to_preview))
                .setTicker(getString(R.string.notification_image_saved))
                .setSmallIcon(R.drawable.ic_icon_ok)
                .setOngoing(false)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                .setAutoCancel(true);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(DOWNLOAD_NOTIFICATION_ID_DONE, mNotification.build());
    }
}
