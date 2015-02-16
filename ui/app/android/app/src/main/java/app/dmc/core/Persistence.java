package app.dmc.core;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarException;

/**
 * Created by Joscormir on 16/02/2015.
 */
public class Persistence {
    //-----------------------------------------------------------------------------------------------------------------
    static public void init() { instance = new Persistence(); }

    //-----------------------------------------------------------------------------------------------------------------
    public static Persistence get(){ return instance; }

    //-----------------------------------------------------------------------------------------------------------------
    public static JSONObject getData(){
        if(mFile.exists()) {
            try {
                BufferedReader is = new BufferedReader(new FileReader(mFile));
                String jsonString = is.readLine();
                JSONObject json = new JSONObject(jsonString);
            } catch (IOException e) {
                Log.d("Can't open HubList", e.getMessage());
            }catch (JSONException e){
                Log.d("Can't open JSONObject", e.getMessage());
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static boolean putData(){


    }
    //-----------------------------------------------------------------------------------------------------------------

    //Private interface
    private Persistence(Context _context){
        File externalDir = _context.getFilesDir();
        String externalDirPath = externalDir.getPath();
        mFile = new File(externalDirPath + "hubList.json");
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static Persistence instance = null;
    private static File mFile;
}
