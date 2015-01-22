package jp.ac.kcska.dokodemo.Home;

import jp.ac.kcska.dokodemo.R;
import jp.ac.kcska.dokodemo.Blood.BloodMainActivity;
import jp.ac.kcska.dokodemo.Show.ShowMainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
 



import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface.OnCancelListener;


public class HomeMainActivity extends Activity {
    /** Called when the activity is first created. */
	private ExampleOpenHelper mExampleOpenHelper;
	private TextView textView;
    private ProgressDialog dialog;
    private Context context;
    
    
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
          
      //レイアウトに表示
        LinearLayout mainFrame = (LinearLayout)findViewById(R.id.layout1);
        ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        ImageButton imageButton4 = (ImageButton)findViewById(R.id.imageButton4);
        
        mExampleOpenHelper = new ExampleOpenHelper(HomeMainActivity.this);
		SQLiteDatabase db = mExampleOpenHelper.getReadableDatabase(); //データベース取得
		Cursor cursor = db.rawQuery("SELECT * from example", new String[] {});
		
		GraphicalView lineChartGraph = LineChart.execute(this,cursor);
		mainFrame.addView(lineChartGraph);
		
		cursor.close();
		db.close();
		mExampleOpenHelper.close();
		  
        imageButton1.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                //アバター管理画面を起動
                Intent intent = new Intent();
                startActivity(intent);
            }
        });
        
        imageButton2.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                // ヘルス管理画面を起動
                Intent intent = new Intent(HomeMainActivity.this,health.class);
                startActivity(intent); 
            }
        });
        
        imageButton3.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                // 血糖値・インスリン 画面を起動
                Intent intent = new Intent(HomeMainActivity.this, BloodMainActivity.class);
                startActivity(intent);
            }
        });
        
        imageButton4.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                // 閲覧画面を起動
                Intent intent = new Intent(HomeMainActivity.this, ShowMainActivity.class);
                startActivity(intent);
            }
        });
    }
        
        private class ExampleOpenHelper extends SQLiteOpenHelper {
    		final static private int DB_VERSION = 1;
     
    		public ExampleOpenHelper(Context context) {
    			super(context, "sample.db", null, DB_VERSION);
    		}
     
    		//初回起動時のみ処理される
    		@Override
    		public void onCreate(SQLiteDatabase db) {
    			/**
    			 *  name: example�e�[�u��
    			 *  id:integer | memo:real
    			 */
    			db.execSQL("CREATE TABLE example(id INTEGER PRIMARY KEY AUTOINCREMENT, memo REAL NOT NULL);");
     
    			//初期データの投入
    			for(int i=0;i<=99;i++){
    				db.execSQL("INSERT INTO example(memo) values('"+ Math.random() +"')");
    			}	        
    		}
    		
    		@Override
    		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		}
    	}
     
    	public static class LineChart{
    		static XYSeries series,series2,series3;
    		public static GraphicalView execute(Context context,Cursor cursor) {
    			
    			series = new XYSeries("血糖値");
    			//標準としきい値		 
    			int number = 0;
    			double x,y;
    			boolean next = cursor.moveToFirst();
    			while (next) {
    				x = number;
    				y = cursor.getDouble(1);
    				series.add(x, y);
    				number++;
    				next = cursor.moveToNext();
    			}
    			
                series2 = new XYSeries("基準値");
    			//標準としきい値2		 
    			double a,b;
    	 
    	        for (int i = 0; i < 13; i++) {
    	            a = i;
    	            b = 150;
    	            series2.add(a, b);
    	        }
    	        
    			//インテントではなくGraphicalViewを戻り値に
    			GraphicalView gv = ChartFactory.getLineChartView(context, getDataset(), getRenderer());
    			return gv;
    		}
    		public static XYMultipleSeriesDataset getDataset() {
    			XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
    			dataSet.addSeries(series);
    			dataSet.addSeries(series2);
    			return dataSet;
    		}
    		public static XYMultipleSeriesRenderer getRenderer(){ //個別のグラフ
    			XYSeriesRenderer renderer1 = new XYSeriesRenderer(); 
    			renderer1.setColor(Color.YELLOW); //グラフの色
    			renderer1.setLineWidth(2); //グラフの幅
    			renderer1.setPointStyle(PointStyle.CIRCLE);	//ポイント設定
    			renderer1.setPointStrokeWidth(8);
    			renderer1.setFillPoints(true);
    			
    			XYSeriesRenderer renderer2 = new XYSeriesRenderer();
    			renderer2.setColor(Color.RED); //グラフの色
    			renderer2.setLineWidth(2); //グラフの幅
    			renderer2.setPointStyle(PointStyle.SQUARE);	//ポイント設定
    			renderer2.setPointStrokeWidth(8);
    			renderer2.setFillPoints(true);
    			
    			XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); //グラフ全体
    			mRenderer.setXLabels(6);
    			mRenderer.setXAxisMin(0);//X最小値
    			mRenderer.setXAxisMax(6);// X最大値
    			mRenderer.setYAxisMin(0);// Y最小値
    			mRenderer.setYAxisMax(300);//Y最大値
    			mRenderer.setChartTitle("今日の血糖値の推移");     //グラフタイトル
    			mRenderer.setXTitle("時間");                 //X軸タイトル
    			mRenderer.setYTitle("血糖値");             //Y軸タイトル
    			mRenderer.setApplyBackgroundColor(true); //背景色変更
    			mRenderer.setBackgroundColor(Color.BLACK);
    			mRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01)); //余白色変更
    			mRenderer.setPanEnabled(false);	//グラフを固定
    			mRenderer.setZoomEnabled(true); //ズームを不可に
    			mRenderer.setShowLegend(true); //凡例非表示
    			mRenderer.setAxesColor(Color.GRAY); //軸の色
    			mRenderer.setShowGrid(true); //グリッド表示
    			mRenderer.setGridColor(Color.parseColor("lightgray")); //グリッドの色
    			mRenderer.setLabelsColor(Color.WHITE);          // ラベルカラー
    			mRenderer.setXAxisMin(0);
    			mRenderer.setXAxisMax(6); //表示範囲
    			mRenderer.setLabelsTextSize(10); //ラベル文字サイズ
    			//Y軸ラベル設定
    			mRenderer.setYLabels(6);//Y軸グリッドの密度？
    			mRenderer.setYLabelsColor(0, Color.GRAY);
    			mRenderer.setYLabelsPadding(10);
    			mRenderer.setYLabelsVerticalPadding(-8);
    			mRenderer.addSeriesRenderer(renderer1);
    			mRenderer.addSeriesRenderer(renderer2);
    			
    			return mRenderer;		
    		}
    		
    	}
    	
}