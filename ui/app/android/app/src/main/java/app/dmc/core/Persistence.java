package app.dmc.core;

import android.content.Context;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joscormir on 16/02/2015.
 */

public class Persistence {
    //-----------------------------------------------------------------------------------------------------------------
    public static void init(Context _context){
        assert sInstance == null;
        sInstance = new Persistence(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static Persistence get(){
        return sInstance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject getJSON(String _fileName){
        if (mFiles.containsKey(_fileName)){
            return mFiles.get(_fileName);
        }else {
            return null;
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    public boolean putJSON(String _fileName, JSONObject _jsonToInsert){
        if (mFiles.containsKey(_fileName)){
            mFiles.put(_fileName,_jsonToInsert);
            return true;
        }else if(!mFiles.containsKey(_fileName)){
            mFiles.put(_fileName,_jsonToInsert);
            return true;
        }else{
            return false;
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    private JSONObject loadFile(String _fileName){
       JSONObject json = null;
        File file = new File(mContext.getExternalFilesDir(null), _fileName + ".json");
            try {
                InputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] data = new byte[in.available()];
                in.read(data);
                out.write(data);
                String outToString = new String(out.toByteArray());
                json = new JSONObject(outToString);
            } catch (Exception e) {
                e.printStackTrace();
            }
       return json;
    }

    //-----------------------------------------------------------------------------------------------------------------
    private boolean saveFile(String _fileName, JSONObject _json){
        boolean success = false;
        File file = new File(mContext.getExternalFilesDir(null), _fileName + ".json");
        try{
            InputStream in = new ByteArrayInputStream(_json.toString().getBytes());
            OutputStream out = new FileOutputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            out.write(data);
            in.close();
            out.close();
            success = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return success;
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void updateFilesMap (String _fileName){
        mFiles.put(_fileName,loadFile(_fileName));
    }

    //-----------------------------------------------------------------------------------------------------------------
    //Private interface
    private Persistence(Context _context){
            assert _context != null;
            mContext = _context;
            mFiles = new HashMap<String,JSONObject>();//nor need to be initialized here.
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static  Persistence             sInstance = null;
    private         Context                 mContext;
    private         Map<String, JSONObject> mFiles;

}
