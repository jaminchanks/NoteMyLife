package com.challenger.jamin.notemylife2.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.ChatMessage;
import com.challenger.jamin.notemylife2.net.ChatWithRobot;
import com.challenger.jamin.notemylife2.ui.adapter.ChatAdapter;

import java.util.ArrayList;

/**
 * Created by jamin on 8/7/15.
 */
public class RobotFragment extends Fragment {
    private static final int GET_ROBOT_RESPONSE = 1;
    private ListView chatListview;
    private ArrayList<ChatMessage> chatMessageList;
    private ChatAdapter chatAdapter;
    private EditText inputEditText;

    private static String inputStr; //用户的输入内容
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message){
            switch (message.what) {
                case GET_ROBOT_RESPONSE:
                    ChatMessage chatMessage = (ChatMessage)message.obj;
                    chatMessageList.add(chatMessage);
                    chatAdapter.notifyDataSetChanged();
                    chatListview.setSelection(chatMessageList.size());
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatMessageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(getActivity(), chatMessageList);
        //延迟发送机器人问候语
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatMessage chatMessage0 = new ChatMessage("有什么可以帮助到您的吗?", ChatMessage.TYPE_RECEIVE);
                chatMessageList.add(chatMessage0);
                chatAdapter.notifyDataSetChanged();
            }
        }, 500);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View chatLayout = inflater.inflate(R.layout.frag_robot, null);
        chatListview = (ListView) chatLayout.findViewById(R.id.chat_listview);
        chatListview.setAdapter(chatAdapter);

        Button btnSendMessage = (Button) chatLayout.findViewById(R.id.message_send_btn);
        btnSendMessage.setOnClickListener(new myOnclickListener());

        inputEditText = (EditText)chatLayout.findViewById(R.id.message_input_content);
        return chatLayout;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //事件点击监听器
    class myOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.message_send_btn :
                    //这里考虑一个问题，保存上次的聊天历史？
                    inputStr = inputEditText.getText().toString();
                    if (!TextUtils.isEmpty(inputStr)) {
                        ChatMessage messageSend = new ChatMessage(inputStr,
                                ChatMessage.TYPE_SEND);
                        chatMessageList.add(messageSend);
                        chatAdapter.notifyDataSetChanged();
                        chatListview.setSelection(chatMessageList.size());
                        inputEditText.setText("");
                        //请求被机器人响应
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ChatMessage chatMessage = new ChatMessage(
                                        ChatWithRobot.getResponse(inputStr),
                                        ChatMessage.TYPE_RECEIVE);
                                Message handlerMessage = new Message();
                                handlerMessage.what = GET_ROBOT_RESPONSE;
                                handlerMessage.obj = chatMessage;
                                handler.sendMessage(handlerMessage);
                            }
                        }).start();

                    }
                    break;
            }
        }
    }
}
