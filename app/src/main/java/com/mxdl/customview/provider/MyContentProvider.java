package com.mxdl.customview.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        Log.v("MYTAG", "delete");
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        Log.v("MYTAG", "getType");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v("MYTAG", "insert...");
        return null;
    }

    @Override
    public boolean onCreate() {
        Log.v("MYTAG", "onCreate...");
        Log.v("MYTAG", "currThread:" + Thread.currentThread().getName());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.v("MYTAG", "query...");
        Log.v("MYTAG", "currThread:" + Thread.currentThread().getName());
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v("MYTAG", "update...");
        return 0;
    }
}
