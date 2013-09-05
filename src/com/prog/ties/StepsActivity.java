package com.prog.ties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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

import com.viewpagerindicator.CirclePageIndicator;

public class StepsActivity extends Activity {
	ImageView iv, b1, b2;
	private TextView tvs;
	private Button backbut, mirbut;
	int pos=0;
	private int file;
	private String back;
	private String tie_step_name[];
	private String tie_step_info[];
	public List<View> steps=new ArrayList<View>();
	private ViewPager viewPagerS;
	private CirclePageIndicator CircleIndicatorS;
	private TextView textView;
	
	private Integer[] Images1 = {
    		R.drawable.double_windsor_step1,
    		R.drawable.double_windsor_step2,
    		R.drawable.double_windsor_step3,
    		R.drawable.double_windsor_step4,
    		R.drawable.double_windsor_step5,
    		R.drawable.double_windsor_step6,
    		R.drawable.double_windsor_step7,
    		R.drawable.double_windsor_step8,
    		R.drawable.double_windsor_step9,
    		R.drawable.double_windsor_step10,
    };
	private Integer[] Images2 = {
    		R.drawable.windsor_step1,
    		R.drawable.windsor_step2,
    		R.drawable.windsor_step3,
    		R.drawable.windsor_step4,
    		R.drawable.windsor_step5,
    		R.drawable.windsor_step6,
    		R.drawable.windsor_step7,
    		R.drawable.windsor_step8,
	};
	
	private Integer[] Images3 = {
    		R.drawable.calvin_step1,
    		R.drawable.calvin_step2,
    		R.drawable.calvin_step3,
    		R.drawable.calvin_step4,
    		R.drawable.calvin_step5,
    		R.drawable.calvin_step6,
    		R.drawable.calvin_step7
	};	
	
	private Integer[] Images4 = {
    		R.drawable.diagonal_step1,
    		R.drawable.diagonal_step2,
    		R.drawable.diagonal_step3,
    		R.drawable.diagonal_step4,
    		R.drawable.diagonal_step5,
    		R.drawable.diagonal_step6,
    		R.drawable.diagonal_step7,
    		R.drawable.diagonal_step8,
	};
	
	private Integer[] Images5 = {
    		R.drawable.onassis_step1,
    		R.drawable.onassis_step2,
    		R.drawable.onassis_step3,
    		R.drawable.onassis_step4,
    		R.drawable.onassis_step5,
    		R.drawable.onassis_step6,
    		R.drawable.onassis_step7,
    		R.drawable.onassis_step8,
	};
	
	private Integer[] Images6 = {
    		R.drawable.kent_step1,
    		R.drawable.kent_step2,
    		R.drawable.kent_step3,
    		R.drawable.kent_step4,
    		R.drawable.kent_step5,
	};
	
	private Integer[] Images7 = {
    		R.drawable.four_in_hand_step1,
    		R.drawable.four_in_hand_step2,
    		R.drawable.four_in_hand_step3,
    		R.drawable.four_in_hand_step4,
    		R.drawable.four_in_hand_step5,
    		R.drawable.four_in_hand_step6,
	};
	LayoutInflater inflater;
	int x, tie_in;
	Integer[] m;
	String tie_name;
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.steps);
        loadViews();
        backbut.setText("Назад");
	    inflater=LayoutInflater.from(this); 
		Bundle bundle = getIntent().getExtras();
	    tie_in=bundle.getInt("ItemNumber");
	    tie_in++;
	    file=TiesActivity.getFile();
	
	    readFile(file);
	    
	    for(int i=0;i<x;i++) 
	    {    
	    	View page=inflater.inflate(R.layout.page,null);
	    	iv=(ImageView)page.findViewById(R.id.imageView1);
	    	Bitmap im=BitmapFactory.decodeResource(getResources(), m[i]);
	    	iv.setImageBitmap(im);
	        tvs=(TextView)page.findViewById(R.id.textView1);
	        tvs.setText(tie_step_info[i]);
	        tvs.setMovementMethod(new ScrollingMovementMethod());       	
	        steps.add(page);
	    }
	    	
	    textView.setText(tie_name);
	        
		backbut.setText(back);
	    backbut.setOnClickListener(new OnClickListener() {
			@Override
	        public void onClick(View v) {
					finish();
				}
		});
	    mirbut.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v)
			{					
				for(View page:steps)
				{
					try
					{
						iv=(ImageView)page.findViewById(R.id.imageView1);
						Bitmap bmp=((BitmapDrawable)iv.getDrawable()).getBitmap();
						Matrix matrix = new Matrix(); 
						matrix.preScale(-1, 1); 
						Bitmap mirroredBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
						bmp.recycle();							
						iv.setImageBitmap(mirroredBitmap);
						System.gc();
					}
					catch(Exception ex){Toast.makeText(StepsActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();}			        	
				}
			}
	    });
			
		viewPagerS.setAdapter(new PreviewPagerAdapter(steps));
	    viewPagerS.setCurrentItem(0); 
			
		CircleIndicatorS.setViewPager(viewPagerS);
        CircleIndicatorS.setCurrentItem(0);
	        
	}
	
    public void loadViews()
    {
    	backbut=(Button)findViewById(R.id.backbut);
        mirbut=(Button)findViewById(R.id.mirbut);
        viewPagerS=(ViewPager)findViewById(R.id.view_pagerS);
        CircleIndicatorS=(CirclePageIndicator)findViewById(R.id.circlePageIndicatorS);
		textView=(TextView)findViewById(R.id.tiename);
    }
    
	
	public void readFile(int file)
    {
  		Context context = getBaseContext();        
  		InputStream inputStream = context.getResources().openRawResource(file);   
  		InputStreamReader inputreader = new InputStreamReader(inputStream);
  		BufferedReader buffreader = new BufferedReader(inputreader);
  		String line="";
  		int z=0;
		switch(tie_in)
		{
			case 1: m=Images1; break; 
			case 2: m=Images2; break; 
			case 3: m=Images3; break; 
			case 4: m=Images4; break; 
			case 5: m=Images5; break; 
			case 6: m=Images6; break; 
			case 7: m=Images7; break; 
			default: m=Images1; break;
		};
		x=m.length;
		tie_step_name=new String [x];
		tie_step_info=new String [x];
		try 
			{
			while (( line = buffreader.readLine()) != null  && z!=x) 
					{ 
						if(line.substring(1, line.indexOf("\"=")).equals("item"+tie_in+"name")) tie_name=line.substring(line.indexOf("=\"")+2,line.length()-1);
						if(line.substring(1, line.indexOf("\"=")).equals("back")) back=line.substring(line.indexOf("=\"")+2,line.length()-1);
						if(line.substring(1, line.indexOf("\"=")).equals("item"+tie_in+"step"+(z+1)+"name")) tie_step_name[z]=line.substring(line.indexOf("=\"")+2,line.length()-1);
						if(line.substring(1, line.indexOf("\"=")).equals("item"+tie_in+"step"+(z+1)+"info")) tie_step_info[z++]=line.substring(line.indexOf("=\"")+2,line.length()-1).replace('*', '\n');
					}
			}
		catch (IOException e){ Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();}
    }
    

}
