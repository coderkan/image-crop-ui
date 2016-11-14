package cheetatech.ucropcustomui.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by erkan on 14.11.2016.
 */

public class FirebaseParcel implements Parcelable {

    private FirebaseModel fbaseModel = null;

    public FirebaseParcel(){}

    public FirebaseParcel(FirebaseModel fbaseModel){
        this.fbaseModel = fbaseModel;
    }

    protected FirebaseParcel(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.fbaseModel.setName(data[0]);
        this.fbaseModel.setFileName(data[1]);
        this.fbaseModel.setUrl(data[2]);
    }

    public FirebaseParcel(String name,String fname,String url){
        this.fbaseModel = new FirebaseModel();
        this.fbaseModel.setName(name);
        this.fbaseModel.setFileName(fname);
        this.fbaseModel.setUrl(url);
    }

    public static final Creator<FirebaseParcel> CREATOR = new Creator<FirebaseParcel>() {
        @Override
        public FirebaseParcel createFromParcel(Parcel in) {
            return new FirebaseParcel(in);
        }

        @Override
        public FirebaseParcel[] newArray(int size) {
            return new FirebaseParcel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fbaseModel.getName());
        parcel.writeString(this.fbaseModel.getFileName());
        parcel.writeString(this.fbaseModel.getUrl());
    }
}
