package jp.ac.kcska.dokodemo.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlOpenHelper extends SQLiteOpenHelper {
	private final static String DB_NAME = "FoodManager";
	final static private int DB_VERSION = 1;
	private Context mContext;
	public SqlOpenHelper(Context context) {
		
		super(context, DB_NAME, null, DB_VERSION);
		// TODO 自動生成されたコンストラクター・スタブ
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ
		try {
            execSql(db,"sql/create");
        } catch (IOException e) {
            e.printStackTrace();
        }    
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO 自動生成されたメソッド・スタブ
		try {
            execSql(db,"sql/drop");
        } catch (IOException e) {
            e.printStackTrace();
        }
        onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
            execSql(db,"sql/drop");
        } catch (IOException e) {
            e.printStackTrace();
        }
        onCreate(db);
	}
	
	private void execSql(SQLiteDatabase db,String assetsDir) throws IOException {
        AssetManager as = mContext.getResources().getAssets();    
        try {
            String files[] = as.list(assetsDir);
            for (int i = 0; i < files.length; i++) {    
                String str = readFile(as.open(assetsDir + "/" + files[i]));
                for (String sql: str.split("/")){
                    db.execSQL(sql);
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private String readFile(InputStream is) throws IOException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is,"SJIS"));

            StringBuilder sb = new StringBuilder();    
            String str;      
            while((str = br.readLine()) != null){      
                sb.append(str +"\n");     
            }    
            return sb.toString();
        } finally {
            if (br != null) br.close();
        }
    }
	
}
