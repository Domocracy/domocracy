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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class JsonRequest {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    JsonRequest(String _url, JSONObject _json){
        decodeRequest(_json);

        initConnection(_url);

        try {
            con.setRequestMethod("GET");
            //add request header
            //con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        }catch (MalformedURLException eMalformed){
            eMalformed.printStackTrace();
        }catch (IOException eIOException){
            eIOException.printStackTrace();
        }
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
            mMethod = _json.getString("method");
            JSONArray headers = _json.getJSONArray("headers");

        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
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
