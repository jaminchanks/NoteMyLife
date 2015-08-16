package com.challenger.jamin.notemylife2.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.challenger.jamin.notemylife2.bean.User;
import com.challenger.jamin.notemylife2.database.helper.BaseDatabaseHelper;
import com.challenger.jamin.notemylife2.database.table.UserTable;

import java.util.ArrayList;

/**
 * Created by jamin on 8/12/15.
 */
public class UserDao {
    private SQLiteDatabase db;

    public UserDao(Context context) {
        BaseDatabaseHelper baseDatabaseHelper = BaseDatabaseHelper.getInstance(context);
        db =  baseDatabaseHelper.getWritableDatabase();
    }

    /**
     * @return  boolean:若成功添加返回true,否则返回false
     * */
    public boolean add(User user) {
        Cursor cursor = db.query(UserTable.TABLE_NAME, null, UserTable.EMAIL + " = ?",
                new String[]{user.getEmail()}, null, null, null);

        if (cursor.getCount() != 0) {
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put(UserTable.EMAIL, user.getEmail());
            values.put(UserTable.NICK_NAME, user.getNickName());
            values.put(UserTable.PASSWORD, user.getPassword());
            db.insert(UserTable.TABLE_NAME, null, values);
            Log.w("addUser1", "OK");
            Log.w("addUser2", user.getEmail() + "//" + user.getPassword());
            return true;
        }
    }

    public User getUserById(int id) {
         User user = null;
         Cursor cursor = db.query(UserTable.TABLE_NAME, null,
                  UserTable.USER_ID + " = ?",
                  new String[]{id + ""},
                 null, null, null);
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(cursor.getColumnIndex(UserTable.USER_ID)),
                    cursor.getString(cursor.getColumnIndex(UserTable.EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UserTable.NICK_NAME)),
                    cursor.getString(cursor.getColumnIndex(UserTable.PASSWORD))
                    );
            //返回头像
            user.setHead(cursor.getString(cursor.getColumnIndex(UserTable.HEAD)));
        }
        cursor.close();
        Log.w("searchUserById", cursor.getCount() + "");
        return user;
    }


    /**
     * 根据账户和密码查询用户
     * @param email
     * @param password
     * @return User
     * */
    public User getUserByAccountNpassword(String email, String password) {
        User user = null;
         Cursor cursor = db.query(UserTable.TABLE_NAME, null,
                  UserTable.EMAIL + " = ? and " + UserTable.PASSWORD + " = ?",
                  new String[]{email, password},
                 null, null, null);
        Log.w("isCursorNull?", cursor.getCount() + "");
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(cursor.getColumnIndex(UserTable.USER_ID)),
                    cursor.getString(cursor.getColumnIndex(UserTable.EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UserTable.NICK_NAME)),
                    cursor.getString(cursor.getColumnIndex(UserTable.PASSWORD))
                    );
            //返回头像
            user.setHead(cursor.getString(cursor.getColumnIndex(UserTable.HEAD)));
        }
        cursor.close();
        Log.w("searchUserByEmailNPass", email + "//" + password);
        Log.w("searchUserByEmailNPass", cursor.getCount() + "");
        return user;
    }

    public int delete(User User) {
        int index = User.getUserId();
        return db.delete(UserTable.TABLE_NAME, UserTable.USER_ID + " = ?", new String[]{index + ""});
    }


    public int updateUser(User user) {
        int index = user.getUserId();
        ContentValues values = new ContentValues();
        values.put(UserTable.NICK_NAME, user.getNickName());
        values.put(UserTable.PASSWORD, user.getPassword());
        values.put(UserTable.HEAD, user.getHead());
        return db.update(UserTable.TABLE_NAME, values, UserTable.USER_ID + " = ?", new String[]{index + ""});
    }


    public ArrayList<User> getAllusersByPage() {
        return null;
    }
}

