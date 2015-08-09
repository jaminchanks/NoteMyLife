package com.challenger.jamin.notemylife2.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.Weather;
import com.challenger.jamin.notemylife2.net.RequestWeather;
import com.challenger.jamin.notemylife2.ui.activity.WeatherActivity;

/**
 * Created by jamin on 8/7/15.
 */
public class WeatherView extends RelativeLayout{
    private static final int FUCK_CODE = 1;
    Context context;
    View view;
    static Weather weather;

    TextView tvWeatherType;
    TextView tvWeatherCity;
    TextView tvWeatherDate;
    TextView tvWeatherTemp;
    TextView tvWeatherTempRange;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FUCK_CODE:
                   // Weather weather1 = (Weather)msg.obj;
                    if (weather != null) {
                        Log.w("weather", weather.getCity());
                        //设置所有控件的内容
                        tvWeatherDate.setText(weather.getDate());
                        tvWeatherType.setText(weather.getWeatherType());
                        tvWeatherCity.setText(weather.getCity());
                        tvWeatherTemp.setText(weather.getTemp());
                        tvWeatherTempRange.setText(weather.getL_temp().substring(0, weather.getL_temp().length() - 1)
                                + "/" + weather.getH_temp());
                        view.setVisibility(VISIBLE);
                    }
                    break;
            }
        }
    };
    public WeatherView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.weather_top_view, this);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WeatherActivity.class);
                context.startActivity(intent);
            }
        });

        //未查询到数据前该View不可见
        view.setVisibility(GONE);

        //查询所有控件
        tvWeatherType = (TextView)view.findViewById(R.id.weatherType);
        tvWeatherCity = (TextView)view.findViewById(R.id.weatherCity);
        tvWeatherDate = (TextView)view.findViewById(R.id.weatherDate);
        tvWeatherTemp = (TextView)view.findViewById(R.id.weatherTemp);
        tvWeatherTempRange = (TextView)view.findViewById(R.id.weatherTempRange);

        //判断有无网络，有则发送网络请求天气信息
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            requestWeather();
        }
    }

    private void requestWeather() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weather = RequestWeather.getWeather("广州");
                    //Message message = new Message();
                    //message.what = FUCK_CODE;
                    //message.obj = weather;
                    handler.sendEmptyMessage(FUCK_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    public static String getWeatherType() {
        if (weather != null) {
            return weather.getWeatherType();
        } else
            return "未知";
    }

}
