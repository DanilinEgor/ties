package com.prog.ties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class InfoTextActivity extends Activity {
	TextView tv;
	String kind;
	int file;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info_text);
		
	    kind=getIntent().getExtras().getString("advice_kind");
	    file=TiesActivity.getFile();
	    tv=(TextView)findViewById(R.id.textView2);
	    
	    readFile(file);	    	    
		tv.setMovementMethod(new ScrollingMovementMethod());
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
					if(line.substring(1, line.indexOf("\"=")).equals(kind+"Text")) tv.setText(line.substring(line.indexOf("=\"")+2, line.length()-1).replace('*', '\n'));
				}
			}
		}
		catch (IOException e){ Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();}
    }	
}