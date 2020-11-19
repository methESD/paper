package com.example.paper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserProfile.db";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.users.TABLE_NAME + " (" +
                    UserProfile.users._ID + " INTEGER PRIMARY KEY," +
                    UserProfile.users.COLUMN_NAME_1 + " TEXT," +
                    UserProfile.users.COLUMN_NAME_2 + " TEXT," +
                    UserProfile.users.COLUMN_NAME_3 + " TEXT," +
                    UserProfile.users.COLUMN_NAME_4 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.users.TABLE_NAME;


    public long addInfo(String userName, String dob, String password, String gender){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

         // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserProfile.users.COLUMN_NAME_1, userName);
        values.put(UserProfile.users.COLUMN_NAME_2, dob);
        values.put(UserProfile.users.COLUMN_NAME_3, password);
        values.put(UserProfile.users.COLUMN_NAME_4,gender);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserProfile.users.TABLE_NAME, null, values);

        return newRowId;

}

    public boolean updateInfor(String userName, String dob, String password, String gender){
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(UserProfile.users.COLUMN_NAME_2, dob);
        values.put(UserProfile.users.COLUMN_NAME_3, password);
        values.put(UserProfile.users.COLUMN_NAME_4, gender);

         // Which row to update, based on the title
        String selection = UserProfile.users.COLUMN_NAME_1 + " LIKE ?";
        String[] selectionArgs = { "MyOldTitle" };

        int count = db.update(
            UserProfile.users.TABLE_NAME,
            values,
            selection,
            selectionArgs);

        if (count >= 1){
            return true;
        }else {
            return false;
        }
}

    public void deleteInfo(String username){
        SQLiteDatabase db = getWritableDatabase();
        // Define 'where' part of query.
        Object users;
        String selection = UserProfile.users.COLUMN_NAME_2 + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { "MyTitle" };
        // Issue SQL statement.
        int deletedRows = db.delete(UserProfile.users.TABLE_NAME, selection, selectionArgs);
    }

    public List readAllInfor(){

        String username = "meth";
        SQLiteDatabase db =getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.users.COLUMN_NAME_1,
                UserProfile.users.COLUMN_NAME_2,
                UserProfile.users.COLUMN_NAME_3,
                UserProfile.users.COLUMN_NAME_4,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.users.COLUMN_NAME_1+ " = ?";
        String[] selectionArgs = { username };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.users.COLUMN_NAME_1 + " ASC";

        Cursor cursor = db.query(
                UserProfile.users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List usernames = new ArrayList<>();
        while(cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow( UserProfile.users.COLUMN_NAME_1));
            usernames.add(user);
        }
        cursor.close();
        return usernames;
    }

    public List readAllInfor(String username){

        SQLiteDatabase db =getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.users.COLUMN_NAME_1,
                UserProfile.users.COLUMN_NAME_2,
                UserProfile.users.COLUMN_NAME_3,
                UserProfile.users.COLUMN_NAME_4,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.users.COLUMN_NAME_1+ "Like  = ?";
        String[] selectionArgs = { username };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.users.COLUMN_NAME_1 + " ASC";

        Cursor cursor = db.query(
                UserProfile.users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause,          // The values for the WHERE clause
                selectionArgs,                   // don't group the rows
                null,
                null,           // don't filter by row groups
                sortOrder               // The sort order
        );

        List userinfo = new ArrayList<>();
        while(cursor.moveToNext()) {
            String uname = cursor.getString(cursor.getColumnIndexOrThrow( UserProfile.users.COLUMN_NAME_1));
            String dnob = cursor.getString(cursor.getColumnIndexOrThrow( UserProfile.users.COLUMN_NAME_2));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow( UserProfile.users.COLUMN_NAME_3));
            String gend = cursor.getString(cursor.getColumnIndexOrThrow( UserProfile.users.COLUMN_NAME_4));

            userinfo.add(uname);
            userinfo.add(dnob);
            userinfo.add(pass);
            userinfo.add(gend);
        }
        cursor.close();
        return userinfo;
    }
}

