package com.shou.wosafebook;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {
	private List<View> listview;

	public MyPagerAdapter(List<View> listview) {
		this.listview = listview;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(listview.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method
		// stubcontainer.addView(viewList.get(position));
		container.addView(listview.get(position));
		return listview.get(position);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listview.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
