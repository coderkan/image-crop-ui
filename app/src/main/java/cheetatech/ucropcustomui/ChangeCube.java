package cheetatech.ucropcustomui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Manifest;

import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.controllers.CubeSidesController;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.gallery.GalleryController;
import cheetatech.ucropcustomui.gallery.ImageHub;

public class ChangeCube extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "ChangeCube";

    private GalleryController controller = null;
    private ArrayList<Integer> idList = new ArrayList<Integer>();
    private ImageView backgroundImageView = null;
    private ImageView  back = null, front = null,bottom = null, top = null, right = null, left = null;
    private ToggleButton  leftToggleButton = null,rightToggleButton = null, backToggleButton = null,
            frontToggleButton = null, bottomToggleButton = null,topToggleButton = null;
    private ImageView applyButton,okButton;

    private CubeSidesController cubeSidesController = null;
    private String mCurrentPhotoPath;
    private Uri mUri = null;

    private ImageController imageController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cube);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadElements();
        loadGallery();
    }

    private void loadElements()
    {


        if(imageController == null)
            imageController = new ImageController(getApplicationContext());

        ((FloatingActionButton) findViewById(R.id.fabGallery)).setOnClickListener(this);
        ((FloatingActionButton) findViewById(R.id.fabCamera)).setOnClickListener(this);

        okButton = (ImageView) findViewById(R.id.iconOk);
        applyButton = (ImageView) findViewById(R.id.iconApply);
        front = (ImageView) findViewById(R.id.frontImView);
        back = (ImageView) findViewById(R.id.backImView);
        right =(ImageView) findViewById(R.id.rightImView);
        left = (ImageView) findViewById(R.id.leftImView);
        top = (ImageView) findViewById(R.id.topImView);
        bottom = (ImageView) findViewById(R.id.bottomImView);
        leftToggleButton = (ToggleButton) findViewById(R.id.leftToggleButton);
        backToggleButton =(ToggleButton) findViewById(R.id.backToggleButton);
        frontToggleButton = (ToggleButton) findViewById(R.id.frontToggleButton);
        topToggleButton = (ToggleButton) findViewById(R.id.topToggleButton);
        bottomToggleButton = (ToggleButton) findViewById(R.id.bottomToggleButton);
        rightToggleButton = (ToggleButton) findViewById(R.id.rightToggleButton);



        leftToggleButton.setOnClickListener(this);
        backToggleButton.setOnClickListener(this);
        frontToggleButton.setOnClickListener(this);
        topToggleButton.setOnClickListener(this);
        bottomToggleButton.setOnClickListener(this);
        rightToggleButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
        okButton.setOnClickListener(this);

        front.setOnClickListener(this);
        back.setOnClickListener(this);
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        top.setOnClickListener(this);
        bottom.setOnClickListener(this);

        imageController.addCubeSideImageViews(new ImageView[]{
                front,
                back,
                left,
                right,
                top,
                bottom
        });

        imageController.loadAllBitmapCubeSideFromPicture();


        bottom.setSelected(true);

        cubeSidesController = new CubeSidesController(
                new ToggleButton[]{frontToggleButton,backToggleButton,leftToggleButton,rightToggleButton,topToggleButton,bottomToggleButton},
                new ImageView[]{front,back,left,right,top,bottom}
                );

        frontToggleButton.setChecked(true);
        cubeSidesController.controlToggleButtons(frontToggleButton.getId());
    }

    private void loadGallery()
    {

        backgroundImageView = ((ImageView)findViewById(R.id.backgroundImView));
        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //imageController.setBackgroundImageView(backgroundImageView);

        imageController.loadBitmap(backgroundImageView, Side.FRONT);


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

        ((HorizontalScrollView) findViewById(R.id.horizontalScroll)).setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.galleryLayout);
        linearLayout.setOnClickListener(this);

        controller = new GalleryController(getApplicationContext(),imageHub,linearLayout);

        controller.loadImages();

        idList = controller.getIdList();

        for(int i=0; i<idList.size(); i++) {
            ((ImageView)findViewById(idList.get(i))).setOnClickListener(this);
        }



    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id == R.id.leftToggleButton || id == R.id.frontToggleButton|| id == R.id.backToggleButton|| id == R.id.rightToggleButton
                || id == R.id.topToggleButton|| id == R.id.bottomToggleButton)
        {
            cubeSidesController.controlToggleButtons(id);
            imageController.loadBitmap(backgroundImageView,cubeSidesController.getSelectedIndex());

            Toast.makeText(this, "Index "+ cubeSidesController.getSelectedIndex(), Toast.LENGTH_SHORT).show();
            return;
        }


        switch (view.getId())
        {
            case R.id.iconOk :
                cubeSidesController.getCurrentImageView().setImageBitmap(controller.getBitmap(controller.getSelectedIndex()));
                break;
            case R.id.iconApply:
                saveImage();
                onBackPressed();
                break;
            case R.id.fabGallery:
                pickFromGallery();
                break;

            case R.id.fabCamera:
                pickFromCamera();
                break;

            case R.id.frontImView:
                break;
            case R.id.backImView:
                break;
            case R.id.topImView:
                break;
            case R.id.rightImView:
                break;
            case R.id.leftImView:
                break;
            case R.id.bottomImView:
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
                photoFile = createImageFile();
            }catch (IOException e){
                Toast.makeText(ChangeCube.this, "ErrorCreateImageFile", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ChangeCube.this, "PickImage " +ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "Hata : "+ ex.getMessage());
                }

            }
        }

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
            case REQUEST_CAMERA_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickFromCamera();
                }
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveImage();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }
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
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_SELECT_PICTURE){
                final Uri selectedUri = data.getData();
                if(selectedUri!= null) {
                    startCropActivity(data.getData());
                }else {
                    Toast.makeText(ChangeCube.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }

            }else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
            else if(requestCode == REQUEST_START_CAMERA_APP){
                // save images
                try {
                    startCropActivityFromCamera();
                }catch (Exception ex){
                    Log.e("TAG","Cannot Start CropActivity");
                    Toast.makeText(ChangeCube.this, "Exception Error "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }

    }


    private void startCropActivityFromCamera(/*@NonNull Uri uri*/) {
        Uri uri = mUri;
        Toast.makeText(ChangeCube.this, "Uri Uri "+uri.toString(), Toast.LENGTH_SHORT).show();
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".png";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));



        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);

        uCrop.start(ChangeCube.this);

    }

    private void startCropActivity(@NonNull Uri uri) {
        Toast.makeText(ChangeCube.this, uri.toString(), Toast.LENGTH_SHORT).show();
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
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

        uCrop.start(ChangeCube.this);

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
            CropResult.startWithUri(ChangeCube.this, resultUri);
        } else {
            Toast.makeText(ChangeCube.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(ChangeCube.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ChangeCube.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }



    private void saveImage() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        }else{
            imageController.saveCubeSidesImage();
        }
    }
}
