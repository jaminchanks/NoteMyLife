package com.challenger.jamin.notemylife2.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.ChatMessage;

import java.util.List;

/**
 * Created by jamin on 8/8/15.
 */
public class ChatAdapter extends BaseAdapter {
   // private Context context;
    private LayoutInflater inflater;

    private List<ChatMessage> chatMessages;
    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
      //  this.context = context;
        this.chatMessages = chatMessages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ChatMessage chatMessage = chatMessages.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item_chat, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout)convertView.findViewById(R.id.send_by_robot_layout);
            viewHolder.headRobot = (ImageView)convertView.findViewById(R.id.head_robot);
            viewHolder.contentRobot = (TextView)convertView.findViewById(R.id.send_by_robot_content);

            viewHolder.rightLayout = (LinearLayout)convertView.findViewById(R.id.send_by_me_layout);
            viewHolder.headMe = (ImageView)convertView.findViewById(R.id.head_me);
            viewHolder.contentMe = (TextView)convertView.findViewById(R.id.send_by_me_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (chatMessage.getType() == ChatMessage.TYPE_RECEIVE) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.headRobot.setImageResource(R.drawable.robot_showdown);
            viewHolder.contentRobot.setText(chatMessage.getContent());
        } else if (chatMessage.getType() == ChatMessage.TYPE_SEND) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.headMe.setImageResource(R.drawable.me);
            viewHolder.contentMe.setText(chatMessage.getContent());
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout leftLayout;
        ImageView headRobot;
        TextView contentRobot;

        LinearLayout rightLayout;
        ImageView headMe;
        TextView contentMe;

    }

}
