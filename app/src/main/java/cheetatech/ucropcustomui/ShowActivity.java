package cheetatech.ucropcustomui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.ecoinlib.OnCoinLibListener;
import cheetatech.ucropcustomui.ecoinlib.eCoinLib;
import cheetatech.ucropcustomui.firebase.FBaseModel;
import cheetatech.ucropcustomui.firebase.FirebaseModel;
import cheetatech.ucropcustomui.firebase.FirebaseParcel;
import cheetatech.ucropcustomui.firebase.FirebaseParcelable;
import cheetatech.ucropcustomui.showactivity.ShowPresenter;
import cheetatech.ucropcustomui.showactivity.ShowView;

public class ShowActivity extends BaseActivity implements ShowView ,OnCoinLibListener{


    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.show_imageview)
    ImageView previewImageView;
    @BindView(R.id.text_coin)
    TextView textCoin;


    ShowPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        ButterKnife.bind(this);

        // Presenter's method
        presenter = new ShowPresenter(getApplicationContext(),this);
        presenter.init(FBaseModel.getInstance().getModel());
        presenter.setCoinListener(this);
        presenter.loadCoin();
        //

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.fab) public void fabOnClick(){
        presenter.downloadImage();
    }

    @Override
    public void onChangeFabButtonDisable() {
        fab.setImageResource(R.drawable.ic_file_remove);
    }

    @Override
    public void onChangeFabButtonDownload() {
        fab.setImageResource(R.drawable.ic_file_download);
    }

    @Override
    public void onPreviewImage(String url) {
        Picasso.with(this).load(url).into(previewImageView);
    }

    @Override
    public void onAlreadyDownload(String name) {
        Toast.makeText(getApplicationContext(),"File is already downloaded local",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadSuccessfully(String name) {
        Toast.makeText(getApplicationContext(),"Successfuly downloaded image to "+ name,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureDownloadImage() {
        Toast.makeText(getApplicationContext(),"An error when downloaded images, please checout your NETWORK... ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNeedMoreCoin() {
        Toast.makeText(getApplicationContext(),"You can not download image to your local, Need More Coin",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnoughCoin() {
        Toast.makeText(getApplicationContext(),"You can download image to your local",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onLoadCoin(int coin) {
        textCoin.setText(""+coin+ " $ ");
    }
}
