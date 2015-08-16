package com.challenger.jamin.notemylife2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.Diary;

/**
 * Created by jamin on 8/3/15.
 */
public class DiaryDetailActivity extends Activity implements View.OnClickListener {
    FrameLayout hiddenLayout;
    Bundle diaryBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        //返回主界面
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        backIcon.setOnClickListener(this);

        //设置显示的标题
        TextView menuTitle = (TextView)findViewById(R.id.menu2_title);
        menuTitle.setText("日志详情");


        //标题和内容
        TextView titleView = (TextView)findViewById(R.id.title_detail);
        TextView contentView = (TextView)findViewById(R.id.content_detail);
        contentView.setOnClickListener(this);

        ScrollView scrollView = (ScrollView) findViewById(R.id.diary_detail_scrollView);
        scrollView.setClickable(true);
        scrollView.setOnClickListener(this);

        //底部隐藏布局
        hiddenLayout = (FrameLayout)findViewById(R.id.hidden_diary_deatil_layout);
        LinearLayout deleteLayout = (LinearLayout) findViewById(R.id.diary_detail_delete);
        deleteLayout.setOnClickListener(this);
        LinearLayout addLayout = (LinearLayout) findViewById(R.id.diary_detail_add);
        addLayout.setOnClickListener(this);
        LinearLayout editLayout = (LinearLayout) findViewById(R.id.diary_detail_edit);
        editLayout.setOnClickListener(this);


        //从来源处读取日志对象
        diaryBundle = getIntent().getExtras();
        if (diaryBundle != null) {
            Diary diary = (Diary) diaryBundle.getSerializable("diaryItem");
            if (diary != null) {
                titleView.setText(diary.getTitle());
                contentView.setText(diary.getContent());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_icon:
                finish();
                break;

            case R.id.diary_detail_delete:
                // TODO: 8/16/15
                
                break;
            
            case R.id.diary_detail_add:
                startActivity(new Intent(DiaryDetailActivity.this, DiaryEditActivity.class));
                break;
            
            case R.id.diary_detail_edit:
                Intent editIntent = new Intent(DiaryDetailActivity.this, DiaryEditActivity.class);
                editIntent.putExtras(diaryBundle);
                startActivity(editIntent);
                break;
            
            default:
                if (hiddenLayout.getVisibility() == View.VISIBLE) {
                    hiddenLayout.setVisibility(View.GONE);
                } else if (hiddenLayout.getVisibility() == View.GONE) {
                    hiddenLayout.setVisibility(View.VISIBLE);
                }
                Log.w("default", "onClick");
                break;
        }
    }
}
