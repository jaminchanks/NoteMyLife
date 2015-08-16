package com.challenger.jamin.notemylife2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.challenger.jamin.notemylife2.Base.DataVar;
import com.challenger.jamin.notemylife2.Base.MyApplication;
import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.User;
import com.challenger.jamin.notemylife2.database.dao.UserDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by jamin on 7/29/15.
 */
public class WelcomeActivity extends Activity {
    private static final int INIT_HEAD_RES_ID = R.drawable.me;
    private static User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new GetDataAsyncTask().execute(null, null);
    }


    class GetDataAsyncTask extends AsyncTask<Void, Void, Integer> {
        long startTime;
        long endTime;
        static final long MIN_SHOW_TIME = 800;
        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            endTime = System.currentTimeMillis();
            //读取用户设置
            currentUser = getCurrentLoginUser();
            //初始化文件夹
            initApp();

            //若读取数据时间少于MIN_SHOW_TIME则启动界面的时间为MIN_SHOW_TIME
            if (endTime - startTime < MIN_SHOW_TIME)
                SystemClock.sleep(MIN_SHOW_TIME);
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //完成后进入主界面
            // Log.w("currentUser", currentUser.toString());
            if (currentUser != null){
                //提取登陆信息并存进全局区域
                MyApplication myApplication = (MyApplication)getApplication();
                myApplication.setUser(currentUser);

                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
            WelcomeActivity.this.finish();
            //两个参数分别表示进入的动画,退出的动画
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    private void initApp() {
        mkdirs(DataVar.APP_ROOT_FILE);
        mkdirs(DataVar.APP_IMG_FILE);

        //头像在img目录下
        File imageFile1 = new File(DataVar.APP_IMG_FILE + File.separator + DataVar.INIT_HEAD_IMAGE_FILE_NAME);
        if (!imageFile1.exists()) {
            try {
                imageFile1.createNewFile();

                //初始化默认头像
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), INIT_HEAD_RES_ID);
                Bitmap.CompressFormat format= Bitmap.CompressFormat.PNG;
                OutputStream stream  = new FileOutputStream(
                        DataVar.APP_IMG_FILE + File.separator + DataVar.INIT_HEAD_IMAGE_FILE_NAME);
                bitmap.compress(format, 100, stream);

            }catch (Exception ex){
                ex.printStackTrace();
                Log.w("createInitImage", "fail");
            }
        }

    }

    private void mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    /**
     * 读取应用设置中的用户信息
     * @return : 返回当前已登陆的用户
     *
     * */
    private User getCurrentLoginUser() {
        User user = null;
        SharedPreferences sp = getSharedPreferences(DataVar.SP_FILE, MODE_PRIVATE);
        int userId = sp.getInt(DataVar.SP_USER_ID, -1);
        Log.w("userId1", userId + "");
        if ( userId > -1) {
            Log.w("userId2", userId + "");
            UserDao userDao = new UserDao(this);
            user = userDao.getUserById(userId);
            if (user != null)
                Log.w("userGetById", user.toString());
        }
        return user;
    }
}
