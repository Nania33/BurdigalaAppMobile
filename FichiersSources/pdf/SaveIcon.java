package com.enseirb.pfa.bastats.pdf;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.enseirb.pfa.bastats.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rsabir on 28/03/15.
 */
public class SaveIcon {
    SaveIcon(int drawable,String fileName, Activity activity) {
        Bitmap bitMap = BitmapFactory.decodeResource(activity.getResources(), drawable);

        File mFile1 = new File(BrowserFile.path+"/");//****


        File mFile2 = new File(mFile1, fileName);
        try {
            FileOutputStream outStream;

            outStream = new FileOutputStream(mFile2);

            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            outStream.flush();

            outStream.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String sdPath = BrowserFile.path + "/" + fileName;

        Log.i("MAULIK", "Your IMAGE ABSOLUTE PATH:-" + sdPath);

        File temp = new File(sdPath);

        if (!temp.exists()) {
            Log.e("file", "no image file at location :" + sdPath);
        }
    }
}
