package com.prog.ties;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.TitleProvider;

public class PreviewPagerAdapter extends PagerAdapter implements TitleProvider {
	List<View> pages=null;
	public PreviewPagerAdapter(List<View> pages)
	{
		this.pages=pages;
	}
	
	@Override
	public Object instantiateItem(View collection, int position)
	{
		View v=pages.get(position);
		((ViewPager)collection).addView(v, 0);
		return v;
	}
	
	@Override
	public void destroyItem(View collection, int position, Object view)
	{
		((ViewPager)collection).removeView((View)view);
	}
	
	@Override
	public int getCount()
	{
		return pages.size();
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view.equals(object);
	}

	@Override
	public String getTitle(int position) {
		// TODO Auto-generated method stub
		return (String)(pages.get(position).getTag());
	}
	
	
	@Override
    public void finishUpdate(View arg0){
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1){
    }

    @Override
    public Parcelable saveState(){
        return null;
    }

    @Override
    public void startUpdate(View arg0){
    }
    
}
