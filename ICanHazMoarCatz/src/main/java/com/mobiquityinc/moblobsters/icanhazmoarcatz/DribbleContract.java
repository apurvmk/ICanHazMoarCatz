package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by paeder on 11/5/13.
 */
public class DribbleContract {

    public static final String AUTHORITY = "com.mobiquityinc.moblobsters.icanhazmoarcatz.DribbleProvider";

    public static final class Dribble implements BaseColumns{

        public static final String DATABASE_NAME = "dribble_db";
        public static final String DRIBBLE_ID = "_id";
        public static final String DRIBBLE_TITLE = "_title";


        public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DATABASE_NAME);
        //public static Uri URI_LARGE = Uri.parse("content://"+AUTHORITY+"/"+DATABASE_NAME);

        public static String CONTRACT_TYPE_DIR = "vnd.mobiquityinc.cursor.dir/vnd.mobiquityinc.dribble";
        public static String CONTRACT_TYPE_ITEM = "vnd.mobiquityinc.cursor.item/vnd.mobiquityinc.dribble";

        public static final HashMap<String,String> mProjectionMap = new HashMap<String, String>();
        static{
            mProjectionMap.put(_ID,_ID);
            mProjectionMap.put(DRIBBLE_ID,DRIBBLE_ID);
            mProjectionMap.put(DRIBBLE_TITLE,DRIBBLE_TITLE);

        }



    }


}