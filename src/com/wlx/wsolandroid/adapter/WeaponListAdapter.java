package com.wlx.wsolandroid.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.model.Weapon;

/**
 *金牌武器上升值列表适配器
 * 
 * @author wanglixin
 */
public class WeaponListAdapter extends BaseAdapter {

    private List<Weapon>  lists = new ArrayList<Weapon>();

    private final Context context;

    public WeaponListAdapter(Context context, List<Weapon> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }
    
    public void setData(List<Weapon> lists){
        this.lists = lists;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_weapon, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_r1 = (TextView) convertView.findViewById(R.id.tv_r1);
            holder.tv_r2 = (TextView) convertView.findViewById(R.id.tv_r2);
            holder.tv_r3 = (TextView) convertView.findViewById(R.id.tv_r3);
            holder.tv_r4 = (TextView) convertView.findViewById(R.id.tv_r4);
            holder.tv_r5 = (TextView) convertView.findViewById(R.id.tv_r5);
            holder.tv_r6 = (TextView) convertView.findViewById(R.id.tv_r6);
            holder.tv_r7 = (TextView) convertView.findViewById(R.id.tv_r7);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Weapon weapon = lists.get(position);

        StringBuilder sb1 = new StringBuilder("R1   ");
        sb1.append(weapon.getG()).append(weapon.getP()).append(weapon.getF()).append(weapon.getT())
                .append(weapon.getW());

        StringBuilder sb2 = new StringBuilder();
        sb2.append((weapon.getG() + 3) % 10).append((weapon.getP() + 3) % 10)
                .append((weapon.getF() + 3) % 10).append((weapon.getT() + 3) % 10)
                .append((weapon.getW() + 3) % 10).append("   R2");

        StringBuilder sb3 = new StringBuilder("R3   ");
        sb3.append((weapon.getG() + 6) % 10).append((weapon.getP() + 6) % 10)
                .append((weapon.getF() + 6) % 10).append((weapon.getT() + 6) % 10)
                .append((weapon.getW() + 6) % 10);

        StringBuilder sb4 = new StringBuilder();
        sb4.append((weapon.getG() + 9) % 10).append((weapon.getP() + 9) % 10)
                .append((weapon.getF() + 9) % 10).append((weapon.getT() + 9) % 10)
                .append((weapon.getW() + 9) % 10).append("   R4");

        StringBuilder sb5 = new StringBuilder("R5   ");
        sb5.append((weapon.getG() + 12) % 10).append((weapon.getP() + 12) % 10)
                .append((weapon.getF() + 12) % 10).append((weapon.getT() + 12) % 10)
                .append((weapon.getW() + 12) % 10);

        StringBuilder sb6 = new StringBuilder();
        sb6.append((weapon.getG() + 15) % 10).append((weapon.getP() + 15) % 10)
                .append((weapon.getF() + 15) % 10).append((weapon.getT() + 15) % 10)
                .append((weapon.getW() + 15) % 10).append("   R6");

        StringBuilder sb7 = new StringBuilder("R7   ");
        sb7.append((weapon.getG() + 18) % 10).append((weapon.getP() + 18) % 10)
                .append((weapon.getF() + 18) % 10).append((weapon.getT() + 18) % 10)
                .append((weapon.getW() + 18) % 10);

        holder.tv_name.setText(weapon.getName());
        holder.tv_r1.setText(sb1);
        holder.tv_r2.setText(sb2);
        holder.tv_r3.setText(sb3);
        holder.tv_r4.setText(sb4);
        holder.tv_r5.setText(sb5);
        holder.tv_r6.setText(sb6);
        holder.tv_r7.setText(sb7);

        return convertView;
    }

    private class ViewHolder {

        TextView tv_name;
        TextView tv_r1;
        TextView tv_r2;
        TextView tv_r3;
        TextView tv_r4;
        TextView tv_r5;
        TextView tv_r6;
        TextView tv_r7;
    }

}
