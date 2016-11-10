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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.backgroundactivity.ImageModel;
import cheetatech.ucropcustomui.changecubeactivities.ChangeCubePresenter;
import cheetatech.ucropcustomui.changecubeactivities.ChangeCubeView;
import cheetatech.ucropcustomui.controllers.CubeSidesController;
import cheetatech.ucropcustomui.controllers.ImageController;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.gallery.GalleryController;
import cheetatech.ucropcustomui.gallery.ImageHub;

public class ChangeCube extends BaseActivity implements View.OnClickListener , ChangeCubeView{

    private static final String TAG = "ChangeCube";

    @BindView(R.id.fabGallery) FloatingActionButton fabGallery;
    @BindView(R.id.fabCamera) FloatingActionButton fabCamera;

    @BindView(R.id.iconOk) ImageView okButton ;
    @BindView(R.id.iconApply)ImageView applyButton ;
    @BindView(R.id.frontImView) ImageView front ;
    @BindView(R.id.backImView) ImageView back ;
    @BindView(R.id.rightImView)ImageView right;
    @BindView(R.id.leftImView) ImageView left;
    @BindView(R.id.topImView)ImageView top ;
    @BindView(R.id.bottomImView) ImageView bottom ;
    @BindView(R.id.leftToggleButton)ToggleButton leftToggleButton ;
    @BindView(R.id.backToggleButton)ToggleButton backToggleButton ;
    @BindView(R.id.frontToggleButton)ToggleButton frontToggleButton ;
    @BindView(R.id.topToggleButton)ToggleButton topToggleButton ;
    @BindView(R.id.bottomToggleButton)ToggleButton bottomToggleButton ;
    @BindView(R.id.rightToggleButton)ToggleButton rightToggleButton ;

    @BindView(R.id.galleryLayout) LinearLayout galleryLayout;
    @BindView(R.id.backgroundImView) ImageView backgroundImageView;

    private ImageView[] cubeSides = null;

    private ChangeCubePresenter presenter = null;

    private ArrayList<ImageModel> models = new ArrayList<ImageModel>();




    private GalleryController controller = null;
    private ArrayList<Integer> idList = new ArrayList<Integer>();

    private CubeSidesController cubeSidesController = null;
    private String mCurrentPhotoPath;
    private Uri mUri = null;

    private ImageController imageController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cube);
        ButterKnife.bind(this);

        if(presenter == null)
            presenter = new ChangeCubePresenter(getApplicationContext(),this);

        cubeSides = new ImageView[]{ front,back,left,right,top,bottom };


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadElements();
        loadGallery();
    }

    private void loadElements()
    {


        presenter.loadElements();
        presenter.loadCubeSideImages();

//        if(imageController == null)
//            imageController = new ImageController(getApplicationContext());
//
//
//
//
//        bottom.setSelected(true);
//
//        cubeSidesController = new CubeSidesController(
//                new ToggleButton[]{frontToggleButton,backToggleButton,leftToggleButton,rightToggleButton,topToggleButton,bottomToggleButton},
//                new ImageView[]{front,back,left,right,top,bottom}
//                );
//
//        frontToggleButton.setChecked(true);
//        cubeSidesController.controlToggleButtons(frontToggleButton.getId());
    }

    private void loadGallery()
    {

        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);


//        backgroundImageView = ((ImageView)findViewById(R.id.backgroundImView));
//        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);
//
//
//        imageController.loadBitmap(backgroundImageView, Side.FRONT);
//
//
//        int[] images = new int[]{
//                R.drawable.im1,
//                R.drawable.im2 ,
//                R.drawable.im3,
//                R.drawable.im4,
//                R.drawable.im5,
//                R.drawable.im6,
//                R.drawable.im7,
//                R.drawable.im8,
//                R.drawable.im9,
//                R.drawable.im10,
//                R.drawable.im11
//        };
//        ImageHub imageHub = new ImageHub();
//        imageHub.add(images);
//
//        ((HorizontalScrollView) findViewById(R.id.horizontalScroll)).setOnClickListener(this);
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.galleryLayout);
//        linearLayout.setOnClickListener(this);
//
//        controller = new GalleryController(getApplicationContext(),imageHub,linearLayout);
//
//        controller.loadImages();
//
//        idList = controller.getIdList();
//
//        for(int i=0; i<idList.size(); i++) {
//            ((ImageView)findViewById(idList.get(i))).setOnClickListener(this);
//        }




    }

    @OnClick(R.id.fabCamera) public void clickCamera(){
        pickFromCamera();
    }

    @OnClick(R.id.fabGallery) public void clickGallery(){
        pickFromGallery();
    }

    @OnClick(R.id.iconOk)
    public void clickOkButton(){
        cubeSidesController.getCurrentImageView().setImageBitmap(controller.getBitmap(controller.getSelectedIndex()));
    }

    @OnClick(R.id.iconApply)
    public void clickApplyButton(){
        saveImage();
        onBackPressed();
    }

    @OnClick({R.id.leftToggleButton,R.id.rightToggleButton,R.id.topToggleButton,R.id.bottomToggleButton,R.id.frontToggleButton,R.id.backToggleButton})
    public void clickToggleButton(View view){
        int id = view.getId();
        Log.e("TAG","New OnClick Listener");
        if(id == R.id.leftToggleButton || id == R.id.frontToggleButton|| id == R.id.backToggleButton|| id == R.id.rightToggleButton
                || id == R.id.topToggleButton|| id == R.id.bottomToggleButton) {
            cubeSidesController.controlToggleButtons(id);
            imageController.loadBitmap(backgroundImageView, cubeSidesController.getSelectedIndex());

            Toast.makeText(this, "Indexxxx " + cubeSidesController.getSelectedIndex(), Toast.LENGTH_SHORT).show();
            return;
        }
    }



    @Override
    public void onClick(View view) {
        for(int i=0; i<this.models.size(); i++) {
            if(view.getId() == this.models.get(i).getId()) {
                Toast.makeText(this, "Index "+(i+1), Toast.LENGTH_SHORT).show();
                presenter.setSelectedImageModel(this.models.get(i));
            }
        }

//        for(int i=0; i<idList.size(); i++) {
//            if(view.getId() == idList.get(i)) {
//                Toast.makeText(this, "Index "+(i+1), Toast.LENGTH_SHORT).show();
//                backgroundImageView.setImageBitmap(controller.getBitmap(i));
//                controller.setSelectedIndex(i);
//            }
//        }
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
    public void onLoadCubeSideBitmap(int side, Bitmap bitmap) {

    }

    @Override
    public void onLoadCubeSides(Bitmap[] bitmaps) {
        int i = 0;
        for (ImageView imageView: cubeSides
             ) {
            imageView.setImageBitmap(bitmaps[i++]);
        }
    }
}
