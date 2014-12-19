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
    // ���O�o�͎��̃^�O
    private final String TAG = "Chapter0417";
    // �J�����_�[��\������GridLayout
    private GridLayout mGridLayout1;
    // �����ɑ΂���\�����̈ʒu
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

        // ��ʂ̃C���X�^���X���擾
        mGridLayout1 = (GridLayout) findViewById(R.id.gridLayout1);

        
        // �挎�{�^����Click���̃��X�i�[��ݒ�
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition--;
                buildCalendar();
            }
        });

        // �����{�^����Click���̃��X�i�[��ݒ�s
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition++;
                buildCalendar();
            }
        });
        
        Intent intent = getIntent();
        String strcal = intent.getStringExtra("calcal");
        Log.d("���났MAX", strcal);
        
        stryear = Integer.parseInt(strcal.substring(0,4));
        Log.d("���났�l�`�w", String.valueOf(stryear));
        strmonth = Integer.parseInt(strcal.substring(4,6));
        Log.d("���났�l�`�w", String.valueOf(strmonth));
        
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, stryear);
    	cal.set(Calendar.MONTH, strmonth);
    	cal.set(Calendar.DAY_OF_MONTH,1);
    	
    	Log.d("�d����", String.valueOf(cal.get(Calendar.YEAR)));
        
        // �J�����_�[�f�[�^��ݒ�
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

        // �\�����̈ʒu��ۑ�
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = shared.edit();
        editor.putInt("key.position", mPosition);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        // �^�b�v���ꂽView�ɐݒ肳��Ă���CalendarEvent���擾
        
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
		Log.d("log�����I", String.valueOf(str));
		
		int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    //int day = cal.get(Calendar.DATE);
	    int day = Integer.parseInt(str);
	    Log.d("log�����I", String.valueOf(day));
	    
	    if(month==0){
	    	year=year-1;
	    	month=12;
	    }
	    
	    String moonth = String.format("%02d", month);
	    String daay = String.format("%02d", day);
		String format1 = year+"�N"+moonth+"��"+daay+"��";
		
		Intent intent = new Intent();
		
		//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		intent.putExtra("cal",format1);
		setResult(Activity.RESULT_OK, intent);
		finish();
        
    }

    /***
     * <�N4��>/<���Q��>�̃t�H�[�}�b�g�ŕ�������쐬�����\���p��TextView�ɐݒ�
     * 
     * @param calendar
     */
    private void setTitle(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(sdf.format(calendar.getTime()));
    }

    /***
     * �J�����_�[�f�[�^��ݒ�
     */
    private void buildCalendar() {
        // �\�����錎�̃|�W�V������p���ăJ�����_�[�𐶐�
        //Calendar current = Calendar.getInstance();
        cal.add(Calendar.MONTH, mPosition);
        cal.set(Calendar.DAY_OF_MONTH,1);	
        // �^�C�g����ݒ�
        setTitle(cal);   
        //cal = current;
        // �J�����_�[�f�[�^�����Z�b�g
        mGridLayout1.removeAllViews();
        // �j����ݒ�
        addDayOfWeek();
        // �����̃f�[�^��ݒ�
        addDayOfMonth(cal);
    }
    
    private void buildCalendar(int stryear,int strmonth) {
    	Calendar current = Calendar.getInstance();
    	current.set(Calendar.YEAR, stryear);
    	current.set(Calendar.MONTH, strmonth);
    	current.set(Calendar.DAY_OF_MONTH,1);
    	// �^�C�g����ݒ�
        setTitle(current);
        cal = current;
        // �J�����_�[�f�[�^�����Z�b�g
        mGridLayout1.removeAllViews();
        // �j����ݒ�
        addDayOfWeek();
        // �����̃f�[�^��ݒ�
        addDayOfMonth(current);
    }

    /***
     * �j����ݒ�
     */
    private void addDayOfWeek() {
        // ���`�y�̗j���̕�������擾
        String[] weeks = getResources().getStringArray(R.array.week_names);
        for (int i = 0; i < weeks.length; i++) {
            // �j���̃��C�A�E�g�𐶐�
            View child = getLayoutInflater().inflate(R.layout.calendar_week, null);
            // �j����ݒ肷��TextView�̃C���X�^���X���擾
            TextView textView1 = (TextView) child.findViewById(R.id.textView1);
            // �j����TextView�ɐݒ�
            textView1.setText(weeks[i]);
            // �j�������j���Ȃ�ԁA�����łȂ���΍��̃e�L�X�g�J���[��ݒ�
            textView1.setTextColor(i == 0 ? Color.RED : Color.BLACK);

            // �쐬���ꂽ�j���̃��C�A�E�g��GridLayout�ɒǉ�
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.FILL_HORIZONTAL);
            mGridLayout1.addView(child);
        }
    }

    /***
     * �����̃f�[�^��ݒ�
     * 
     * @param calendar
     */
    private void addDayOfMonth(Calendar calendar) {
        // �\�����̍ő�������擾
        int maxdate = calendar.getMaximum(Calendar.DAY_OF_MONTH);

        // �ő�������J��Ԃ��������s��
        for (int i = 0; i < maxdate; i++) {
            // �����̃��C�A�E�g�𐶐�.
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
            // �����̃��C�A�E�g����eView�̃C���X�^���X���擾
            TextView textView1 = (TextView) child.findViewById(R.id.textView1);
            TextView textView2 = (TextView) child.findViewById(R.id.textView2);
            tv3 = (TextView) child.findViewById(R.id.textView3);
            // �쐬������̗j�����擾
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            
        	

            // ����\������View��Click���̃��X�i�[��ݒ�
            child.setOnClickListener(this);
            // �쐬������̓��t��TextView�ɐݒ�
            textView1.setText(Integer.toString(i + 1));
            // �쐬����������j���Ȃ�ԁA�����łȂ���΍��̃e�L�X�g�J���[��ݒ�
            textView1.setTextColor(week == Calendar.SUNDAY ? Color.RED : Color.BLACK);

            // �쐬������̃C�x���g��S�Ď擾
            List<CalendarEvent> events = queryEvent(calendar);
            
            
            //if()
            // �����̃C�x���g������΁u�E�E�E�v��\��
            if (events.size() > 1) {
                tv3.setVisibility(View.VISIBLE);
            }
            if (events != null && events.size() > 0) {
                // �C�x���g������Έ�ڂ̃C�x���g�̃^�C�g�����擾��TextView�ɐݒ�
                child.setTag(events);
            }
            else {
                // �C�x���g���Ȃ��ꍇ�̓u�����N��ݒ�
                textView2.setText("");
                tv3.setVisibility(View.GONE);
            }

            // �쐬���ꂽ���̃��C�A�E�g��GridLayout�ɒǉ�
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            if (i == 0) {
                params.rowSpec = GridLayout.spec(1);
                params.columnSpec = GridLayout.spec(week - Calendar.SUNDAY);
            }
            params.setGravity(Gravity.FILL_HORIZONTAL);
            mGridLayout1.addView(child, params);

            // ���ɂ����P���i�߂�
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /***
     * �w�肳�ꂽ���̃C�x���g������
     * 
     * @param calendar
     * @return
     */
    private List<CalendarEvent> queryEvent(Calendar calendar) {
        // �ԋp�p�̃��X�g�𐶐�
        List<CalendarEvent> result = new ArrayList<CalendarEvent>();

        // �C�x���g�f�[�^����擾����t�B�[���h���쐬
        String[] projection = new String[] {
                CalendarContract.Instances.EVENT_ID, CalendarContract.Instances.TITLE,
                CalendarContract.Instances.BEGIN, CalendarContract.Instances.END
        };

        // �J�n�������쐬
        Calendar beginTime = (Calendar) calendar.clone();
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        beginTime.set(Calendar.SECOND, 0);
        // �I���������쐬
        Calendar endTime = (Calendar) beginTime.clone();
        endTime.add(Calendar.DAY_OF_MONTH, 1);

        // �C�x���g����������Uri���쐬
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, beginTime.getTimeInMillis());
        ContentUris.appendId(builder, endTime.getTimeInMillis());

        Log.d(TAG,
                new Date(beginTime.getTimeInMillis()) + "," + new Date(endTime.getTimeInMillis())
                        + ":retrive");
        // �C�x���g�̌��������s
        // ���ӁF���̏����͖{���o�b�N�O���E���h�ōs���ׂ������A�������ȗ������邽��UI�X���b�h�ōs���Ă���B
        Cursor cursor = getContentResolver().query(builder.build(), projection, null, null, null);
        try {
            // �C�x���g�f�[�^������ꍇ�͏������s��
            if (cursor.moveToFirst()) {
                do {
                    // projection�Ŏw�肳�ꂽ���ԂŃf�[�^������ł���A�P���ڂɓ����Ă���EVENT_ID��p����CalendarEvent�𐶐�
                    CalendarEvent event = new CalendarEvent(cursor.getLong(0));
                    // CalendarEvent�̃C���X�^���X�Ɏ擾���ꂽ�f�[�^��ݒ�
                    event.setTitle(cursor.getString(1));
                    long start = cursor.getLong(2);
                    long end = cursor.getLong(3);
                    Log.d(TAG,
                            new Date(start) + "," + new Date(end) + ":query " + event.getId() + ":"
                                    + event.getTitle());
                    // �ԋp�p�̃��X�g�ɒǉ�
                    result.add(event);
                } while (cursor.moveToNext());
            }
        } finally {
            // �I�����ɃJ�[�\���Ƃ����
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
}
