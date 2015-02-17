package app.dmc.core;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Joscormir on 16/02/2015.
 */
public class Persistence {
    //-----------------------------------------------------------------------------------------------------------------
    static public void init(Context _context) { instance = new Persistence(_context); }

    //-----------------------------------------------------------------------------------------------------------------
    public static Persistence get(){ return instance; }

    //-----------------------------------------------------------------------------------------------------------------
    public static JSONObject getData(Context _context, String _fileName){
       JSONObject json = null;
            try {
                FileInputStream fileToRead = _context.openFileInput(_fileName + ".json");
                //here code throws an IOException, can't open Hub because it can't find it
                ObjectInputStream in = new ObjectInputStream(fileToRead);
                json = new JSONObject(in.readUTF());
            } catch (IOException e) {
                Log.d("Can't open HubList", e.getMessage());
            }catch (JSONException e){
                Log.d("Can't open JSONObject", e.getMessage());
            }
       return json;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static boolean putData(Context _context,String _fileName, JSONObject _json){
        boolean success = false;
        try{
            FileOutputStream fileToWrite = _context.openFileOutput(_fileName + ".json",Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileToWrite);
            out.writeObject(_json.toString());
            out.close();
            success = true;
        }catch(FileNotFoundException e){
            Log.d("Can't find file", e.getMessage());

        }catch(IOException e){
            Log.d("Can't send", e.getMessage());

        }
        return success;
    }

    //-----------------------------------------------------------------------------------------------------------------

    //Private interface
    private Persistence(Context _context){

    }

    //-----------------------------------------------------------------------------------------------------------------
    private static Persistence  instance = null;
}
