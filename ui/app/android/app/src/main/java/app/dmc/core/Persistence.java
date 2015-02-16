package app.dmc.core;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by Joscormir on 16/02/2015.
 */
public class Persistence {
    //-----------------------------------------------------------------------------------------------------------------
    static public void init(Context _context) { instance = new Persistence(_context); }

    //-----------------------------------------------------------------------------------------------------------------
    public static Persistence get(){ return instance; }

    //-----------------------------------------------------------------------------------------------------------------
    public static JSONObject getData(){
        File file = new File(mExternalDirPath + "hubList.json");
        if(file.exists()){
            try {
                BufferedReader is = new BufferedReader(new FileReader(file));
                String jsonString = is.readLine();
                mJson = new JSONObject(jsonString);

            } catch (IOException e) {
                Log.d("Can't open HubList", e.getMessage());
            }catch (JSONException e){
                Log.d("Can't open JSONObject", e.getMessage());
            }
            return mJson;
        }else return mJson = null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static boolean putData() throws IOException{
        File file = new File(mExternalDirPath +"hubList.json");
        try {
            FileWriter fileToWrite = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileToWrite);
            String changesToWrite = mJson.toString();
            out.write(changesToWrite);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            Log.d("Can't open HubList", e.getMessage());
            return false;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    //Private interface
    private Persistence(Context _context){
        File externalDir = _context.getFilesDir();
        mExternalDirPath = externalDir.getPath();

    }

    //-----------------------------------------------------------------------------------------------------------------
    private static Persistence  instance = null;
    private static String       mExternalDirPath;
    private static JSONObject   mJson;
}
