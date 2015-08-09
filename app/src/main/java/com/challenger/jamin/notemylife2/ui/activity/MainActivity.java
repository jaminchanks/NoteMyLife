package com.challenger.jamin.notemylife2.ui.activity;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.ui.fragment.LeftFragment;
import com.challenger.jamin.notemylife2.ui.fragment.MainFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description 主界面
 */
public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {
	private ImageView homeMenu;
	private Fragment mContent;
	private TextView topTextView;
	//private NetworkChangeReceive networkChangeReceive;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSlidingMenu(savedInstanceState);

		homeMenu = (ImageView) findViewById(R.id.menu_home);
		homeMenu.setOnClickListener(this);
		topTextView = (TextView) findViewById(R.id.menu_title);

		//随时检测网络状况
		if (!isNetworkAvailable()) {
			Toast.makeText(this, "现在处于离线状态", Toast.LENGTH_SHORT).show();
		}
		//networkChangeReceive = new NetworkChangeReceive();
		//IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		//registerReceiver(networkChangeReceive, intentFilter);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		//unregisterReceiver(networkChangeReceive);
	}


	/**
	 * 初始化侧边栏
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
		if (savedInstanceState != null) {
			mContent = getFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new MainFragment();
			getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		}

		// 设置左侧滑动菜单
		setBehindContentView(R.layout.menu_frame_left);
		getFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new LeftFragment()).commit();

		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shap_shadow);
		// 设置滑动菜单阴影的图像资源
		sm.setShadowDrawable(null);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式,这里设置为全屏
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.0f);

	}

	/*
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getFragmentManager().putFragment(outState, "mContent", mContent);
	}
	*/

	/**
	 * 切换Fragment
	 *
	 * @param fragment
	 */
	public void switchConent(Fragment fragment, String title) {
		mContent = fragment;
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();


		getSlidingMenu().showContent();
		topTextView.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.menu_home:
				toggle();
				break;
			default:
				break;
		}
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			return true;
		} else {
			Toast.makeText(this, "现在处于离线状态", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

}
