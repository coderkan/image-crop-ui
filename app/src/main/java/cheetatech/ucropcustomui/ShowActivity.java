package cheetatech.ucropcustomui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import cheetatech.ucropcustomui.firebase.FBaseModel;
import cheetatech.ucropcustomui.firebase.FirebaseModel;
import cheetatech.ucropcustomui.firebase.FirebaseParcel;
import cheetatech.ucropcustomui.firebase.FirebaseParcelable;

public class ShowActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseModel model = FBaseModel.getInstance().getModel();
        Toast.makeText(getApplicationContext(),"Alınan Model "+ model.getName(),Toast.LENGTH_SHORT).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}