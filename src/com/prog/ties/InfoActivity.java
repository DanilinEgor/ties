package com.prog.ties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class InfoActivity extends Activity {
	Button b1,b2,b3,b4,b5;
	int file, file1;
	String back, change;
	@Override
	public void onCreate(Bundle savedInstanceState)
    { 
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info);
        file=TiesActivity.getFile();   
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        readFile(file);
       
        b1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				createActivity("Color", file);
			}
		});
        
        b2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				createActivity("Picture", file);
			}
		});        
        
        b3.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				createActivity("Shape", file);
			}
		});
        
        b4.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				createActivity("Care", file);
			}
		});
        
        b5.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				createActivity("Quote", file);
			}
		});
    }
	
	public void createActivity(String s, int x)
	{
		Intent intent=new Intent();
		intent.setClass(InfoActivity.this, InfoTextActivity.class);
		Bundle b=new Bundle();
		b.putString( "advice_kind", s);
		file1=file;
		intent.putExtras(b);
		startActivity(intent);
	}
		
	public void readFile(int file)
	{
		Context context = getBaseContext();        
		InputStream inputStream = context.getResources().openRawResource(file);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line="";
		try 
		{
			while (( line = buffreader.readLine()) != null)  
			{
				if(!line.isEmpty())   
				{ 
					if(line.substring(1, line.indexOf("\"=")).equals("ColorName")) b1.setText(line.substring(line.indexOf("=\"")+2, line.length()-1));
					if(line.substring(1, line.indexOf("\"=")).equals("PictureName")) b2.setText(line.substring(line.indexOf("=\"")+2, line.length()-1));
					if(line.substring(1, line.indexOf("\"=")).equals("ShapeName")) b3.setText(line.substring(line.indexOf("=\"")+2, line.length()-1));
					if(line.substring(1, line.indexOf("\"=")).equals("CareName")) b4.setText(line.substring(line.indexOf("=\"")+2, line.length()-1));
					if(line.substring(1, line.indexOf("\"=")).equals("QuoteName")) b5.setText(line.substring(line.indexOf("=\"")+2, line.length()-1));
					if(line.substring(1, line.indexOf("\"=")).equals("back")) back=line.substring(line.indexOf("=\"")+2,line.length()-1);
					if(line.substring(1, line.indexOf("\"=")).equals("change")) change=line.substring(line.indexOf("=\"")+2,line.length()-1);
					
				}
			}
		}
		catch (IOException e){ Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();}    
	}
}