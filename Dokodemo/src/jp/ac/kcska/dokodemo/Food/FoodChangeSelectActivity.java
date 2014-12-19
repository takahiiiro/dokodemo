package jp.ac.kcska.dokodemo.Food;

import java.util.ArrayList;
import java.util.List;

import jp.ac.kcska.dokodemo.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FoodChangeSelectActivity extends Activity {
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_food_change_select);
		//ListView lisview = new ListView(this);
		//setContentView(lisview);
		
		ListView lisview = (ListView)findViewById(R.id.listView1);
		//List<CardItem> objects = new ArrayList<CardItem>();

//		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//		adapter.add("07:30\n朝だよ");
//		adapter.add("12:40\n昼だよ");
		ArrayList<String> list = new ArrayList<String>();
		for(int i=1; i<=200; i++){
			
			//Log.d("555555%%%%", String.valueOf(i%3));
            list.add("List Item "+i);
            
            //CardItem tmpItem = new CardItem();
            //tmpItem.setTitleText1("カードUIをアニメーション付きで実装してみました:" + i);
            //objects.add(tmpItem);
            
        }
		
		CustomAdapter mAdapter = new CustomAdapter(this, 0, list);
		//CardArrayAdapter adapter = new CardArrayAdapter(this, 0, objects);
        
		//lisview.setAdapter(adapter);
		//lisview.setSelector(android.R.color.transparent);
		lisview.setAdapter(mAdapter);
		lisview.setDivider(null);
		
		
		Intent i = getIntent();
		String date = i.getStringExtra("date");
		TextView text = (TextView)findViewById(R.id.textView1);
		text.setText(date);
		
		
		 lisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                //ListView listView = (ListView) parent;
	                // クリックされたアイテムを取得します
	               // String item = (String) listView.getItemAtPosition(position);
	                Intent intent=new Intent(FoodChangeSelectActivity.this,FoodSign_upActivity.class);
				    startActivity(intent);
	            }
	        });

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_change_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_food_change_select, container, false);
			return rootView;
		}
	}

}
