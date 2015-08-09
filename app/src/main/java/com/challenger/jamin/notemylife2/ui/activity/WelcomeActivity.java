package com.challenger.jamin.notemylife2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.challenger.jamin.notemylife2.R;

/**
 * Created by jamin on 7/29/15.
 */
public class WelcomeActivity extends Activity {
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
            //若读取数据时间少于MIN_SHOW_TIME则启动界面的时间为MIN_SHOW_TIME
            if (endTime - startTime < MIN_SHOW_TIME)
                SystemClock.sleep(MIN_SHOW_TIME);
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //完成后进入主界面
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
            //两个参数分别表示进入的动画,退出的动画
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
