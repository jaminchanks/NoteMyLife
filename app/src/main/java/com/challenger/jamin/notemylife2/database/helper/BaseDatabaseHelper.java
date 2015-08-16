package com.challenger.jamin.notemylife2.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.challenger.jamin.notemylife2.Base.DataVar;
import com.challenger.jamin.notemylife2.database.table.BookTable;
import com.challenger.jamin.notemylife2.database.table.DiaryTable;
import com.challenger.jamin.notemylife2.database.table.UserTable;

import java.io.File;
import java.util.Objects;

/**
 * Created by jamin on 7/29/15.
 */
public class BaseDatabaseHelper extends SQLiteOpenHelper {
    private static BaseDatabaseHelper baseDatabaseHelper;
    private static String databaseName = "NoteMyLife.db";
    private static String extraDatabasePath = DataVar.APP_ROOT_FILE
            + File.separator + "db";
    private static final int DATABASE_VERSION = 11;

    private static final String CREATE_USER_TABLE_SQL = "create table " + UserTable.TABLE_NAME
            + "("
            + UserTable.USER_ID + " integer primary key autoincrement,"
            + UserTable.EMAIL + " text not null,"
            + UserTable.NICK_NAME + " text not null,"
            + UserTable.PASSWORD + " text not null,"
            + UserTable.HEAD + " text "
            + ");";

    private static final String CREATE_BOOK_TABLE_SQL = "create table " + BookTable.TABLE_NAME
            + "("
            + BookTable.BOOK_ID + " integer primary key autoincrement,"
            + BookTable.BOOK_NAME + " text not null"
            + ");";

    private static final String CREATE_DIARY_TABLE_SQL = "create table " + DiaryTable.TABLE_NAME
            + "("
            + DiaryTable.DIARY_ID + " integer primary key autoincrement,"
            + DiaryTable.TITLE + " text not null,"
            + DiaryTable.WEATHER + " text,"
            + DiaryTable.CONTENT + " text,"
            + DiaryTable.CREATE_TIME + " timestamp,"
            + DiaryTable.ADDRESS + " text,"
            + DiaryTable.BOOK_ID + " integer"
            + ");";


    public synchronized static BaseDatabaseHelper getInstance(Context context) {
        if (Objects.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            File file = new File(extraDatabasePath);
            if (!file.exists()) {
                file.mkdirs();
                databaseName = extraDatabasePath + File.separator + databaseName;
                Log.e("database", "extra");
            }
            else {
                //do something here
            }
        }

        if (baseDatabaseHelper == null)
            baseDatabaseHelper = new BaseDatabaseHelper(context);
        return baseDatabaseHelper;
    }

    //默认构造函数
    public BaseDatabaseHelper(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_SQL);
        db.execSQL(CREATE_BOOK_TABLE_SQL);
        db.execSQL(CREATE_DIARY_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteAllTables(db);
        onCreate(db);
    }

    private void deleteAllTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BookTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DiaryTable.TABLE_NAME);
    }
}
