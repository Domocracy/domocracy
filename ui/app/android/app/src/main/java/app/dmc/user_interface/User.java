package app.dmc.user_interface;
import android.content.Context;

/**
 * Created by Joscormir on 02/03/2015.
 */
public class User {
    //-----------------------------------------------------------------------------------------------------------------
    public static void init(Context _context){
        assert sInstance == null;
        sInstance = new User(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static User get(){
        return sInstance;
    }


    //Private interface
    private User(Context _context){
        assert _context != null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static  User             sInstance = null;
}
