///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//      Example of device for testing.

package app.dmc.devices;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import app.dmc.Hub;

public class SwitchDevice implements  Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public SwitchDevice(Hub _hub){
        mHub = _hub;
    }

    @Override
    public View view(Context _context) {
        if(mView == null){
            LinearLayout base = new LinearLayout(_context);
            Button button = new Button(_context);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    run();
                }
            });

            base.addView(button);
            mView = base;
        }
        return mView;
    }

    @Override
    public void run() {
        /*Send Command*/
        Thread requestThread = new Thread( new Runnable() {
            @Override
            public void run() {
                String url = "http://10.100.2.45";
                HttpURLConnection con = null;
                try {
                    URL obj = new URL(url);

                    con = (HttpURLConnection) obj.openConnection();
                    if(con == null)
                        return;
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
        });
        requestThread.start();
    }

    @Override
    public String name() {
        return mId;
    }

    @Override
    public String id() {
        return mName;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
    private String mName;
    private String mId;
    private Hub mHub;

    private View mView;

}
