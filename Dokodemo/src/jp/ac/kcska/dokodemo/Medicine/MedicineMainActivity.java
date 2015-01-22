package jp.ac.kcska.dokodemo.Medicine;

import java.util.Calendar;


import jp.ac.kcska.dokodemo.R;
import jp.ac.kcska.dokodemo.R.id;
import jp.ac.kcska.dokodemo.R.layout;
import jp.ac.kcska.dokodemo.R.menu;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Build;
import android.view.View.OnClickListener;





public class MedicineMainActivity extends Activity {
	public int count = 5;
	private TimePickerDialog.OnTimeSetListener varTimeSetListener;
	
	private final String[] item = new String[]{
			"内服薬 1",
			"内服薬 2",
			"外用薬 3"
	};
	
	private TextView tv1;
	private MyTask Task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_main);
        
        //時間
        final Calendar calender = Calendar.getInstance();
       //時間の取得(24時間単位)
       int hour = calender.get(Calendar.HOUR_OF_DAY);
       //分の取得
		
     		int min = calender.get(Calendar.MINUTE);
     		
     		
     		final TextView text = (TextView)findViewById(R.id.time);
     		text.setText(hour + ":" + String.format("%02d", min));
     		
     		varTimeSetListener = new OnTimeSetListener() {
     			@Override
     			public void onTimeSet(TimePicker view, int hour, int min) {
     				
     				text.setText(hour + ":" + String.format("%02d", min));
     				
     			}
     		};
     		((TextView)findViewById(R.id.time))
     		.setOnClickListener(new View.OnClickListener() {
     			
     			
     			@Override
     			public void onClick(View v) {
     				// TODO 自動生成されたメソッド・スタブ
     				
     				
     				TimePickerDialog timeDialog = new TimePickerDialog(
     						MedicineMainActivity.this,
     						varTimeSetListener,
     						calender.get(Calendar.HOUR_OF_DAY),
     						calender.get(Calendar.MINUTE),
     						true);
     				
     				timeDialog.show();
     				
     			}
     		});
     			//時計終わり
        
       
        tv1 = (TextView) findViewById(R.id.textView1);
        
        Task = new MyTask(tv1,this);
        Task.execute("http://api.atnd.org/events/?keyword=android&format=json");
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,item );
        //adapter.add("listview item 1");
        //adapter.add("listview.item 2");
        //adapter.add("listview.item 3");
        
        
        ListView listView = (ListView)findViewById(R.id.check_list);
        listView.setAdapter(adapter);
        
        fncDispCount();
        
        //ボタン
        Button btn = (Button)findViewById(R.id.btnOK);
        btn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		//選択アイテム取得
        		ListView check_list = (ListView)findViewById(R.id.check_list);
        		SparseBooleanArray checked = check_list.getCheckedItemPositions();
        		
        		//判定
        		StringBuilder sb = new StringBuilder();
        		for (int i=0; i<checked.size(); i++){
        			if (checked.valueAt(i)){
        			//正しい	
        			//sb.append(item[i]+",");
        			//試し
        				sb.append(item[i]+"\n");
        			
        		}
        	}
        		//ダイアログ
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MedicineMainActivity.this);
				alertDialogBuilder.setTitle("この内容で登録しますか？");
				//alertDialogBuilder.setMessage("この内容で登録しますか？");
				
				//チェックした内容の表示
				alertDialogBuilder.setMessage(sb);
				
				
				//確認画面処理
				alertDialogBuilder.setPositiveButton("ok", 
						new DialogInterface.OnClickListener() {
					
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO 自動生成されたメソッド・スタブ
								//トースト表示
								Toast.makeText(MedicineMainActivity.this, "登録しました", Toast.LENGTH_LONG).show();
								//トースト終了
								
								//残数減らす
								count--;
								fncDispCount();
							}
						});
				alertDialogBuilder.setNegativeButton("cancel", 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO 自動生成されたメソッド・スタブ
								
							}
						});
				alertDialogBuilder.setCancelable(true);
				AlertDialog alertDialog = alertDialogBuilder.create();
				
				alertDialog.show();
        	}
        });
     }
  //カウントの値表示
  	public void fncDispCount(){
  		TextView tv=(TextView)findViewById(R.id.textView6);
  		tv.setText(String.format("残数%d",count ));
  	}
    
}  


   // @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
   //     getMenuInflater().inflate(R.menu.main, menu);
    //    return true;
   // }

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();
     //   if (id == R.id.action_settings) {
       //     return true;
      //  }
        //return super.onOptionsItemSelected(item);
   // }

    /**
     * A placeholder fragment containing a simple view.
     */
    //public static class PlaceholderFragment extends Fragment {

      //  public PlaceholderFragment() {
        //}

      //  @Override
        //public View onCreateView(LayoutInflater inflater, ViewGroup container,
          //      Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //return rootView;
        //}
   // }

//}
