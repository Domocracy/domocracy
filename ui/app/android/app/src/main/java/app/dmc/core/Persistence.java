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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public JSONObject getJSON(String _path){
        JSONObject json = new JSONObject();
        List<String> fileLevels = decodePath(_path);

        if (!mFiles.containsKey(fileLevels.get(0))){
            updateFilesMap(fileLevels.get(0));
            json = mFiles.get(fileLevels.get(0));
        }else{
            json = mFiles.get(fileLevels.get(0));
        }

        for(int i = 1; i < fileLevels.size(); i++){
            try {
                return json.getJSONObject(fileLevels.get(i));


            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    //-----------------------------------------------------------------------------------------------------------------
    public boolean putJSON(String _path, JSONObject _jsonToInsert){
        JSONObject jsonToChange = getJSON(_path);
        List<String> fileLevels = decodePath(_path);

        for(int i = 0; i < fileLevels.size(); i++){
            try {

                if (jsonToChange.getString(fileLevels.get(i)) != _jsonToInsert.getString(fileLevels.get(i))) {
                    jsonToChange.put(fileLevels.get(i), _jsonToInsert.getJSONObject(fileLevels.get(i)));
                    return true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    private List decodePath (String _path){
        List<String> fileLevels = new ArrayList<String>();

        String level = "";
        for(int i = 1 ; i < _path.length()+1 ; i++){
            if (i < _path.length() && _path.charAt(i) != '/') {
                level = level + _path.charAt(i);
            }else {
                fileLevels.add(level);
                level = "";
            }
        }
    return fileLevels;
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
