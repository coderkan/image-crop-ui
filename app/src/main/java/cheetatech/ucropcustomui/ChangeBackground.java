package cheetatech.ucropcustomui;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cheetatech.ucropcustomui.gallery.GalleryController;
import cheetatech.ucropcustomui.gallery.ImageHub;

public class ChangeBackground extends AppCompatActivity implements View.OnClickListener{

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

        GalleryController controller = new GalleryController(getApplicationContext(),imageHub,linearLayout);

        controller.loadImages();


    }

    private void loadElements()
    {
        ((FloatingActionButton) findViewById(R.id.fabCamera)).setOnClickListener(this);
        ((FloatingActionButton) findViewById(R.id.fabGallery)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.backgroundImView)).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.fabCamera:

                break;
            case R.id.fabGallery:

                break;
            case R.id.backgroundImView:

                break;
        }

    }
}
