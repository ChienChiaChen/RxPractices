package com.chiachen.rxjavapractices;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jianjiacheng on 20/12/2017.
 */

public class CommonUtils {

    public static void showToast(Context context,String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
