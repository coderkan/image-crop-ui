package cheetatech.ucropcustomui.gallery;

import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by erkan on 29.10.2016.
 */

public class ViewIdGenerator {

    private static final AtomicInteger nextGeneratedId = new AtomicInteger(1);

    public static int generateViewId()
    {
        if(Build.VERSION.SDK_INT < 17 )
            return generateIdVer17();
        else
            return View.generateViewId();
    }
    private static int generateIdVer17()
    {
        for (;;) {
            final int result = nextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (nextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
