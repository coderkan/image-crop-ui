package cheetatech.ucropcustomui.showactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

import cheetatech.ucropcustomui.CardActivity;
import cheetatech.ucropcustomui.R;
import cheetatech.ucropcustomui.controllers.Side;
import cheetatech.ucropcustomui.ecoinlib.OnCoinLibListener;
import cheetatech.ucropcustomui.ecoinlib.eCoinLib;
import cheetatech.ucropcustomui.fileutil.FileUtilz;
import cheetatech.ucropcustomui.firebase.FirebaseModel;

/**
 * Created by Erkan.Guzeler on 15.11.2016.
 */

public class ShowPresenter {

    private Context context = null;
    private FirebaseModel model = null;
    private ShowView view = null;
    private OnCoinLibListener coinListener = null;
    private eCoinLib eCoin = null;
    private FirebaseStorage storage = null;
    private StorageReference storageRef = null;

    private boolean state = false;


    public ShowPresenter(){

    }
    public ShowPresenter(Context context,ShowView view){
        this.context = context;
        this.view = view;
    }

    public FirebaseModel getModel(){
        return this.model;
    }
    public void setModel(FirebaseModel model){
        this.model = model;
    }

    public void init( FirebaseModel model) {
        this.model = model;

        // fab button icon control
        String fname = this.model.getFileName();

        if(FileUtilz.isFileInRootBackground(context,fname)){
            this.view.onChangeFabButtonDisable();
            setState(true);
        }
        else{
            this.view.onChangeFabButtonDownload();
            setState(false);
        }
        // imgenin yüklenmesi yapılacak
        if(this.model.getUrl() != null)
            this.view.onPreviewImage(this.model.getUrl());
    }
    public OnCoinLibListener getCoinListener(){
        return this.coinListener;
    }
    public void setCoinListener(OnCoinLibListener listener){
        this.coinListener = listener;
    }
    public void loadCoin(){
        if(this.coinListener == null)
            return;
        if(this.context == null)
            return;
        eCoin = new eCoinLib(context,this.coinListener);
    }

    public boolean getState(){
        return this.state;
    }
    public void setState(boolean state){
        this.state = state;
    }


    private void controlStorage()
    {
        storage = FirebaseStorage.getInstance();
        if(storage == null)
            Log.e("TAG","storage null");
        storageRef = storage.getReferenceFromUrl(CardActivity.BUCKETPATH);
        if(storageRef == null)
            Log.e("TAG","storage REF null");
    }

    public void downloadImage() {
        String fname = this.model.getFileName();

        if(FileUtilz.isFileInRootBackground(context,fname)){
            this.view.onAlreadyDownload(fname);
            return;
        }

        // Coin Controlü

        if(!this.eCoin.compareAndRemoveCoin())
            return;


        controlStorage();
        //
        StorageReference imagesRef = storageRef.child("images");

        String filename = this.model.getFileName();

        StorageReference spaceRef = imagesRef.child(filename);

        StorageReference imRef = imagesRef.child(filename);

        File localFile = null;

        String path = Side.BACKGROUND + File.separator + this.model.getFileName();

        localFile = FileUtilz.getOutMediaFile(this.context,path);
        final File finalLocalFile = localFile;
        imRef.getFile(finalLocalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("ShowActivity","OnSuccess Download File "+ finalLocalFile.getAbsolutePath().toString() );
                view.onDownloadSuccessfully(finalLocalFile.getAbsolutePath().toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ShowActivity","Error onFailure Download Image");
                view.onFailureDownloadImage();
            }
        });
    }



    public void deleteImage() {
        String fname = this.model.getFileName();

        if(FileUtilz.isFileInRootBackground(context,fname)){
            String path = Side.BACKGROUND + File.separator + this.model.getFileName();

            File localFile = FileUtilz.getOutMediaFile(this.context,path);
            if(localFile.exists()){
                boolean deleted = localFile.delete();
                if(deleted){
                    this.view.onChangeFabButtonDownload();
                    this.view.onRemovedSuccessfuly();
                }
                else{
                    this.view.onChangeFabButtonDisable();
                    this.view.onFailureRemovedImage();
                }

            }else{
                this.view.onChangeFabButtonDownload();
                this.view.onRemovedSuccessfuly();
            }

        }

    }




}
