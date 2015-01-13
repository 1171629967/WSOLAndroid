package com.wlx.wsolandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlx.wsolandroid.R;

/**
 *任务等级列表适配器
 * 
 * @author wanglixin
 */
public class NeizhengdengjiListAdapter extends BaseAdapter {

    private String[] totalCountArray;

    private final Context context;

    public NeizhengdengjiListAdapter(Context context, String[] totalCountArray) {
        this.context = context;
        this.totalCountArray = totalCountArray;
    }

    @Override
    public int getCount() {
        return totalCountArray.length;
    }
    
    

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_neizhengdengji, null);
            holder.tv_lvNumber = (TextView) convertView.findViewById(R.id.tv_lvNumber);
            holder.tv_nextNeed = (TextView) convertView.findViewById(R.id.tv_nextNeed); 
            holder.tv_lastDiffer = (TextView) convertView.findViewById(R.id.tv_lastDiffer); 
            holder.tv_totalCount = (TextView) convertView.findViewById(R.id.tv_totalCount);           
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        

        holder.tv_lvNumber.setText((position + 1)+"");
        //总内政值
        holder.tv_totalCount.setText(totalCountArray[position]);
        //升下级所需
        if (position == 0) {
        	holder.tv_nextNeed.setText("10");
		}
        else {
        	int b = Integer.parseInt(totalCountArray[position]);
        	int a = Integer.parseInt(totalCountArray[position-1]);
        	holder.tv_nextNeed.setText((b-a)+"");
		}
        //与上级等级差
        if (position < 50) {
        	holder.tv_lastDiffer.setText("2");
		}
        else if(position >= 50 && position < 90){
        	holder.tv_lastDiffer.setText("4");
        }
        else if(position >= 90 && position < 100){
        	holder.tv_lastDiffer.setText("6");
        }
        else if(position >= 100 && position < 130){
        	holder.tv_lastDiffer.setText("12");
        }
        

        return convertView;
    }

    private class ViewHolder {
        TextView tv_lvNumber;
        TextView tv_nextNeed;
        TextView tv_lastDiffer;
        TextView tv_totalCount;
    }

}
