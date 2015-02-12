///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


package app.dmc.devices;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonRequest {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    JsonRequest(String _url, JSONObject _json){
        mHeaders = new HashMap<String, String>();

        decodeJson(_json);
        initConnection(_url);
        setUpRequest();
        sendRequest();
    }

    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
    private void initConnection(String _url){
        try {
            // Create new URL. This class check if string is properly written, and can create a connection from it.
            mUrl = new URL(_url);

            // Open a connection with the given URL.
            mConnection = (HttpURLConnection) mUrl.openConnection();
            if (mConnection == null) {
                Log.d("DOMOCRACY", "Fail opening URL: " + _url);
                return;
            }
        }catch (MalformedURLException eMalformed){
            eMalformed.printStackTrace();
        }catch (IOException eIOException){
            eIOException.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void decodeJson(JSONObject _json){
        try {
            // Get method type
            mMethod = _json.getString("method");
            JSONObject headers = _json.getJSONObject("headers");

            // Get headers
            Iterator<?> keys = headers.keys();
            while(keys.hasNext()){
                String key = (String)keys.next();
                mHeaders.put(key, headers.getString(key));
            }
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    private void setUpRequest(){
        try {
            // Add Method
            mConnection.setRequestMethod("GET");

            //Add headers
            for(String key: mHeaders.keySet()){
                mConnection.setRequestProperty(key, mHeaders.get(key));
            }

        }catch (ProtocolException _protocolException){
            _protocolException.printStackTrace();
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    private void sendRequest(){
        try {
            int responseCode = mConnection.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + mUrl);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(mConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        }catch (IOException _ioException){
            _ioException.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Net
    HttpURLConnection   mConnection = null;
    URL                 mUrl = null;

    // Http
    String mMethod = null;
    Map<String, String> mHeaders = null;

}
