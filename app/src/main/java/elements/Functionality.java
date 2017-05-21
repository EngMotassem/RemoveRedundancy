package elements;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Environment;

import com.example.mohamedhanydev.repeatcancellation.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Created by MohamedDev on 5/16/2017.
 */

public class Functionality {


    public static ArrayList<String> getPathOfAllImages(Activity activity,String s) {
        String SD_CARD_ROOT;



        SD_CARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
        final ArrayList<String> tFileList = new ArrayList<String>();
        String[] imageTypes;
        Resources resources = activity.getResources();
        // array of valid image file extensions
        if(s.equals("image"))
            imageTypes = resources.getStringArray(R.array.image);
        else {
            imageTypes = resources.getStringArray(R.array.video);

        }
        FilenameFilter[] filter = new FilenameFilter[imageTypes.length];

        int i = 0;
        for (final String type : imageTypes) {
            filter[i] = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith("." + type);
                }
            };
            i++;
        }

        FileUtils fileUtils = new FileUtils();
        File[] allMatchingFiles = fileUtils.listFilesAsArray(
                new File(SD_CARD_ROOT), filter, -1);
        for (File f : allMatchingFiles) {
            tFileList.add(f.getAbsolutePath());
        }
        return tFileList;
    }
    public static String getMd5OfFile(String filePath)
    {
        String returnVal = "";
        try
        {
            InputStream input   = new FileInputStream(filePath);
            byte[]        buffer  = new byte[1024];
            MessageDigest md5Hash = MessageDigest.getInstance("MD5");
            int           numRead = 0;
            while (numRead != -1)
            {
                numRead = input.read(buffer);
                if (numRead > 0)
                {
                    md5Hash.update(buffer, 0, numRead);
                }
            }
            input.close();

            byte [] md5Bytes = md5Hash.digest();
            for (int i=0; i < md5Bytes.length; i++)
            {
                returnVal += Integer.toString( ( md5Bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
        }
        catch(Throwable t) {t.printStackTrace();}
        return returnVal.toUpperCase();
    }

}
