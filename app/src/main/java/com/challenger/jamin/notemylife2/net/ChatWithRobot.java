package com.challenger.jamin.notemylife2.net;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jamin on 8/8/15.
 */
public class ChatWithRobot {
    private static String responses[] = {"主人,我有事去了趟Kepler-452b星球,从地球到这里的信号不好,你不要太想我哦~",
            "主人,我听不清你说什么...我去拯救地球了,现在没空跟你聊天了啦",
            "主人,我去泡妹纸了...",
            "主人,我不在的时候希望你不会太寂寞...",
            "您所呼叫的用户现在不在服务区中,请稍后再呼叫..."
    };

    static int requestTime = -1;


    /**发送用户问题返回答案
     *解析的json格式大致为：
     * {"code":100000,"text":"你好啊！"}
     *
     * @param question 用户发出的问题
     * @return 机器人回答的问题
     *
     * */
    public static String getResponse(String question) {
        requestTime += 1;
        HttpURLConnection connection = null;
        try {
            String APIKEY = "b3da931effefa884ba33e562fbc61f98";
            String INFO = URLEncoder.encode(question, "utf-8");
            String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
            URL getUrl = new URL(getURL);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();

            // 取得输入流，并使用Reader读取
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(),"utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            Log.w("codeInt", jsonObject.getInt("code") + "");
            //能正确返回答案的情况下返回码为100000
            return jsonObject.getString("text");

        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 断开连接
            if (connection != null)
                connection.disconnect();
        }
        if (requestTime < 3)
            return responses[requestTime];
        else return responses[4];
    }
}
