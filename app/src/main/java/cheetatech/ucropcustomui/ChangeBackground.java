package cheetatech.ucropcustomui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.gallery.GalleryController;
import cheetatech.ucropcustomui.gallery.ImageHub;

public class ChangeBackground extends BaseActivity implements View.OnClickListener{


    private static final String TAG = "ChangeBackgroun";

    private GalleryController controller = null;
    private ArrayList<Integer> idList = new ArrayList<Integer>();
    private ImageView backgroundImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.fabCamera:

                break;
            case R.id.fabGallery:
                pickFromGallery();
                break;
            case R.id.backgroundImView:

                break;
            case R.id.iconOk:
                // background imgesi kabul edilecek....
                break;
        }

        for(int i=0; i<idList.size(); i++) {
            if(view.getId() == idList.get(i)) {
                Toast.makeText(this, "Index "+(i+1), Toast.LENGTH_SHORT).show();
                backgroundImageView.setImageBitmap(controller.getBitmap(i));

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
            CropResult.startWithUri(ChangeBackground.this, resultUri);
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



}
