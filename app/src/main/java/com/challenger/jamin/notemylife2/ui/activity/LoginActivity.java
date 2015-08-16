package com.challenger.jamin.notemylife2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.challenger.jamin.notemylife2.Base.DataVar;
import com.challenger.jamin.notemylife2.Base.MyApplication;
import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.User;
import com.challenger.jamin.notemylife2.database.dao.UserDao;

/**
 * Created by jamin on 8/12/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText etAccount;
    private EditText etPassword;

    private long exitTime1 = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etAccount = (EditText)findViewById(R.id.user_account);
        etPassword = (EditText)findViewById(R.id.user_password);
        Button submitBtn = (Button) findViewById(R.id.user_login_submit_btn);
        submitBtn.setOnClickListener(this);

        //点击注册
        TextView tvRegister = (TextView)findViewById(R.id.user_register_tv);
        tvRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_login_submit_btn:
                UserDao userDao = new UserDao(LoginActivity.this);
                User user = userDao.getUserByAccountNpassword(etAccount.getText().toString(),
                        etPassword.getText().toString());
                if (user != null) {
                    //保存数据到本地
                    SharedPreferences sp = getSharedPreferences(DataVar.SP_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(DataVar.SP_USER_ID, user.getUserId());
                    editor.commit();
                    //保存至全局
                    MyApplication myApplication = (MyApplication) getApplication();
                    myApplication.setUser(user);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或用户名出错", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.user_register_tv:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    //按键监听
    @Override
    public boolean onKeyDown(int key, KeyEvent event){
        switch (key) {
            case KeyEvent.KEYCODE_BACK:
                //检测两次按返回键的时间间隔，若与上次相比大于两秒则只是提醒
                if (System.currentTimeMillis() - exitTime1 > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime1 = System.currentTimeMillis();
                } else {
                    //若两次按键时间小于2s,直接退出程序
                    System.exit(0);
                }
                break;
            default:
                break;
        }
        return false;
    }

}
