package cheetatech.ucropcustomui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import cheetatech.ucropcustomui.R;

/**
 * Created by erkan on 15.11.2016.
 */

public class DialogBuilder {

    private Context context = null;
    private Dialog dialog = null;

    public DialogBuilder(){}
    public DialogBuilder(Context context){
        this.context = context;
        this.dialog = new Dialog(this.context);

        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Get More Coin");

        Button apply = (Button)dialog.findViewById(R.id.button_ad_apply);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
