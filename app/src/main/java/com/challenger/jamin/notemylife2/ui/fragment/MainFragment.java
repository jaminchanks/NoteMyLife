package com.challenger.jamin.notemylife2.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.challenger.jamin.notemylife2.R;
import com.challenger.jamin.notemylife2.bean.Diary;
import com.challenger.jamin.notemylife2.database.dao.DiaryDao;
import com.challenger.jamin.notemylife2.ui.activity.DiaryDetailActivity;
import com.challenger.jamin.notemylife2.ui.activity.DiaryEditActivity;
import com.challenger.jamin.notemylife2.ui.activity.MainActivity;
import com.challenger.jamin.notemylife2.ui.adapter.SimpleDiaryAdapter;
import com.challenger.jamin.notemylife2.ui.view.WeatherView;

import java.util.ArrayList;


/**
 *
 *	主页
 */
public class MainFragment extends Fragment {
	private ArrayList<Diary> diaries;
	private DiaryDao diaryDao;
	private SimpleDiaryAdapter adapter;
	private View mainLayout;
	private LayoutInflater inflater;

	private ListView diaryListview;
	private SwipeRefreshLayout swipeRefreshLayout;
	private View weatherLayout;

	private NetworkStateReceiver networkStateReceiver;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//每次获取数据库，更新数据
		diaryDao = DiaryDao.getInstance(getActivity());
		diaries = diaryDao.getAllDiaries();
		//初始化adapter
		adapter = new SimpleDiaryAdapter(getActivity(), diaries);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		this.inflater = inflater;
		mainLayout = inflater.inflate(R.layout.frag_main, null);
		listViewSetting();
		swipeRefreshLayoutSetting();
		btnAddSetting();
		return mainLayout;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onStart(){
		super.onStart();
		reLoadData();
		//帮其父activity绑定广播接收器
		networkStateReceiver = new NetworkStateReceiver();
		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		getActivity().registerReceiver(networkStateReceiver, intentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.w("mainFragment", "destroy");
		getActivity().unregisterReceiver(networkStateReceiver);
	}

	private void btnAddSetting() {
		ImageButton addDiaryBtn = (ImageButton) mainLayout.findViewById(R.id.add_diary_button);
		addDiaryBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), DiaryEditActivity.class);
				if (weatherLayout != null) {
					//获得天气类型信息
					intent.putExtra("weatherType", WeatherView.getWeatherType());
				}
				//else
				//	intent.putExtra("weatherType", "未知");
				startActivity(intent);
			}
		});
	}

	/**
	 * listView的设置，包括item点击事件，长按弹出菜单事件，以及滚动事件监听
	 * */
	private void listViewSetting() {
		//显示所有日志的listView
		diaryListview = (ListView) mainLayout.findViewById(R.id.diary_listView);
		//添加天气显示栏
		if (((MainActivity) getActivity()).isNetworkAvailable()) {
			//天气布局
			weatherLayout = inflater.inflate(R.layout.weather_top_view2, null);
			diaryListview.addHeaderView(weatherLayout);
		}
		diaryListview.setAdapter(adapter);
		//设置物滚动条
		diaryListview.setOverScrollMode(View.OVER_SCROLL_NEVER);
		//滚动事件处理
		diaryListview.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				//判断第一个item完全显示
				if (firstVisibleItem == 0) {
					if (view.getChildAt(0) != null && view.getChildAt(0).getTop() >= 0) {
						swipeRefreshLayout.setEnabled(true);
					}
				} else
					//显示下拉刷新图标
					swipeRefreshLayout.setEnabled(false);
			}
		});
		//item点击事件
		diaryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//点击进入详情
				goToDiaryDetail(position);
			}
		});
		//item长按事件监听
		diaryListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (position >= diaryListview.getHeaderViewsCount())
					showOperateDialog(position);
				return true;
			}
		});
	}

	/**进入日志详情
	 *
	 * @param position item对应的位置
	 *
	 */
	public void goToDiaryDetail(int position) {
		//获得headView的个数
		int headViewCount = diaryListview.getHeaderViewsCount();
		if (position >= headViewCount) {
			//以下是listView的正文
			Diary diary = adapter.getItem(position - headViewCount);
			Log.w("diary", diary.getTitle());
			Bundle bundle = new Bundle();
			bundle.putSerializable("diaryItem", diary);
			Intent intent = new Intent(getActivity(), DiaryDetailActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	/**
	 * 删除操作
	 * @param position item所对应的位置
	 * */
	public void deleteDiary(int position) {
		//获得headView的个数
		int headViewCount = diaryListview.getHeaderViewsCount();
		if (position >= headViewCount) {
			//以下是listView的正文
			Diary diary = adapter.getItem(position - headViewCount);
			//删除数据库中的diary,同时更新diaries
			diaryDao.delete(diary);
			diaries.remove(position - headViewCount);
			adapter.notifyDataSetChanged();
		}
	}


	/**弹出选择操作的对话框
	 *
	 * @param position item对应的位置
	 *
	 */
	public void showOperateDialog(final int position){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//builder.setTitle("请选择操作");
		builder.setItems(new CharSequence[]{"查看", "编辑", "删除"},
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:		//查看操作
								//获得headView的个数
								goToDiaryDetail(position);
								break;
							case 1:	//编辑操作
								Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
								break;
							case 2:	//删除操作
								deleteDiary(position);
								break;
							default:
								break;
						}
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}


	/**
	 * 下拉刷新设置
	 * */
	private void swipeRefreshLayoutSetting() {
		//设置下拉刷新
		swipeRefreshLayout = (SwipeRefreshLayout) mainLayout.findViewById(R.id.swipe_container);
		//设置刷新时动画的颜色，可以设置4个
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(true);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						reLoadData();
						if (swipeRefreshLayout.isRefreshing())
							swipeRefreshLayout.setRefreshing(false);
					}
				}, 2000);
			}
		});
	}

	/**
	 * 重新获取数据库中的日志
	 * */
	public void reLoadData() {
		diaries.clear();
		diaries.addAll(diaryDao.getAllDiaries());
		adapter.notifyDataSetChanged();
	}


	class NetworkStateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isAvailable()) {
                if (weatherLayout == null) {
                    weatherLayout = inflater.inflate(R.layout.weather_top_view2, null);
                    diaryListview.addHeaderView(weatherLayout);
                    adapter.notifyDataSetChanged();
                }
			} else {
                Toast.makeText(getActivity(), "现在处于离线状态", Toast.LENGTH_SHORT).show();
				if (weatherLayout != null) {
					diaryListview.removeHeaderView(weatherLayout);
					weatherLayout = null;
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

}
