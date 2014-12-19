package jp.ac.kcska.dokodemo.Show;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class CalMainActivity extends Activity implements OnClickListener {
    // ログ出力時のタグ
    private final String TAG = "Chapter0417";
    // カレンダーを表示するGridLayout
    private GridLayout mGridLayout1;
    // 当月に対する表示月の位置
    private int mPosition = 0;
    
    //private MyTask task;
    
    private Calendar cal;
    private ProgressDialog dialog;
	private MyTask asyncGet;
    
    TextView tv1,tv2,tv3;
    int i =0;
    private int stryear,strmonth;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);

        // 画面のインスタンスを取得
        mGridLayout1 = (GridLayout) findViewById(R.id.gridLayout1);

        
        // 先月ボタンのClick時のリスナーを設定
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition--;
                buildCalendar();
            }
        });

        // 来月ボタンのClick時のリスナーを設定s
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition++;
                buildCalendar();
            }
        });
        
        Intent intent = getIntent();
        String strcal = intent.getStringExtra("calcal");
        Log.d("くつろぎMAX", strcal);
        
        stryear = Integer.parseInt(strcal.substring(0,4));
        Log.d("くつろぎＭＡＸ", String.valueOf(stryear));
        strmonth = Integer.parseInt(strcal.substring(4,6));
        Log.d("くつろぎＭＡＸ", String.valueOf(strmonth));
        
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, stryear);
    	cal.set(Calendar.MONTH, strmonth);
    	cal.set(Calendar.DAY_OF_MONTH,1);
    	
    	Log.d("重すぎ", String.valueOf(cal.get(Calendar.YEAR)));
        
        // カレンダーデータを設定
        buildCalendar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 表示月の位置を保存
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = shared.edit();
        editor.putInt("key.position", mPosition);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        // タップされたViewに設定されているCalendarEventを取得
        
		List<CalendarEvent> event = (List<CalendarEvent>) v.getTag();
		
		/*
		final Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		*/

		TextView tv = (TextView) v.findViewById(R.id.textView1);
		String str = (String) tv.getText();
		Log.d("logだお！", String.valueOf(str));
		
		int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    //int day = cal.get(Calendar.DATE);
	    int day = Integer.parseInt(str);
	    Log.d("logだお！", String.valueOf(day));
	    
	    if(month==0){
	    	year=year-1;
	    	month=12;
	    }
	    
	    String moonth = String.format("%02d", month);
	    String daay = String.format("%02d", day);
		String format1 = year+"年"+moonth+"月"+daay+"日";
		
		Intent intent = new Intent();
		
		//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		intent.putExtra("cal",format1);
		setResult(Activity.RESULT_OK, intent);
		finish();
        
    }

    /***
     * <年4桁>/<月２桁>のフォーマットで文字列を作成し月表示用のTextViewに設定
     * 
     * @param calendar
     */
    private void setTitle(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(sdf.format(calendar.getTime()));
    }

    /***
     * カレンダーデータを設定
     */
    private void buildCalendar() {
        // 表示する月のポジションを用いてカレンダーを生成
        //Calendar current = Calendar.getInstance();
        cal.add(Calendar.MONTH, mPosition);
        cal.set(Calendar.DAY_OF_MONTH,1);	
        // タイトルを設定
        setTitle(cal);   
        //cal = current;
        // カレンダーデータをリセット
        mGridLayout1.removeAllViews();
        // 曜日を設定
        addDayOfWeek();
        // 日毎のデータを設定
        addDayOfMonth(cal);
    }
    
    private void buildCalendar(int stryear,int strmonth) {
    	Calendar current = Calendar.getInstance();
    	current.set(Calendar.YEAR, stryear);
    	current.set(Calendar.MONTH, strmonth);
    	current.set(Calendar.DAY_OF_MONTH,1);
    	// タイトルを設定
        setTitle(current);
        cal = current;
        // カレンダーデータをリセット
        mGridLayout1.removeAllViews();
        // 曜日を設定
        addDayOfWeek();
        // 日毎のデータを設定
        addDayOfMonth(current);
    }

    /***
     * 曜日を設定
     */
    private void addDayOfWeek() {
        // 日～土の曜日の文字列を取得
        String[] weeks = getResources().getStringArray(R.array.week_names);
        for (int i = 0; i < weeks.length; i++) {
            // 曜日のレイアウトを生成
            View child = getLayoutInflater().inflate(R.layout.calendar_week, null);
            // 曜日を設定するTextViewのインスタンスを取得
            TextView textView1 = (TextView) child.findViewById(R.id.textView1);
            // 曜日をTextViewに設定
            textView1.setText(weeks[i]);
            // 曜日が日曜日なら赤、そうでなければ黒のテキストカラーを設定
            textView1.setTextColor(i == 0 ? Color.RED : Color.BLACK);

            // 作成された曜日のレイアウトをGridLayoutに追加
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.FILL_HORIZONTAL);
            mGridLayout1.addView(child);
        }
    }

    /***
     * 日毎のデータを設定
     * 
     * @param calendar
     */
    private void addDayOfMonth(Calendar calendar) {
        // 表示月の最大日数を取得
        int maxdate = calendar.getMaximum(Calendar.DAY_OF_MONTH);

        // 最大日数分繰り返し処理を行う
        for (int i = 0; i < maxdate; i++) {
            // 日毎のレイアウトを生成.
        	/*
        	asyncGet = new MyTask(new AsyncCallback() {
	            public void onPreExecute() {
	                // do something
	            	dialog = new ProgressDialog(MainActivity.this);
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
	            	tv2.setText(result.get(0));
	                dialog.dismiss();
	            }
	            public void onCancelled() {
	            	// do something
	            	dialog.dismiss();
	            }
	        });
			asyncGet.execute("http://192.168.33.10:3000/formuras.json");
        	*/
            View child = getLayoutInflater().inflate(R.layout.calendar_date, null);
            // 日毎のレイアウトから各Viewのインスタンスを取得
            TextView textView1 = (TextView) child.findViewById(R.id.textView1);
            TextView textView2 = (TextView) child.findViewById(R.id.textView2);
            tv3 = (TextView) child.findViewById(R.id.textView3);
            // 作成する日の曜日を取得
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            
        	

            // 日を表示するViewにClick時のリスナーを設定
            child.setOnClickListener(this);
            // 作成する日の日付をTextViewに設定
            textView1.setText(Integer.toString(i + 1));
            // 作成する日が日曜日なら赤、そうでなければ黒のテキストカラーを設定
            textView1.setTextColor(week == Calendar.SUNDAY ? Color.RED : Color.BLACK);

            // 作成する日のイベントを全て取得
            List<CalendarEvent> events = queryEvent(calendar);
            
            
            //if()
            // 複数のイベントがあれば「・・・」を表示
            if (events.size() > 1) {
                tv3.setVisibility(View.VISIBLE);
            }
            if (events != null && events.size() > 0) {
                // イベントがあれば一つ目のイベントのタイトルを取得しTextViewに設定
                child.setTag(events);
            }
            else {
                // イベントがない場合はブランクを設定
                textView2.setText("");
                tv3.setVisibility(View.GONE);
            }

            // 作成された日のレイアウトをGridLayoutに追加
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            if (i == 0) {
                params.rowSpec = GridLayout.spec(1);
                params.columnSpec = GridLayout.spec(week - Calendar.SUNDAY);
            }
            params.setGravity(Gravity.FILL_HORIZONTAL);
            mGridLayout1.addView(child, params);

            // 日にちを１日進める
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /***
     * 指定された日のイベントを検索
     * 
     * @param calendar
     * @return
     */
    private List<CalendarEvent> queryEvent(Calendar calendar) {
        // 返却用のリストを生成
        List<CalendarEvent> result = new ArrayList<CalendarEvent>();

        // イベントデータから取得するフィールドを作成
        String[] projection = new String[] {
                CalendarContract.Instances.EVENT_ID, CalendarContract.Instances.TITLE,
                CalendarContract.Instances.BEGIN, CalendarContract.Instances.END
        };

        // 開始日時を作成
        Calendar beginTime = (Calendar) calendar.clone();
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        beginTime.set(Calendar.SECOND, 0);
        // 終了日時を作成
        Calendar endTime = (Calendar) beginTime.clone();
        endTime.add(Calendar.DAY_OF_MONTH, 1);

        // イベントを検索するUriを作成
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, beginTime.getTimeInMillis());
        ContentUris.appendId(builder, endTime.getTimeInMillis());

        Log.d(TAG,
                new Date(beginTime.getTimeInMillis()) + "," + new Date(endTime.getTimeInMillis())
                        + ":retrive");
        // イベントの検索を実行
        // 注意：この処理は本来バックグラウンドで行うべきだが、処理を簡略化するためUIスレッドで行っている。
        Cursor cursor = getContentResolver().query(builder.build(), projection, null, null, null);
        try {
            // イベントデータがある場合は処理を行う
            if (cursor.moveToFirst()) {
                do {
                    // projectionで指定された順番でデータが並んでいる、１件目に入っているEVENT_IDを用いてCalendarEventを生成
                    CalendarEvent event = new CalendarEvent(cursor.getLong(0));
                    // CalendarEventのインスタンスに取得されたデータを設定
                    event.setTitle(cursor.getString(1));
                    long start = cursor.getLong(2);
                    long end = cursor.getLong(3);
                    Log.d(TAG,
                            new Date(start) + "," + new Date(end) + ":query " + event.getId() + ":"
                                    + event.getTitle());
                    // 返却用のリストに追加
                    result.add(event);
                } while (cursor.moveToNext());
            }
        } finally {
            // 終了時にカーソルとを閉じる
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
}
