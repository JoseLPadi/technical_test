package com.josepadilla.technical_test;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.josepadilla.technical_test.adapters.ListTaskListAdapter;
import com.josepadilla.technical_test.adapters.SpinnerAdapter;
import com.josepadilla.technical_test.entities.Project;
import com.josepadilla.technical_test.entities.TaskGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus5.factory.RequiresPresenter;
import nucleus5.view.NucleusFragment;

/**
 * Created by jose on 03/24/2018.
 */
@RequiresPresenter(FragmentTaskListPresenter.class)
public class FragmentTaskListView extends NucleusFragment<FragmentTaskListPresenter> {

    @BindView(R.id.SpinnerSelectProject)  Spinner spinnerselectProyect;
    @BindView(R.id.ListViewGroupTaskList)  ListView listViewgroupTaskList;
    @BindView(R.id.ButtonAddNewTaskList) Button buttonaddNewTaskList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_task_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonaddNewTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        showAlert("Loading Projects.");
        getPresenter().startEvent(getPresenter().LIST_PROJECT_ID);
    }


    public void setSpinnerList(List<Project> projects){
        if (spinnerselectProyect.getAdapter() == null){
            SpinnerAdapter spa =  new SpinnerAdapter(getActivity(), R.layout.cell_task_list, projects);
            spinnerselectProyect.setAdapter(spa);
            this.spinnerselectProyect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listViewgroupTaskList.setVisibility(View.VISIBLE);
                    showAlert("Loading list tasks.");
                    getPresenter().selectProject(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    listViewgroupTaskList.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            ((SpinnerAdapter)spinnerselectProyect.getAdapter()).setArrayList(projects);
        }
    }

    public void setListViewTasksList(List<TaskGroup> tasksList){
        if (listViewgroupTaskList.getAdapter() == null){
            ListTaskListAdapter tkla  = new ListTaskListAdapter(tasksList, getActivity());
            this.listViewgroupTaskList.setAdapter(tkla);
            this.listViewgroupTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle b = getPresenter().selectTaskGroup(position);
                    openTaskSelected(b.getString("id"), b.getString("name"));

                }
            });

        } else {
            ((ListTaskListAdapter)this.listViewgroupTaskList.getAdapter()).setListTaskGroup(tasksList);
        }
        listViewgroupTaskList.setVisibility(View.VISIBLE);

    }

    public void openTaskSelected(String taskID, String itemName){
        ((MainActivity) getActivity()).showAddNewTaskFragment(taskID, itemName);
    }



    AlertDialog dialog;
    public void showAlert(String message){
        if (dialog == null) {
            dialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setMessage(message)
                    .create();
        }
        dialog.show();
    }
    public void dismissAlert(){
        if (dialog != null){
            dialog.dismiss();
        }
    }


}
