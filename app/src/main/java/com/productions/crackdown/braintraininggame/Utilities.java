package com.productions.crackdown.braintraininggame;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Avinath on 2/24/2018.
 */

public class Utilities {

    public static void alertUser(Context context,String message,int length){ //showing a toast to user
        Toast.makeText(context,message,length).show();
    }
}
