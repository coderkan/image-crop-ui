package cheetatech.ucropcustomui.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import cheetatech.ucropcustomui.parcelable.GlobalParcelable;

/**
 * Created by erkan on 14.11.2016.
 */

public class FirebaseParcel extends GlobalParcelable {

    private FirebaseModel fbaseModel = null;
    public FirebaseParcel(){}

    public FirebaseParcel(FirebaseModel fbaseModel){
        this.fbaseModel = fbaseModel;
    }
    public void setFbaseModel(FirebaseModel fbaseModel){
        this.fbaseModel = fbaseModel;
    }
    public FirebaseModel getFbaseModel(){
        return this.fbaseModel;
    }
    public String getName(){
        return this.fbaseModel.getName();
    }
    public String getFileName(){
        return this.fbaseModel.getFileName();
    }
    public String getUrl(){
        return this.fbaseModel.getUrl();
    }
}
