package app.dmc.core;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	public static void end() {
		sInstance = null;
	}

    //-----------------------------------------------------------------------------------------------------------------
    public static Persistence get(){
        return sInstance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject getJSON(String _fileName){
        if (mFiles.containsKey(_fileName)){
            return mFiles.get(_fileName);
        }else{
            if (updateFilesMap(_fileName)) {
                return mFiles.get(_fileName);
            } else {
                return null;
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public boolean putJSON(String _fileName, JSONObject _jsonToInsert){
        mFiles.put(_fileName,_jsonToInsert);
        return true;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public boolean removeJSON(String _fileName){
        mFiles.remove(_fileName);
        mFilesToDelete.add(_fileName);
        return true;
    }

    //-----------------------------------------------------------------------------------------------------------------
    private JSONObject loadJSONFile(String _fileName) {
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
    private boolean saveJSONFile(String _fileName, JSONObject _json){
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
    private boolean deleteJSONFile(String _fileName){
		File dir = mContext.getExternalFilesDir(null);
		Log.d("Persistence", dir.toString());
		File file = new File(dir, _fileName + ".json");
        return file.delete();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private boolean updateFilesMap (String _fileName){
		File dir = mContext.getExternalFilesDir(null);
		Log.d("Persistence", dir.toString());
		File file = new File(dir, _fileName + ".json");
        if (file.exists()) {
            mFiles.put(_fileName, loadJSONFile(_fileName));
            return true;
        }else{
           return false;
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    public void flush(){
        for(String deleteIterator : mFilesToDelete){
            deleteJSONFile(deleteIterator);
        }

        for(String filesIterator : mFiles.keySet()){
            saveJSONFile(filesIterator,mFiles.get(filesIterator));
        }
    }

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	protected void finalize() throws Throwable {
		flush();
		super.finalize();
	}

    //Private interface
    private Persistence(Context _context){
            assert _context != null;
            mContext        = _context;
            mFiles          = new HashMap<String,JSONObject>();
            mFilesToDelete  = new HashSet<String>();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static  Persistence             sInstance = null;
    private         Context                 mContext;
    private         Map<String, JSONObject> mFiles;
    private         Set<String>             mFilesToDelete;

}
