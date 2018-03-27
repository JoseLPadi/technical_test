package com.josepadilla.technical_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.josepadilla.technical_test.FragmentAddMultipleTaskView;
import com.josepadilla.technical_test.R;

import java.util.List;

/**
 * Created by jose on 03/25/2018.
 */

public class ListNewTaskListAdapter extends BaseAdapter {
    FragmentAddMultipleTaskView view;
    List<String> listNewTask;
    Context ctx;


    public ListNewTaskListAdapter(List<String> listNewTask, Context ctx, FragmentAddMultipleTaskView view){
        this.listNewTask = listNewTask;
        this.view = view;
        this.ctx = ctx;
    }

    private static class ViewHolder {
        TextView taskName;
        Button deleteTask;
    }


    public void setListNewTask(List<String> listNewTask){
        this.listNewTask = listNewTask;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listNewTask.size();
    }

    @Override
    public Object getItem(int position) {
        return listNewTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){

            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView = inflater.inflate(R.layout.cell_new_task, parent, false);
            holder.taskName = (TextView) convertView.findViewById(R.id.TextViewTaskName);
            holder.deleteTask = (Button) convertView.findViewById(R.id.ButtonRemoveTask);
            holder.deleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    view.removeItem(position);
                }
            });
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.taskName.setText(listNewTask.get(position));



        return convertView;
    }
}
