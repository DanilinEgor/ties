package com.prog.ties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends Activity {
	TextView tv;
	ImageView nm;
	private int file;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		file=TiesActivity.getFile();
		
		nm=(ImageView)findViewById(R.id.nm_logo);
		   nm.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.novilabmobile.ru/"));
					startActivity(browserIntent);				
				}
			});
		tv=(TextView)findViewById(R.id.about_text);
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
					if(line.substring(1, line.indexOf("\"=")).equals("DeveloperInfo")) tv.setText(line.substring(line.indexOf("=\"")+2, line.length()-1).replace('*', '\n'));
			}
		}
		catch (IOException e){ Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();}
    }
}
