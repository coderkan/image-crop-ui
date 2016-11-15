package cheetatech.ucropcustomui.firebase;

import com.baoyz.pg.Parcelable;

@Parcelable
public class FirebaseParcelable {
    private FirebaseModel model = null;

    public FirebaseParcelable(){}

    public FirebaseParcelable(FirebaseModel model){
        this.model = model;
    }

    public FirebaseModel getModel(){
        return this.model;
    }
    public void setModel(FirebaseModel model){
        this.model = model;
    }
}
