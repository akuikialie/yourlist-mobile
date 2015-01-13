package com.example.alie.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alie.myapplication.R;

import java.util.List;

/**
 * Created by ALIE on 10/01/2015.
 */
public class ProjectAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    Context context;
    List<Project> projectList;
    ProjectAdapter projectAdapter;

    String msg = "Home:";

    public ProjectAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList  = projectList;
    }

    @Override
    public int getCount() {
        return projectList.size();
    }

    @Override
    public Project getItem(int position) {
        return projectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.owner_home_list_row, parent, false);
        TextView textProjectName = (TextView) convertView.findViewById(R.id.projectName);
        textProjectName.setText(getItem(position).nama_project);
        TextView textProjectPlatform = (TextView) convertView.findViewById(R.id.projectPlatform);
        textProjectPlatform.setText(getItem(position).platform_project);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(context, SingleProjectOwnerActivity.class);
        i.putExtra("kode_project", getItem(position).kode_project);
        context.startActivity(i);
    }
}
