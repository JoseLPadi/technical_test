package com.josepadilla.technical_test.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.josepadilla.technical_test.R;
import com.josepadilla.technical_test.entities.Project;

import java.util.List;

/**
 * Created by jose on 03/24/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<Project> {
    private LayoutInflater inflater;
    private List<Project> projects;
    private int layoutID;

    public SpinnerAdapter(Context context, int viewResourceId, List<Project> listItems) {
        super(context, viewResourceId, listItems);
        this.inflater = LayoutInflater.from(context);
        this.layoutID = viewResourceId;
        this.projects = listItems;
    }


    @SuppressLint({ "ViewHolder", "ResourceAsColor" })
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View mySpinner = inflater.inflate(R.layout.cell_task_list, parent, false);
        ((TextView)mySpinner.findViewById(R.id.TextViewItemName)).setText(projects.get(position).getName());
        return mySpinner;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View mySpinner = inflater.inflate(R.layout.cell_task_list, parent, false);
        ((TextView)mySpinner.findViewById(R.id.TextViewItemName)).setText(projects.get(position).getName());
        return mySpinner;
    }

    public void setArrayList(List<Project> projects){
        this.projects = projects;
        notifyDataSetChanged();
    }


}
