package com.example.tpap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tpap.R;
import com.example.tpap.fragments.MainHomeFragment;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PlanFileAdapter extends BaseAdapter {

    private MainHomeFragment fragment;
    private Context context;
    private List<HashMap<String, String>> planFiles;

    public PlanFileAdapter(MainHomeFragment fragment,Context context, List<HashMap<String, String>> planFiles)
    {
        this.fragment = fragment;
        this.context = context;
        this.planFiles = planFiles;
    }

    @Override
    public int getCount() {
        return planFiles.size();
    }

    @Override
    public Object getItem(int position) {
        return planFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlanFileAdapter.ViewHolder holder;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_plan_file, parent, false);
            holder = new PlanFileAdapter.ViewHolder();

            holder.item_layout = convertView.findViewById(R.id.item_layout);
            holder.primary_text = convertView.findViewById(R.id.primary_text);
            holder.secondary_text = convertView.findViewById(R.id.secondary_text);
            holder.delete_imageButton = convertView.findViewById(R.id.delete_imageButton);

            convertView.setTag(holder);
        } else {
            holder = (PlanFileAdapter.ViewHolder) convertView.getTag();
        }

        HashMap<String, String> planFile = planFiles.get(position);
        holder.primary_text.setText(planFile.get("primary"));
        holder.secondary_text.setText(planFile.get("secondary"));

        String fileName = planFile.get("fileName");

        holder.item_layout.setOnClickListener(v-> {
            fragment.move2PlanDisplayActivity(fileName);
        });
        holder.delete_imageButton.setOnClickListener(v-> {
            File file = new File(context.getFilesDir(), fileName);
            boolean isDeleted = file.delete();
            fragment.setListView();
        });
        return convertView;
    }

    static class ViewHolder {
        RelativeLayout item_layout;
        TextView primary_text;
        TextView secondary_text;
        ImageButton delete_imageButton;
    }
}
