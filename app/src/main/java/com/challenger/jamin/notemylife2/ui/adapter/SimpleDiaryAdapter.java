package com.challenger.jamin.notemylife2.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.Diary;

import java.util.ArrayList;

/**
 * Created by jamin on 7/26/15.
 */
public class SimpleDiaryAdapter extends BaseAdapter {
    ArrayList<Diary> diaries;
    LayoutInflater inflater;
    Context context;
    public SimpleDiaryAdapter(Context context, ArrayList<Diary> diaries) {
        inflater = LayoutInflater.from(context);
        this.diaries = diaries;
        this.context = context;
    }

    @Override
    public int getCount() {
        return diaries.size();
    }

    @Override
    public Diary getItem(int position) {
        return diaries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item_main, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.diary_title);
            viewHolder.content = (TextView)convertView.findViewById(R.id.diary_content);
            viewHolder.createTime = (TextView)convertView.findViewById(R.id.diary_create_time);
            viewHolder.address = (TextView)convertView.findViewById(R.id.diary_address);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Diary diary = diaries.get(position);
        viewHolder.title.setText(diary.getTitle());
        String limitContent = diary.getContent();
        if (limitContent.length() > 120) {  //对显示的字数有要求（120）
            limitContent = limitContent.substring(0, 119);
        }
        viewHolder.content.setText("\t" + limitContent + "......");
        viewHolder.createTime.setText(diary.getCreateTime());
        viewHolder.address.setText(diary.getAddress());

      //  viewHolder.content.setOnClickListener(new myOnItemSelectListener(diary));
        return convertView;
    }

/*
    class myOnItemSelectListener implements View.OnClickListener {
        Diary diary;
        myOnItemSelectListener(Diary diary) {
            this.diary = diary;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("diaryItem", diary);
            Intent intent = new Intent(context, DiaryDetailActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
*/

    class ViewHolder {
        TextView title;
        TextView content;
        TextView createTime;
        TextView address;
    }
}
