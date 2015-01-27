package jp.ac.kcska.dokodemo.Home;

import java.util.ArrayList;

import jp.ac.kcska.dokodemo.R;
import jp.ac.kcska.dokodemo.Avatar.AvatarMainActivity;
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
 

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.widget.LinearLayout;

import android.app.ProgressDialog;


public class HomeMainActivity extends Activity {
    /** Called when the activity is first created. */
	static ArrayList<String> list;
	private ProgressDialog dialog;
	private MyTask asyncGet;
	 
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
        
        list = new ArrayList<String>();
		
		//Task
		asyncGet = new MyTask(new AsyncCallback() {
            public void onPreExecute() {
                // do something
            	dialog = new ProgressDialog(HomeMainActivity.this);
                dialog.setTitle("Please wait");
                dialog.setMessage("Loading data...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
            	list = result;
                dialog.dismiss();
                
              //レイアウトに表示
        		LinearLayout mainFrame = (LinearLayout)findViewById(R.id.layout1);	
        		GraphicalView lineChartGraph = LineChart.execute(HomeMainActivity.this);
        		mainFrame.addView(lineChartGraph);
        			
            }
            public void onCancelled() {
            	// do something
            	dialog.dismiss();
            }
        });
		asyncGet.execute("https://kcsgogo.herokuapp.com/glucoses.json");
		  
        imageButton1.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                //アバター管理画面を起動
                Intent intent = new Intent(HomeMainActivity.this,AvatarMainActivity.class);
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
    		public static GraphicalView execute(Context context) {
    			
    			//血糖値のグラフ
    			series = new XYSeries("血糖値");
    			double x,y;
    			
    			for(int i=0;i<21;i++){
    				Double value = Double.parseDouble(list.get(i));
    				x = i;
    				y = value;
    				series.add(x, y);
    			}
    			
    			//食後の基準値グラフ
                series2 = new XYSeries("食後の基準値");		 
    			double a,b;
    	 
    	        for (int i = 0; i < 21; i++) {
    	            a = i;
    	            b = 140;
    	            series2.add(a, b);
    	        }
    	        
    	      //空腹時の基準値グラフ
                series3 = new XYSeries("空腹時の基準値");		 
    			double c,d;
    	 
    	        for (int i = 0; i < 21; i++) {
    	            c = i;
    	            d = 100;
    	            series3.add(c, d);
    	        }
    	        
    			//インテントではなくGraphicalViewを戻り値に
    			GraphicalView gv = ChartFactory.getLineChartView(context, getDataset(), getRenderer());
    			return gv;
    		}
    		public static XYMultipleSeriesDataset getDataset() {
    			XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
    			dataSet.addSeries(series);
    			dataSet.addSeries(series2);
    			dataSet.addSeries(series3);
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
    			
    			XYSeriesRenderer renderer3 = new XYSeriesRenderer();
    			renderer3.setColor(Color.BLUE); //グラフの色
    			renderer3.setLineWidth(2); //グラフの幅
    			renderer3.setPointStyle(PointStyle.SQUARE);	//ポイント設定
    			renderer3.setPointStrokeWidth(8);
    			renderer3.setFillPoints(true);
    			
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
    			mRenderer.addSeriesRenderer(renderer3);
    			
    			return mRenderer;		
    		}
    		
    	}
    	
}