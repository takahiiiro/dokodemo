package jp.ac.kcska.dokodemo.Food;

import java.util.List;

import jp.ac.kcska.dokodemo.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CardArrayAdapter extends ArrayAdapter<CardItem> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CardArrayAdapter(
            Context context,
            int resource,
            List<CardItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent) {

        // Viewは作成済みのものがあれば再利用
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.card_item, null);
        }

        // 行データ取得
        CardItem item = (CardItem) getItem(position);
        Log.d("aaaaaaaa", String.valueOf(getItem(position)));

        TextView title = (TextView) convertView.findViewById(R.id.card_title);
        title.setText(item.getTitleText1());
        

        // アニメーション設定
        Animation anim;
        if (position % 2 == 0) {
            anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion);
        } else {
            anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion_1);
        }

        // アニメーション開始
        convertView.startAnimation(anim);

        return convertView;
    }
}
