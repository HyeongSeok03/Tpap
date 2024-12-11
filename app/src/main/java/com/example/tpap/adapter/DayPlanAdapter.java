package com.example.tpap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tpap.R;

import java.util.HashMap;
import java.util.List;

public class DayPlanAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, String>> dayPlans;

    public DayPlanAdapter(Context context, List<HashMap<String, String>> dayPlans)
    {
        this.context = context;
        this.dayPlans = dayPlans;
    }
    @Override
    public int getCount() {
        return dayPlans.size();
    }

    @Override
    public Object getItem(int position) {
        return dayPlans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_day_plan, parent, false);
            holder = new ViewHolder();
            holder.headerLayout = convertView.findViewById(R.id.headerLayout);
            holder.arrowImageView = convertView.findViewById(R.id.arrowImageView);
            holder.expandableContent = convertView.findViewById(R.id.expandableContent);
            holder.titleTextView = convertView.findViewById(R.id.titleTextView);
            holder.morningTextView = convertView.findViewById(R.id.morning_textView);
            holder.lunchTextView = convertView.findViewById(R.id.lunch_textView);
            holder.afternoonTextView = convertView.findViewById(R.id.afternoon_textView);
            holder.dinnerTextView = convertView.findViewById(R.id.dinner_textView);
            holder.nightTextView = convertView.findViewById(R.id.night_textView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> dayPlan = dayPlans.get(position);
        holder.titleTextView.setText(dayPlan.get("day"));
        holder.morningTextView.setText(dayPlan.get("morning"));
        holder.lunchTextView.setText(dayPlan.get("lunch"));
        holder.afternoonTextView.setText(dayPlan.get("afternoon"));
        holder.dinnerTextView.setText(dayPlan.get("dinner"));
        holder.nightTextView.setText(dayPlan.get("night"));

        holder.headerLayout.setOnClickListener(v -> {
            if (holder.expandableContent.getVisibility() == View.GONE) {
                holder.arrowImageView.setVisibility(View.INVISIBLE);
                holder.expandableContent.setVisibility(View.VISIBLE);
            } else {
                holder.arrowImageView.setVisibility(View.VISIBLE);
                holder.expandableContent.setVisibility(View.GONE);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        RelativeLayout headerLayout;
        ImageView arrowImageView;
        LinearLayout expandableContent;
        TextView titleTextView;
        TextView morningTextView;
        TextView lunchTextView;
        TextView afternoonTextView;
        TextView dinnerTextView;
        TextView nightTextView;
    }
}
