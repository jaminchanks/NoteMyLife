package com.challenger.jamin.notemylife2.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.challenger.jamin.notemylife2.Base.MyApplication;
import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.Diary;
import com.challenger.jamin.notemylife2.database.dao.DiaryDao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jamin on 7/29/15.
 */
public class DiaryEditActivity extends Activity implements View.OnClickListener{
    private EditText titleEdit;
    private EditText contentEdit;

    private DiaryDao diaryDao;
    Diary diary = null;
    TextView tvWeatherType;
    LinearLayout hiddenLayout;
    EditText etWeatherChange;

    boolean isHidden = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);


        //返回主界面
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        backIcon.setOnClickListener(this);

        //提交数据
        Button submitBtn = (Button) findViewById(R.id.submit_edit_btn);
        submitBtn.setOnClickListener(this);

        //时间和天气控件
        TextView tvDate = (TextView)findViewById(R.id.diary_edit_date);
        //设置当前时间
        if (diary == null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            tvDate.setText(format.format(date));
        } else {
            tvDate.setText(diary.getCreateTime());
        }


        //设置显示的标题
        TextView menuTitle = (TextView)findViewById(R.id.menu2_title);
        menuTitle.setText("编辑日志");

        //日志的标题和内容组件
        titleEdit = (EditText)findViewById(R.id.title_edit);
        contentEdit = (EditText)findViewById(R.id.content_edit);

        //获得要编辑的日志对象
        Bundle diaryBundle = getIntent().getExtras();
        if (diaryBundle != null) {
            Log.w("diaryEdit", "bundle is not null");
            diary = (Diary) diaryBundle.getSerializable("diaryItem");
            if (diary != null) {
                titleEdit.setText(diary.getTitle());
                contentEdit.setText(diary.getContent());
            }
        }

        //设置当前天气
        tvWeatherType = (TextView) findViewById(R.id.diary_edit_weatherType);
        tvWeatherType.setOnClickListener(this);
        if (diary == null) {
            MyApplication myApplication = (MyApplication)getApplication();
            String weatherType = myApplication.getWeather().getWeatherType();
            tvWeatherType.setText(weatherType);
        } else {
            tvWeatherType.setText(diary.getWeather());
        }



        //隐藏布局及其控件
        hiddenLayout = (LinearLayout) findViewById(R.id.hidden_weather_edit_layout);
        etWeatherChange = (EditText) findViewById(R.id.diary_edit_weather_change_et);
        Button btnWeatherChange = (Button) findViewById(R.id.diary_edit_weather_change_submit);
        btnWeatherChange.setOnClickListener(this);

        diaryDao = new DiaryDao(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_icon:
                if (!(TextUtils.isEmpty(titleEdit.getText()) && TextUtils.isEmpty(contentEdit.getText())))
                    showSaveDialog();
                else
                    finish();
                break;

            case R.id.submit_edit_btn:
                if (TextUtils.isEmpty(titleEdit.getText())) { //不允许标题为空
                    titleEdit.setHint("标题不能为空!");
                    titleEdit.setSelection(0);
                } else {
                    diaryDao.add(getDiary());
                    //startActivity(new Intent(EditDiaryActivity.this, MainActivity.class));
                    finish();
                }
                break;

            case R.id.diary_edit_weatherType:
                if (isHidden) {
                    hiddenLayout.setVisibility(View.VISIBLE);
                    etWeatherChange.setSelection(0);
                    isHidden = false;
                } else {
                    hiddenLayout.setVisibility(View.GONE);
                    isHidden = true;
                }
                break;

            case R.id.diary_edit_weather_change_submit:
                if (!TextUtils.isEmpty(etWeatherChange.getText())) {
                    tvWeatherType.setText(etWeatherChange.getText());
                    hiddenLayout.setVisibility(View.GONE);
                    isHidden = true;
                }
                break;
            default:
                break;

        }

    }

    private Diary getDiary() {
        if (diary == null) {
            diary = new Diary();
            String title = titleEdit.getText().toString();
            String content = contentEdit.getText().toString();
            String weather = tvWeatherType.getText().toString();
            diary.setTitle(title);
            diary.setWeather(weather);
            diary.setContent(content);
            diary.setCreateTime(new Timestamp(System.currentTimeMillis()));
            diary.setAddress("广东省广州市");
            diary.setBookId(1);
        }
        return diary;
    }

    /**
     * 对返回键进行监听
     * 若当前页面的数据不为空时，弹出对话框提示是否保存数据
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (!(TextUtils.isEmpty(titleEdit.getText()) && TextUtils.isEmpty(contentEdit.getText())))
                    showSaveDialog();
                else
                    finish();
                break;
            default:
                break;
        }
        return false;
    }


    private void showSaveDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("退出编辑");
        dialogBuilder.setMessage("是否保存当前内容?");
        dialogBuilder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //填写保存数据的相关处理代码
                diaryDao.add(getDiary());
                startActivity(new Intent(DiaryEditActivity.this, MainActivity.class));
            }
        });

        dialogBuilder.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


}
