package cheetatech.ucropcustomui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import cheetatech.ucropcustomui.controllers.CubeSidesController;
import cheetatech.ucropcustomui.gallery.GalleryController;
import cheetatech.ucropcustomui.gallery.ImageHub;

public class ChangeCube extends AppCompatActivity implements View.OnClickListener{

    private GalleryController controller = null;
    private ArrayList<Integer> idList = new ArrayList<Integer>();
    private ImageView backgroundImageView = null;
    private ImageView  back = null, front = null,bottom = null, top = null, right = null, left = null;
    private ToggleButton  leftToggleButton = null,rightToggleButton = null, backToggleButton = null,
            frontToggleButton = null, bottomToggleButton = null,topToggleButton = null;


    private CubeSidesController cubeSidesController = null;

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

        front.setOnClickListener(this);
        back.setOnClickListener(this);
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        top.setOnClickListener(this);
        bottom.setOnClickListener(this);

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id == R.id.leftToggleButton || id == R.id.frontToggleButton|| id == R.id.backToggleButton|| id == R.id.rightToggleButton
                || id == R.id.topToggleButton|| id == R.id.bottomToggleButton)
        {
            cubeSidesController.controlToggleButtons(id);

            Toast.makeText(this, "Index "+ cubeSidesController.getSelectedIndex(), Toast.LENGTH_SHORT).show();
            return;
        }


        switch (view.getId())
        {
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
                cubeSidesController.getCurrentImageView().setImageBitmap(controller.getBitmap(i));
            }
        }


    }
}
