package jp.ac.kcska.dokodemo.Food;

import java.util.ArrayList;

import jp.ac.kcska.dokodemo.R;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
	static class ViewHolder {
        TextView labelText;
    }
 
    private LayoutInflater inflater;
 
    // コンストラクタ
    public CustomAdapter(Context context,int textViewResourceId, ArrayList<String> labelList) {
        super(context,textViewResourceId, labelList);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         
        ViewHolder holder; 
        View view = convertView;
         
        // Viewを再利用している場合は新たにViewを作らない
        if (view == null) {
            inflater =  (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.food_change_lisv_layout, null);
            TextView label = (TextView)view.findViewById(R.id.tv);
            holder = new ViewHolder();
            holder.labelText = label;
            view.setTag(holder);            
        } else {
            holder = (ViewHolder) view.getTag();
        }
 
        // 特定の行のデータを取得
        String str = getItem(position);
 
        if (!TextUtils.isEmpty(str)) {
            // テキストビューにラベルをセット
            holder.labelText.setText(str);
        }
 
        // 行毎に背景色を変える
        //Log.d("position", String.valueOf(position%3));
        Animation anim;
        if(position%2==0){
            holder.labelText.setBackgroundColor(Color.parseColor("#00BFFF"));//#aa0000
            anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion);
        }else{
            holder.labelText.setBackgroundColor(Color.parseColor("#F08080"));//#880000
            anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion_1);
        }
 
        // XMLで定義したアニメーションを読み込む
        
        // リストアイテムのアニメーションを開始
        view.startAnimation(anim);
 
        return view;
    }

}
