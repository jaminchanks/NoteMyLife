package com.challenger.jamin.notemylife2.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.challenger.jamin.notemylife2.Base.DataVar;
import com.challenger.jamin.notemylife2.Base.MyApplication;
import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.User;
import com.challenger.jamin.notemylife2.ui.activity.MainActivity;
import com.challenger.jamin.notemylife2.ui.view.RoundImageView;

import java.io.File;


/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description 侧边栏菜单
 */
public class LeftFragment extends Fragment implements OnClickListener {
	RoundImageView headView;
	TextView tvNickName;
	View rootLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		rootLayout = inflater.inflate(R.layout.frag_left, null);

		//所有选项
		View mainView = rootLayout.findViewById(R.id.tvMain);
		View bookCategoryView = rootLayout.findViewById(R.id.tvBookCategory);
		View robotView = rootLayout.findViewById(R.id.head_robot);
		View accountView = rootLayout.findViewById(R.id.tvAccount);
		View settingsView = rootLayout.findViewById(R.id.tvSettings);
		View aboutView = rootLayout.findViewById(R.id.tvAbout);

		//提取当前登陆用户的信息
		MyApplication myApplication = (MyApplication)getActivity().getApplication();
		User user = myApplication.getUser();
		Log.w("user is null?", (user == null)? "is" : "no");


		//头像
		headView = (RoundImageView) rootLayout.findViewById(R.id.left_user_head);
		Log.w("head", user.getHead());
		headView.setImageBitmap(BitmapFactory.decodeFile(DataVar.APP_IMG_FILE + File.separator + user.getHead()));
		headView.setOnClickListener(this);


		//昵称
		tvNickName = (TextView) rootLayout.findViewById(R.id.left_user_nickname);
		tvNickName.setText(user.getNickName());

		mainView.setOnClickListener(this);
		bookCategoryView.setOnClickListener(this);
		robotView.setOnClickListener(this);
		accountView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
		aboutView.setOnClickListener(this);

		Log.w("test", "leftFragmentonCreateView");
		return rootLayout;
	}


	@Override
	public void onStart() {
		super.onStart();
		Log.w("test", "onLeftFragmentStart");
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		String title = null;
		switch (v.getId()) {
			case R.id.left_user_head:
				newContent = new UserFragment();
				title = "账户信息";
				break;
			case R.id.tvMain: // 主页
				newContent = new MainFragment();
				title = "所有日志";
				break;
			case R.id.tvBookCategory: // 笔记本分类
				newContent = new BookCategoryFragment();
				title = "笔记本";
				break;
			case R.id.head_robot: // 机器人
				newContent = new RobotFragment();
				title = "机器人";
				break;
			case R.id.tvAccount: // 账户信息
				newContent = new UserFragment();
				title = "账户信息";
				break;
			case R.id.tvSettings: // 设置
				newContent = new SettingsFragment();
				title = "设置";
				break;
			case R.id.tvAbout: // 关于
				newContent = new AboutFragment();
				title = "关于";
				break;
			default:
				break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}

	/**
	 * 切换fragment
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchConent(fragment, title);
		}
	}


	//更新用户显示信息
	public void updateUserData() {
		//提取当前登陆用户的信息
		MyApplication myApplication = (MyApplication)getActivity().getApplication();
		User user = myApplication.getUser();
		Log.w("user is null?", (user == null)? "is" : "no");

		//头像
		headView = (RoundImageView) rootLayout.findViewById(R.id.left_user_head);
		Log.w("head", user.getHead());
		headView.setImageBitmap(BitmapFactory.decodeFile(DataVar.APP_IMG_FILE + File.separator + user.getHead()));

		//昵称
		tvNickName = (TextView) rootLayout.findViewById(R.id.left_user_nickname);
		tvNickName.setText(user.getNickName());

	}

}
