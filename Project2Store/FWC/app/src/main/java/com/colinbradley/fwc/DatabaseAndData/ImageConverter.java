package com.colinbradley.fwc.DatabaseAndData;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.colinbradley.fwc.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by colinbradley on 11/5/16.
 */

public class ImageConverter {


    public static byte[] drawableToByteArray(Drawable d){
        Bitmap imageBitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, streamOut);
        return streamOut.toByteArray();
    }

    public static Drawable byteToDrawable(byte[] data){
        return new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0, data.length));
    }

    //Bitmap testphoto = BitmapFactory.decodeResource(getResources(), R.drawable.testpicture);

    //byte[] testimageinBYTES = ImageConverter.getBytes(testphoto);

    // helper.insertNewGear("Name", testimageinBYTES, "Description", "Type", 46);
}
