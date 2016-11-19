package cheetatech.ucropcustomui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.merge.ImageMerge;

public class MergeActivity extends AppCompatActivity {

    @BindView(R.id.image_merge_view)
    ImageView mergeView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fab) void pressFab(){
        mergeImage();
    }

    private void mergeImage() {
        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < 6;i++) {
            File f = FileUtilz.getOutMediaFile(getApplicationContext(),FileUtilz.accomplish(Side.CUBE1,Side.cubeSidePath[i]));
            if(f.exists()){
                files.add(f);
            }
        }

        ImageMerge merge = new ImageMerge(getApplicationContext(),files);
        merge.merge();

        Bitmap bitmap = merge.getBitmap();
        merge.save(FileUtilz.getOutMediaFile(getApplicationContext(),FileUtilz.accomplish(Side.CUBE1,"ball_template.png")));

        mergeView.setImageBitmap(bitmap);

    }



}
