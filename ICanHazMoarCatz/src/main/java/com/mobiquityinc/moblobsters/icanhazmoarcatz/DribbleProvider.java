package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.net.URI;

/**
 * Created by paeder on 11/5/13.
 */
public class DribbleProvider extends ContentProvider {

    public static final String AUTHORITY = "com.mobiquityinc.moblobsters.icanhazmoarcatz.DribbleProvider";
    private DribbleDataBase dataBase;

    public static final int dribbleDir = 10;
    public static final int dribbleItem = 20;

    private static final UriMatcher mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        mMatcher.addURI(AUTHORITY, "dribble_db", dribbleDir);
        mMatcher.addURI(AUTHORITY,"dribble_db/#", dribbleItem);
    }

    @Override
    public boolean onCreate() {
        dataBase = new DribbleDataBase(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (mMatcher.match(uri)){

            case dribbleDir:
            {
                return DribbleContract.Dribble.CONTRACT_TYPE_DIR;
            }

            case dribbleItem:
            {
                return DribbleContract.Dribble.CONTRACT_TYPE_ITEM;
            }

            default:
            {
                throw new IllegalArgumentException("uri not defined "+uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        long id;
        SQLiteDatabase db =  dataBase.getWritableDatabase();
        id = db.insert(DribbleContract.Dribble.TABLE_NAME,null,contentValues);
        getContext().getContentResolver().notifyChange(uri,null);
        Log.i("PRA","inserted id: "+id);

        return ContentUris.withAppendedId(DribbleContract.Dribble.CONTENT_URI,id);

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db =  dataBase.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DribbleContract.Dribble.TABLE_NAME);
        builder.setProjectionMap(DribbleContract.Dribble.mProjectionMap);

        switch (mMatcher.match(uri)){
            case dribbleItem:
            {
                builder.appendWhere(String.format(DribbleContract.Dribble._ID + " %s", uri.getLastPathSegment()));
                break;
            }
        }
        return builder.query(db,projection,selection,selectionArgs,null,null,sortOrder);

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }


}
