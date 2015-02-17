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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JsonRequest {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    public JsonRequest(String _url){
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
    public void setMethod(String _method){
        try {
            mConnection.setRequestMethod(_method);
        }catch(ProtocolException _protocolException){
            _protocolException.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setHeader(String _key, String _value){
        mConnection.setRequestProperty(_key, _value);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setBody(String _body){
        mConnection.setDoOutput(true);
        mConnection.setRequestProperty("Content-Length", Integer.toString(_body.length()));
        try {
            OutputStream os = mConnection.getOutputStream();
            os.write(_body.getBytes("UTF8"));
            os.close();
        }catch (IOException _ioException){
            Log.d("DOMOCRACY", "Could not send Command");
            _ioException.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void sendRequest(){
        try {
            int responseCode = mConnection.getResponseCode();
            System.out.println("\nSending 'PUT' request to URL : " + mUrl);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader( new InputStreamReader(mConnection.getInputStream()));
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
    // Private Interface

    //-----------------------------------------------------------------------------------------------------------------
    // Net
    HttpURLConnection   mConnection = null;
    URL                 mUrl = null;
}
