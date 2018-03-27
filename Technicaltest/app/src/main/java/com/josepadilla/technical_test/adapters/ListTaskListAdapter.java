package com.josepadilla.technical_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.josepadilla.technical_test.R;
import com.josepadilla.technical_test.entities.TaskGroup;

import java.util.List;

/**
 * Created by jose on 03/24/2018.
 */

public class ListTaskListAdapter extends BaseAdapter {
    List<TaskGroup> listTasksGroup;
    Context ctx;

    public ListTaskListAdapter(List<TaskGroup> listTasksGroup, Context ctx){
        this.listTasksGroup = listTasksGroup;
        this.ctx = ctx;
    }

    private static class ViewHolder {
        TextView taskName;
    }


    public void setListTaskGroup(List<TaskGroup> listTasksGroup){
        this.listTasksGroup = listTasksGroup;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listTasksGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return listTasksGroup.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(listTasksGroup.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView = inflater.inflate(R.layout.cell_task_list, parent, false);
            holder.taskName = (TextView) convertView.findViewById(R.id.TextViewItemName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.taskName.setText(listTasksGroup.get(position).getName());

        return convertView;
    }
}
