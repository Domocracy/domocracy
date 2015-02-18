package app.dmc.core;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Joscormir on 16/02/2015.
 */

public class Persistence {

    //-----------------------------------------------------------------------------------------------------------------
    public static Persistence get(Context _context){
        instance = new Persistence(_context);
        return instance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject getData(Context _context, String _fileName){
       JSONObject json = null;
        File file = new File(_context.getExternalFilesDir(null), _fileName + ".json");
            try {
                InputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] data = new byte[in.available()];
                in.read(data);
                out.write(data);
                String outToString = new String(out.toByteArray());
                json = new JSONObject(outToString);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }
       return json;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public boolean putData(Context _context,String _fileName, JSONObject _json){
        boolean success = false;
        File file = new File(_context.getExternalFilesDir(null), _fileName + ".json");
        try{
            InputStream in = new ByteArrayInputStream(_json.toString().getBytes());
            OutputStream out = new FileOutputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            out.write(data);
            in.close();
            out.close();
            success = true;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
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
