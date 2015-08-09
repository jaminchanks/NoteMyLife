package com.challenger.jamin.notemylife2.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.ui.activity.MainActivity;


/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description 侧边栏菜单
 */
public class LeftFragment extends Fragment implements OnClickListener {
	private View mainView;
	private View bookCategoryView;
	private View robotView;
	private View accountView;
	private View settingsView;
	private View aboutView;

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
		View view = inflater.inflate(R.layout.layout_slidngmenu, null);
		findViews(view);

		return view;
	}


	public void findViews(View view) {
		mainView = view.findViewById(R.id.tvMain);
		bookCategoryView = view.findViewById(R.id.tvBookCategory);
		robotView = view.findViewById(R.id.head_robot);
		accountView = view.findViewById(R.id.tvAccount);
		settingsView = view.findViewById(R.id.tvSettings);
		aboutView = view.findViewById(R.id.tvAbout);

		mainView.setOnClickListener(this);
		bookCategoryView.setOnClickListener(this);
		robotView.setOnClickListener(this);
		accountView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
		aboutView.setOnClickListener(this);
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
				newContent = new AccountFragment();
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

}
