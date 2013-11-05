package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paeder on 11/5/13.
 */
public class CatDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cat_db";
    private static final int DATA_VERSION = 1;
    private static final String CREATE_CATS = String.format("CREATE_TABLE %s {"
    +" %s integer primary key autoincrement, "+"%s text not null }"


    );

    public CatDataBase(Context context){
        super(context,);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // execute scripts

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
