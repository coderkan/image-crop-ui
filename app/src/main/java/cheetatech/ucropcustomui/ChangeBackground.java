package cheetatech.ucropcustomui;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class ChangeBackground extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadElements();

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
