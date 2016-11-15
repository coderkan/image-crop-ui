package cheetatech.ucropcustomui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.ucropcustomui.activitys.BaseActivity;
import cheetatech.ucropcustomui.ads.OnAdsListener;
import cheetatech.ucropcustomui.dialogs.DialogBuilder;
import cheetatech.ucropcustomui.ecoinlib.OnCoinLibListener;
import cheetatech.ucropcustomui.ecoinlib.eCoinLib;
import cheetatech.ucropcustomui.firebase.FBaseModel;
import cheetatech.ucropcustomui.showactivity.ShowPresenter;
import cheetatech.ucropcustomui.showactivity.ShowView;

public class ShowActivity extends BaseActivity implements ShowView ,OnCoinLibListener,OnAdsListener{


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
        if(presenter.getState())
            presenter.deleteImage();
        else
            presenter.downloadImage();
    }

    @Override
    public void onChangeFabButtonDisable() {
        fab.setImageResource(R.drawable.ic_file_remove);
        presenter.setState(true);
    }

    @Override
    public void onChangeFabButtonDownload() {
        fab.setImageResource(R.drawable.ic_file_download);
        presenter.setState(false);
    }

    @Override
    public void onPreviewImage(String url) {
        Picasso.with(this).load(url).into(previewImageView);
    }

    @Override
    public void onAlreadyDownload(String name) {
        Toast.makeText(getApplicationContext(),"File is already downloaded local",Toast.LENGTH_SHORT).show();
        onChangeFabButtonDisable();
    }

    @Override
    public void onDownloadSuccessfully(String name) {
        Toast.makeText(getApplicationContext(),"Successfuly downloaded image to "+ name,Toast.LENGTH_SHORT).show();
        onChangeFabButtonDisable();
    }

    @Override
    public void onFailureDownloadImage() {
        Toast.makeText(getApplicationContext(),"An error when downloaded images, please checout your NETWORK... ",Toast.LENGTH_SHORT).show();
        onChangeFabButtonDownload();
    }

    @Override
    public void onRemovedSuccessfuly() {
        Toast.makeText(getApplicationContext(),"Successfully Removed image",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureRemovedImage() {
        Toast.makeText(getApplicationContext(),"Remove Failure image",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNeedMoreCoin() {
        Toast.makeText(getApplicationContext(),"You can not download image to your local, Need More Coin",Toast.LENGTH_SHORT).show();
        new DialogBuild(this);
    }

    @Override
    public void onEnoughCoin() {
        Toast.makeText(getApplicationContext(),"You can download image to your local",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onLoadCoin(int coin) {
        textCoin.setText(""+coin+ " $ ");
    }

    @Override
    public void onOpenAds() {
        Toast.makeText(getApplicationContext(),"Ads starting",Toast.LENGTH_SHORT).show();
        eCoinLib eCoinLib = new eCoinLib(getApplicationContext(),this);
        eCoinLib.addCoin(50);
    }

    public class DialogBuild {

        private OnAdsListener listener = null;
        private Dialog dialog = null;

        public DialogBuild(){}
        public DialogBuild(final OnAdsListener listener){

            this.listener = listener;
            this.dialog = new Dialog(ShowActivity.this);

            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Get More Coin");
            dialog.setCanceledOnTouchOutside(false);

            Button apply = (Button)dialog.findViewById(R.id.button_ad_apply);

            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onOpenAds();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

    }

}
