package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by paeder on 11/5/13.
 */
public class DribbleDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dribble_db";
    public static final String URI_THUMB = "_thumb";
    public static final String URI_LARGE = "_large";
    public static final String DRIBBLE_TITLE = "_title";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_DRIBBLE_TABLE = String.format(
            "CREATE TABLE %s ("
            + "%s INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "%s TEXT NOT NULL, "
            + "%s TEXT NOT NULL, "
            + "%s TEXT NOT NULL);",
            DribbleContract.Dribble.TABLE_NAME,
            DribbleContract.Dribble._ID,
            DribbleContract.Dribble.DRIBBLE_TITLE,
            DribbleContract.Dribble.DRIBBLE_URL,
            DribbleContract.Dribble.DRIBBLE_TEASER);

//    private static final String CREATE_DRIBBLE_TABLE = "create table "
//            + DATABASE_NAME + "(" + DribbleContract.Dribble._ID +" integer primary key autoincrement, "
//            + URI_THUMB + " text not null, "
//            + URI_LARGE + " text not null, "
//            + DRIBBLE_TITLE + " text not null " + ");";

    public DribbleDataBase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // execute scripts
        sqLiteDatabase.execSQL(CREATE_DRIBBLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
