package com.challenger.jamin.notemylife2.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.Diary;

/**
 * Created by jamin on 8/3/15.
 */
public class DiaryDetailActivity extends Activity implements View.OnClickListener {
    FrameLayout doSomethingLayout;
    private static boolean isDoSomethingVisible = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_detail);

        //返回主界面
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        backIcon.setOnClickListener(this);

        //设置显示的标题
        TextView menuTitle = (TextView)findViewById(R.id.menu2_title);
        menuTitle.setText("编辑");

        //底部操作菜单
        doSomethingLayout = (FrameLayout)findViewById(R.id.do_something_layout);

        TextView titleView = (TextView)findViewById(R.id.title_detail);
        TextView contentView = (TextView)findViewById(R.id.content_detail);
        contentView.setOnClickListener(this);

        //从来源处读取日志对象
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Diary diary = (Diary) bundle.getSerializable("diaryItem");
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
            default:
                if (!isDoSomethingVisible) {
                    doSomethingLayout.setVisibility(View.VISIBLE);
                    isDoSomethingVisible = true;
                } else {
                    doSomethingLayout.setVisibility(View.GONE);
                    isDoSomethingVisible = false;
                }
                break;
        }
    }
}
