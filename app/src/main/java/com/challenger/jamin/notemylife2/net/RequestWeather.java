package com.challenger.jamin.notemylife2.net;

import android.util.Log;

import com.challenger.jamin.notemylife2.bean.Weather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jamin on 8/7/15.
 *
 * JSON个返回格式为：
 * {
 "errNum": 0,
 "errMsg": "success",
 "retData": {
 "city": "北京",
 "pinyin": "beijing",
 "citycode": "101010100",
 "date": "15-08-07",
 "time": "11:00",
 "postCode": "100000",
 "longitude": 116.391,
 "latitude": 39.904,
 "altitude": "33",
 "weather": "多云",
 "temp": "30",
 "l_tmp": "23",
 "h_tmp": "30",
 "WD": "无持续风向",
 "WS": "微风(<10m/h)",
 "sunrise": "05:18",
 "sunset": "19:22"
 }
 }
 *
 *
 */
public class RequestWeather {
    static Weather weather;
    private static final String HTTP_URL1 = "http://apis.baidu.com/apistore/weatherservice/cityname";
    private static final String HTTP_URL2 = "http://apis.baidu.com/apistore/weatherservice/recentweathers";

    public static Weather getWeather(String cityName) {
        return getWeather1(cityName);
    }

    //根据URL1网络请求的解析方式
    private static Weather getWeather1(String cityName){
        try {
            String httpArg = "cityname=" + URLEncoder.encode(cityName, "UTF-8");
            String jsonResult = request(HTTP_URL1, httpArg);
            //开始解析json
            Log.w("weather", jsonResult);

            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONObject retData = jsonObject.getJSONObject("retData");
            weather = new Weather(retData.getString("city"), "20" + retData.getString("date"),
                    retData.getString("weather"), Integer.valueOf(retData.getString("temp")) - 2  + "°C",
                    retData.getString("l_tmp") + "°C", retData.getString("h_tmp") + "°C");
        } catch (Exception e) {
            Log.e("parseJson", "error");
            e.printStackTrace();
        }
        return weather;
    }

    //根据URL2的网络请求解析
    private static Weather getWeather2(String cityName){
        try {
            String httpArg = "cityname=" + URLEncoder.encode(cityName, "UTF-8");
            String jsonResult = request(HTTP_URL2, httpArg);
            //开始解析json

            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONObject retData = jsonObject.getJSONObject("retData");
            JSONObject today = retData.getJSONObject("today");
            weather = new Weather(retData.getString("city"), today.getString("date"),
                    today.getString("type"), today.getString("curTemp"),
                    today.getString("lowtemp"), today.getString("hightemp"));
        } catch (Exception e) {
            Log.e("parseJson", "error");
            e.printStackTrace();
        }
        return weather;
    }


    /**
     * @param httpUrl
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    private static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "e03e2ffab243ec9c942beca276748c1a");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
