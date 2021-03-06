package com.challenger.jamin.notemylife2.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.challenger.jamin.notemylife2.bean.Diary;
import com.challenger.jamin.notemylife2.database.helper.BaseDatabaseHelper;
import com.challenger.jamin.notemylife2.database.table.DiaryTable;

import java.util.ArrayList;

/**
 * Created by jamin on 7/29/15.
 */
public class DiaryDao {
    private SQLiteDatabase db;
    private ArrayList<Diary> diaries;

    public DiaryDao(Context context) {
        BaseDatabaseHelper baseDatabaseHelper = BaseDatabaseHelper.getInstance(context);
        db =  baseDatabaseHelper.getWritableDatabase();
    }


    public void add(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(DiaryTable.TITLE, diary.getTitle());
        values.put(DiaryTable.WEATHER, diary.getWeather());
        values.put(DiaryTable.CONTENT, diary.getContent());
        values.put(DiaryTable.CREATE_TIME, diary.getCreateTime().toString());
        values.put(DiaryTable.ADDRESS, diary.getAddress());
        values.put(DiaryTable.BOOK_ID, diary.getBookId());
        db.insert(DiaryTable.TABLE_NAME, null, values);
        Log.w("executeSQL", "OK");
//        Toast.makeText(context, "添加数据成功", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Diary> getAllDiaries() {
        diaries = new ArrayList<>();
        Cursor cursor = db.query(DiaryTable.TABLE_NAME, null, null, null,null, null, DiaryTable.CREATE_TIME + " desc");
        //获得各个字段的列数
        int diaryIdIndex = cursor.getColumnIndex(DiaryTable.DIARY_ID);
        int titleIndex = cursor.getColumnIndex(DiaryTable.TITLE);
        int weatherIndex = cursor.getColumnIndex(DiaryTable.WEATHER);
        int contentIndex = cursor.getColumnIndex(DiaryTable.CONTENT);
        int createTimeIdex = cursor.getColumnIndex(DiaryTable.CREATE_TIME);
        int addressIndex = cursor.getColumnIndex(DiaryTable.ADDRESS);
        int bookIndex = cursor.getColumnIndex(DiaryTable.BOOK_ID);

        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            Diary diary = new Diary();
            diary.setDiaryId(cursor.getInt(diaryIdIndex));
            diary.setTitle(cursor.getString(titleIndex));
            diary.setWeather(cursor.getString(weatherIndex));
            diary.setContent(cursor.getString(contentIndex));
            diary.setCreateTime(cursor.getString(createTimeIdex));
            diary.setAddress(cursor.getString(addressIndex));
            diary.setBookId(cursor.getInt(bookIndex));
            diaries.add(diary);
        }
        return diaries;
    }

    public int delete(Diary diary) {
        int index = diary.getDiaryId();
       return db.delete(DiaryTable.TABLE_NAME, DiaryTable.DIARY_ID + " = ?", new String[]{index + ""});
    }


    public ArrayList<Diary> getAllDiariesByPage() {
        return null;
    }
}
