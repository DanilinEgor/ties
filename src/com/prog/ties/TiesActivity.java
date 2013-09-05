package com.prog.ties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TitlePageIndicator;

public class TiesActivity extends Activity {
	private ImageView iv;
	private TextView tv;
	private int count=7;
	private static int file;
	private Button AdviceButton,AboutButton;
	private Bitmap im, bmp_pop, bmp_hard;
	private String pop, hard, rec;	
	private String tie_name[]=new String[count];
	private Integer tie_pop[]=new Integer[count];
	private Integer tie_hard[]=new Integer[count];
	private String tie_info[]=new String[count];
	private Integer[] mImages={
			R.drawable.double_windsor,
			R.drawable.windsor,
			R.drawable.calvin,
	    	R.drawable.diagonal,	    	
	    	R.drawable.onassis,
	  	    R.drawable.kent,
	  	    R.drawable.four_in_hand	    	
	    };
	private List<View> pages=new ArrayList<View>(); 
	private TitlePageIndicator mTitleIndicator;
	private ViewPager viewPager;
	private Paint paint;
	private ImageView splash;
	private ProgressDialog pd;
	boolean first_create=true;
	private LayoutInflater inflater;
	private int curPos=0;
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {  	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);  
        
        if(Locale.getDefault().getDisplayLanguage().equals("русский")) file=R.raw.rus;
        else file=R.raw.eng;
        
		readFile(file); 
        
        inflater=LayoutInflater.from(TiesActivity.this);

        paint = new Paint();   
	    paint.setColor(Color.WHITE); 
	    paint.setStyle(Style.FILL);   
        
        loadViews();
        new setter().execute(0);   
            
        AdviceButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {	
				
				Intent intent=new Intent();
				intent.setClass(TiesActivity.this, InfoActivity.class);
				startActivity(intent);
			}
		});
        
        AboutButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent();
				intent.setClass(TiesActivity.this, AboutActivity.class);
				startActivity(intent);				
			}
		});   
        
     
         
     }
    
    public void loadViews()
    {
    	AdviceButton=(Button)findViewById(R.id.button1);
        AboutButton=(Button)findViewById(R.id.button2);   
        splash=(ImageView)findViewById(R.id.splashscreen);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
	    mTitleIndicator=(TitlePageIndicator)findViewById(R.id.indicator);
    }
     
    public void readFile(int file)
    {
    	try 
		{
    		Context context = getBaseContext();        
    		InputStream inputStream = context.getResources().openRawResource(file);   
    		InputStreamReader inputreader = new InputStreamReader(inputStream);
    		BufferedReader buffreader = new BufferedReader(inputreader);
    		String line="";
    		int i=0;		
			while (( line = buffreader.readLine()) != null)    
			{
					if(line.substring(1, line.indexOf("\"=")).equals("recommendations")) rec=line.substring(line.indexOf("=\"")+2,line.length()-1);
					if(line.substring(1, line.indexOf("\"=")).equals("popularity")) pop=line.substring(line.indexOf("=\"")+2,line.length()-1);
					if(line.substring(1, line.indexOf("\"=")).equals("hardness")) hard=line.substring(line.indexOf("=\"")+2,line.length()-1);
					if(line.substring(1, line.indexOf("\"=")).equals("item"+(i+1)+"name")) tie_name[i]=line.substring(line.indexOf("=\"")+2,line.length()-1);
					if(line.substring(1, line.indexOf("\"=")).equals("item"+(i+1)+"pop")) tie_pop[i]=line.charAt(line.length()-1)-'0';
					if(line.substring(1, line.indexOf("\"=")).equals("item"+(i+1)+"hard")) tie_hard[i]=line.charAt(line.length()-1)-'0';
					if(line.substring(1, line.indexOf("\"=")).equals("item"+(i+1)+"info"))	tie_info[i++]=line.substring(line.indexOf("=\"")+2,line.length()-1).replace('*', '\n');					
			}
		}
		catch (IOException e){ Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();}
    }	
	
	private class setter extends AsyncTask<Integer, Void, Bitmap>
	{
		int pos;
		@Override
		protected Bitmap doInBackground(Integer... params) {
			pos=params[0];	    	 	
	        bmp_pop=BitmapFactory.decodeResource(getResources(), R.drawable.bar_pop);
		    bmp_hard=BitmapFactory.decodeResource(getResources(), R.drawable.bar_hard);
	    	im=null;	
	    	Bitmap ib=BitmapFactory.decodeResource(getResources(), R.drawable.indicator_background);
	 
	    	Canvas canvas=null;        	        	
	        im=BitmapFactory.decodeResource(getResources(), mImages[pos]);

		    paint.setTextSize(im.getWidth()/20);
	        canvas=new Canvas(im);
	        canvas.drawText(hard, 0, 20, paint);
	        canvas.drawBitmap(ib, 0, 54, null);
	        for(int j=0; j<tie_hard[pos]; j++)
	        	canvas.drawBitmap(bmp_hard, 10+j*(bmp_hard.getWidth()+4), 60, null);
	        canvas.drawText(pop, im.getWidth()-pop.length()*paint.getTextSize()/2-20, 20, paint);
	        canvas.drawBitmap(ib, im.getWidth()-ib.getWidth(), 54, null);
	        for(int j=0; j<tie_pop[pos]; j++)
	        	canvas.drawBitmap(bmp_pop, im.getWidth()-j*(bmp_pop.getWidth()+4)-30, 60, null);

	    	 bmp_hard.recycle();
	         bmp_pop.recycle();
	
	         return im;
		}
		
		@Override
		protected void onPostExecute(Bitmap im)
		{
			AdviceButton.setText(rec);
	    	
	    	View page=inflater.inflate(R.layout.page,null);
	    	iv=(ImageView)page.findViewById(R.id.imageView1);
	    	iv.setImageBitmap(im);
	        tv=(TextView)page.findViewById(R.id.textView1);
	        tv.setText(tie_info[pos]);
	        tv.setMovementMethod(new ScrollingMovementMethod());       	
	        tv=(TextView)page.findViewById(R.id.textView1);
	        page.setTag(tie_name[pos]);
	        pages.add(page);	     
	              
	        if(pos==count-1)
	        {    	
	        	for(View p:pages)
		        {
		        	iv=(ImageView)p.findViewById(R.id.imageView1);
	    			iv.setOnClickListener(new OnClickListener() {			
	    				public void onClick(View v) {	
	    					Intent intent=new Intent();
	    					intent.setClass(TiesActivity.this, StepsActivity.class);
	    					Bundle b=new Bundle();
	 						b.putInt("ItemNumber", viewPager.getCurrentItem());
	 						intent.putExtras(b);
	    					startActivity(intent);					
	    				}
				    });
		        }
	        	
	        	viewPager.setAdapter(new PreviewPagerAdapter(pages));
	        	viewPager.setCurrentItem(curPos);        
	         
	        	mTitleIndicator.setViewPager(viewPager);
	        	mTitleIndicator.setCurrentItem(curPos);
	        
	        	if(first_create)
	        		splash.setVisibility(View.GONE);	       
	        	else
	        		pd.dismiss();
	        	first_create=false;			
	        	System.gc();       
	        }
	        else
	        	new setter().execute(pos+1);
	    }
	}

	public static int getFile() {
		return file;
	}
}