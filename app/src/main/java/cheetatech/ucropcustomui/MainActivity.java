package cheetatech.ucropcustomui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import cheetatech.ucropcustomui.controllers.ImageController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageController imageController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(imageController == null)
            imageController = new ImageController(getApplicationContext());

        loadElements();

    }

    @Override
    protected void onResume() {
        super.onResume();

        imageController.loadBackgroundImage();
        imageController.loadAllBitmapCubeSideFromPicture();

    }

    private void loadElements()
    {
        ((ImageView)findViewById(R.id.backgroundIconChange)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.cubeBackgroundChange)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.frontImView)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.backImView)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.leftImView)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.rightImView)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.topImView)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.bottomImView)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.backgroundImView)).setOnClickListener(this);

        imageController.setBackgroundImageView((ImageView)findViewById(R.id.backgroundImView));

        imageController.loadBackgroundImage();
        imageController.addCubeSideImageViews(new ImageView[]{
                ((ImageView)findViewById(R.id.frontImView)),
                ((ImageView)findViewById(R.id.backImView)),
                ((ImageView)findViewById(R.id.leftImView)),
                ((ImageView)findViewById(R.id.rightImView)),
                ((ImageView)findViewById(R.id.topImView)),
                ((ImageView)findViewById(R.id.bottomImView))
        });
        imageController.loadAllBitmapCubeSideFromPicture();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backgroundIconChange:
                Intent intent = new Intent(this, ChangeBackground.class);
                startActivity(intent);
                break;


            case R.id.cubeBackgroundChange :
                //startActivity(new Intent(this, BackgroundActivity.class));
                startActivity(new Intent(this, ChangeCube.class));
                break;
            case R.id.frontImView :

                break;
            case R.id.backImView :

                break;
            case R.id.leftImView :

                break;
            case R.id.rightImView :

                break;
            case R.id.topImView :

                break;
            case R.id.bottomImView :

                break;
            case R.id.backgroundImView :

                break;
        }
    }
}
