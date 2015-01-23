package jp.ac.kcska.dokodemo.Show;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;






import jp.ac.kcska.dokodemo.R;
import jp.ac.kcska.dokodemo.Vial.AsyncCallback;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ShowMainActivity extends Activity implements OnClickListener{
	
	private final String TAG = "中園";
	private ProgressDialog dialog;
	private MyTask asyncGet;
	
	TabHost tabhost;
	TextView tv1,tv2,tv3,tv4,tv5,tv6;
	Button btn1,btn2;
	Calendar cal;
	int i=0;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_main);
	   
	    cal = Calendar.getInstance();
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
	    
	    Intent intent = getIntent();
		String data = intent.getStringExtra("cal");
	    
	    tv1 = (TextView)findViewById(R.id.textView1);
	    tv2 = (TextView)findViewById(R.id.textView2);
	    tv3 = (TextView)findViewById(R.id.textView3);
	    tv4 = (TextView)findViewById(R.id.textView4);
	    tv5 = (TextView)findViewById(R.id.textView5);
	    tv6 = (TextView)findViewById(R.id.textView6);
	    btn1 = (Button)findViewById(R.id.button1);
	    btn2 = (Button)findViewById(R.id.button2);
	    tv1.setText(year+"年"+(month+1)+"月"+day+"日");
	    
	  
	    
	    if(data!=null){
	    	tv1.setText(data);
	    }
	    
	   
	    
	    tv1.setOnClickListener(this);
	    btn1.setOnClickListener(this);
	    btn2.setOnClickListener(this);
	    
	    
	    //TabHost�I�u�W�F�N�g�擾      
	    tabhost = (TabHost)findViewById(android.R.id.tabhost);   
	    tabhost.setup();
	    
	    TabSpec tab1 = tabhost.newTabSpec("tab1"); 
	    tab1.setIndicator("食事");               
	    tab1.setContent(R.id.tab1);
	   
	    tabhost.addTab(tab1);                   
	 
	    TabSpec tab2 = tabhost.newTabSpec("tab2"); 
	    tab2.setIndicator("薬剤");               
	    tab2.setContent(R.id.tab2);
	    
	    tabhost.addTab(tab2);
	     
	    TabSpec tab3 = tabhost.newTabSpec("tab3"); 
	    tab3.setIndicator("処方"); 
	    tab3.setContent(R.id.tab3);
	   
	    tabhost.addTab(tab3);
	    
	    TabSpec tab4 = tabhost.newTabSpec("tab4"); 
	    tab4.setIndicator("バイタル");   
	    tab4.setContent(R.id.tab4);
	    
	    tabhost.addTab(tab4);
	    
	    TabSpec tab5 = tabhost.newTabSpec("tab5"); 
	    tab5.setIndicator("血糖値");  
	    tab5.setContent(R.id.tab5);
	    
	    tabhost.addTab(tab5);
	 
	    //tabhost.setCurrentTab(0);
	    
	    
	    asyncGet = new MyTask(new AsyncCallback() {
            public void onPreExecute() {
                // do something
            	dialog = new ProgressDialog(ShowMainActivity.this);
                dialog.setTitle("Please wait");
                dialog.setMessage("Loading data...");
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setCancelable(true);
                dialog.setMax(100);
                dialog.setProgress(0);
                dialog.show();
            }
            public void onProgressUpdate(int progress) {
            	// do something
            	dialog.setProgress(progress);
            }
            public void onPostExecute(ArrayList<String> result) {
            	// do something
            	tv2.setText("");
            	for(String str : result){
            		String crlf = System.getProperty("line.separator");
            		tv2.append(crlf+str);
            	}
                dialog.dismiss();
            }
            public void onCancelled() {
            	// do something
            	dialog.dismiss();
            }
        });
		asyncGet.execute("https://kcsgogo.herokuapp.com/foods.json");
		
	    
	    tabhost.setOnTabChangedListener(new OnTabChangeListener() {
	        public void onTabChanged(String tabId) {
	            switch (tabhost.getCurrentTab()) {
	            case 0:
	                //do what you want when tab 0 is selected
	            	// �^�X�N�̐���
	            	asyncGet = new MyTask(new AsyncCallback() {
			            public void onPreExecute() {
			                // do something
			            	dialog = new ProgressDialog(ShowMainActivity.this);
			                dialog.setTitle("Please wait");
			                dialog.setMessage("Loading data...");
			                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			                dialog.setCancelable(true);
			                dialog.setMax(100);
			                dialog.setProgress(0);
			                dialog.show();
			            }
			            public void onProgressUpdate(int progress) {
			            	// do something
			            	dialog.setProgress(progress);
			            }
			            public void onPostExecute(ArrayList<String> result) {
			            	// do something
			            	tv2.setText("");
			            	for(String str : result){
			            		String crlf = System.getProperty("line.separator");
			            		tv2.append(crlf+str);
			            	}
			                dialog.dismiss();
			            }
			            public void onCancelled() {
			            	// do something
			            	dialog.dismiss();
			            }
			        });
					asyncGet.execute("https://kcsgogo.herokuapp.com/foods.json");
	                break;
	            case 1:
	                //do what you want when tab 1 is selected
	            	// �^�X�N�̐���
	            	asyncGet = new MyTask(new AsyncCallback() {
			            public void onPreExecute() {
			                // do something
			            	dialog = new ProgressDialog(ShowMainActivity.this);
			                dialog.setTitle("Please wait");
			                dialog.setMessage("Loading data...");
			                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			                dialog.setCancelable(true);
			                dialog.setMax(100);
			                dialog.setProgress(0);
			                dialog.show();
			            }
			            public void onProgressUpdate(int progress) {
			            	// do something
			            	dialog.setProgress(progress);
			            }
			            public void onPostExecute(ArrayList<String> result) {
			            	// do something
			            	tv3.setText("");
			            	for(String str : result){
			            		String crlf = System.getProperty("line.separator");
			            		tv3.append(crlf+str);
			            	}
			                dialog.dismiss();
			            }
			            public void onCancelled() {
			            	// do something
			            	dialog.dismiss();
			            }
			        });
					asyncGet.execute("https://kcsgogo.herokuapp.com/medicines.json");
	                break;
	            case 2:
	                //do what you want when tab 2 is selected
	            	// �^�X�N�̐���
	            	// �^�X�N�̐���
	            	asyncGet = new MyTask(new AsyncCallback() {
			            public void onPreExecute() {
			                // do something
			            	dialog = new ProgressDialog(ShowMainActivity.this);
			                dialog.setTitle("Please wait");
			                dialog.setMessage("Loading data...");
			                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			                dialog.setCancelable(true);
			                dialog.setMax(100);
			                dialog.setProgress(0);
			                dialog.show();
			            }
			            public void onProgressUpdate(int progress) {
			            	// do something
			            	dialog.setProgress(progress);
			            }
			            public void onPostExecute(ArrayList<String> result) {
			            	// do something
			            	tv4.setText("");
			            	for(String str : result){
			            		String crlf = System.getProperty("line.separator");
			            		tv4.append(crlf+str);
			            	}
			                dialog.dismiss();
			            }
			            public void onCancelled() {
			            	// do something
			            	dialog.dismiss();
			            }
			        });
					asyncGet.execute("https://kcsgogo.herokuapp.com/prescriptions.json");
	                break;
	            case 3:
	            	// �^�X�N�̐���
	            	asyncGet = new MyTask(new AsyncCallback() {
			            public void onPreExecute() {
			                // do something
			            	dialog = new ProgressDialog(ShowMainActivity.this);
			                dialog.setTitle("Please wait");
			                dialog.setMessage("Loading data...");
			                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			                dialog.setCancelable(true);
			                dialog.setMax(100);
			                dialog.setProgress(0);
			                dialog.show();
			            }
			            public void onProgressUpdate(int progress) {
			            	// do something
			            	dialog.setProgress(progress);
			            }
			            public void onPostExecute(ArrayList<String> result) {
			            	// do something
			            	tv5.setText("");
			            	for(String str : result){
			            		String crlf = System.getProperty("line.separator");
			            		tv5.append(crlf+str);
			            	}
			                dialog.dismiss();
			            }
			            public void onCancelled() {
			            	// do something
			            	dialog.dismiss();
			            }
			        });
					asyncGet.execute("https://kcsgogo.herokuapp.com/vitals.json");
	            	break;
	            case 4:
	            	// �^�X�N�̐���
	            	asyncGet = new MyTask(new AsyncCallback() {
			            public void onPreExecute() {
			                // do something
			            	dialog = new ProgressDialog(ShowMainActivity.this);
			                dialog.setTitle("Please wait");
			                dialog.setMessage("Loading data...");
			                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			                dialog.setCancelable(true);
			                dialog.setMax(100);
			                dialog.setProgress(0);
			                dialog.show();
			            }
			            public void onProgressUpdate(int progress) {
			            	// do something
			            	dialog.setProgress(progress);
			            }
			            public void onPostExecute(ArrayList<String> result) {
			            	// do something
			            	tv6.setText("");
			            	for(String str : result){
			            		String crlf = System.getProperty("line.separator");
			            		tv6.append(crlf+str);
			            	}
			                dialog.dismiss();
			            }
			            public void onCancelled() {
			            	// do something
			            	dialog.dismiss();
			            }
			        });
					asyncGet.execute("https://kcsgogo.herokuapp.com/glucoses.json");
					break;
	            default:
	                break;
	            }
	        }
	    });
	}
	
	  
	  
	  public void onActivityResult(int requestCode,int resultCode, Intent intent)
	  {
		  if(requestCode == 1001){
			  if(resultCode == Activity.RESULT_OK){
				  String str = intent.getStringExtra("cal");
				  tv1 = (TextView)findViewById(R.id.textView1);
				  tv1.setText(str);
				  
				  Log.d(TAG,str.substring(8,10));
				  
				  cal.set(Calendar.YEAR, Integer.parseInt(str.substring(0,4)));
				  cal.set(Calendar.MONTH, Integer.parseInt(str.substring(5,7))-1);
				  cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str.substring(8,10)));
				  /*
				  Log.d("もっこし中園", String.valueOf(cal.get(Calendar.YEAR)));
				  Log.d("もっこし中園 Part2", String.valueOf(cal.get(Calendar.MONTH)+1));
				  Log.d("もっこし中園 Part3☆", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
				  */
			  }
		  }
	  }


	  
	  
	  
	@Override
	public void onClick(View v) {
		
		tv2 = (TextView)findViewById(R.id.textView2);
	    tv3 = (TextView)findViewById(R.id.textView3);
	    tv4 = (TextView)findViewById(R.id.textView4);
	    tv5 = (TextView)findViewById(R.id.textView5);
	    tv6 = (TextView)findViewById(R.id.textView6);
	    
		  switch(v.getId()){
			case R.id.button1:
				cal.add(Calendar.DAY_OF_MONTH, -1);
				int year = cal.get(Calendar.YEAR);
			    int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				tv1 = (TextView)findViewById(R.id.textView1);
			    tv1.setText(year+"年"+(month+1)+"月"+day+"日");
			    asyncGet = new MyTask(new AsyncCallback() {
		            public void onPreExecute() {
		                // do something
		            	dialog = new ProgressDialog(ShowMainActivity.this);
		                dialog.setTitle("Please wait");
		                dialog.setMessage("Loading data...");
		                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		                dialog.setCancelable(true);
		                dialog.setMax(100);
		                dialog.setProgress(0);
		                dialog.show();
		            }
		            public void onProgressUpdate(int progress) {
		            	// do something
		            	dialog.setProgress(progress);
		            }
		            public void onPostExecute(ArrayList<String> result) {
		            	// do something
		            	tv2.setText("");
		            	for(String str : result){
		            		String crlf = System.getProperty("line.separator");
		            		tv2.append(crlf+str);
		            	}
		                dialog.dismiss();
		            }
		            public void onCancelled() {
		            	// do something
		            	dialog.dismiss();
		            }
		        });
				asyncGet.execute("https://kcsgogo.herokuapp.com/medicines.json");
			    break;
			case R.id.button2:
				cal.add(Calendar.DAY_OF_MONTH, 1);
				int year1 = cal.get(Calendar.YEAR);
			    int month1 = cal.get(Calendar.MONTH);
				int day1 = cal.get(Calendar.DAY_OF_MONTH);
				tv1 = (TextView)findViewById(R.id.textView1);
			    tv1.setText(year1+"年"+(month1+1)+"月"+day1+"日");
			    /*
			    Log.d("もっこしコシKOSI中園", String.valueOf(cal.get(Calendar.YEAR)));
				Log.d("もっこしコシKOSI中園 Part2", String.valueOf(cal.get(Calendar.MONTH)+1));
				Log.d("もっこしコシKOSI中園 Part3☆", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
				*/
			    asyncGet = new MyTask(new AsyncCallback() {
		            public void onPreExecute() {
		                // do something
		            	dialog = new ProgressDialog(ShowMainActivity.this);
		                dialog.setTitle("Please wait");
		                dialog.setMessage("Loading data...");
		                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		                dialog.setCancelable(true);
		                dialog.setMax(100);
		                dialog.setProgress(0);
		                dialog.show();
		            }
		            public void onProgressUpdate(int progress) {
		            	// do something
		            	dialog.setProgress(progress);
		            }
		            public void onPostExecute(ArrayList<String> result) {
		            	// do something
		            	tv2.setText("");
		            	for(String str : result){
		            		String crlf = System.getProperty("line.separator");
		            		tv2.append(crlf+str);
		            	}
		                dialog.dismiss();
		            }
		            public void onCancelled() {
		            	// do something
		            	dialog.dismiss();
		            }
		        });
				asyncGet.execute("https://kcsgogo.herokuapp.com/medicines.json");
			    break;
			case R.id.textView1:
				Intent intent = new Intent(ShowMainActivity.this,GggggggMainActivity.class);
				int requestCode =1001;
				String calll = cal.get(Calendar.YEAR)+""+String.format("%02d", cal.get(Calendar.MONTH));
				intent.putExtra("calcal", calll);
				startActivityForResult(intent, requestCode);
				break;
			}
		}
}

