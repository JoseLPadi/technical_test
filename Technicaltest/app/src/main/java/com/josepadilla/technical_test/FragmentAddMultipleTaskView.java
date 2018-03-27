package com.josepadilla.technical_test;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.josepadilla.technical_test.adapters.ListNewTaskListAdapter;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus5.factory.RequiresPresenter;
import nucleus5.view.NucleusFragment;

/**
 * Created by jose on 03/24/2018.
 */
@RequiresPresenter(FragmentAddMultipleTaskPresenter.class)
public class FragmentAddMultipleTaskView extends NucleusFragment<FragmentAddMultipleTaskPresenter> {

    @BindView(R.id.ListViewNewTasks) ListView listViewNewTasks;
    @BindView(R.id.ButtonSendAllTasks) Button buttonSendAllTasks;
    @BindView(R.id.EditTextNewTask) EditText editTextNewTaskAdded;
    @BindView(R.id.ButtonAddTask) Button buttonAddNewTask;
    @BindView(R.id.TextViewTitle) TextView textViewTitle;

    public final static String ARG_TASK_ID =  "taskID";
    public final static String ARG_TASK_NAME = "taskName";


    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_add_multiple_tasks, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        String taskName;
        String taskID;
        if (getActivity().getIntent().hasExtra(FragmentAddMultipleTaskView.ARG_TASK_NAME)) {
            taskName = getActivity().getIntent().getExtras().getString(FragmentAddMultipleTaskView.ARG_TASK_NAME, "");
            taskID = getActivity().getIntent().getExtras().getString(FragmentAddMultipleTaskView.ARG_TASK_ID,"");
        } else {
            taskName = b.getString(FragmentAddMultipleTaskView.ARG_TASK_NAME);
            taskID = b.getString(FragmentAddMultipleTaskView.ARG_TASK_ID);
        }
        getPresenter().setTaskNameAndTaskId(taskID, taskName);
        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String taskName;
        Bundle b = getArguments();

        if (getActivity().getIntent().hasExtra(FragmentAddMultipleTaskView.ARG_TASK_NAME)) {
            taskName = getActivity().getIntent().getExtras().getString(FragmentAddMultipleTaskView.ARG_TASK_NAME, "");
        } else {
            taskName = b.getString(FragmentAddMultipleTaskView.ARG_TASK_NAME);
        }
        textViewTitle.setText("Adding new tasks for " + taskName);
        buttonAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListTask(getPresenter().addNewTask(editTextNewTaskAdded.getText().toString()));
                editTextNewTaskAdded.setText("");
            }
        });

        buttonSendAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
                getPresenter().addAllTasks();
            }
        });

        editTextNewTaskAdded.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextNewTaskAdded.getText().toString().isEmpty()){
                    buttonAddNewTask.setEnabled(false);
                } else {
                    buttonAddNewTask.setEnabled(true);
                }
            }
        });

        List<String>  li= getPresenter().addNewTask("");
        if (li !=null){
            updateListTask(li);
        }
    }

    public void updateListTask(List<String> newList){
        if (listViewNewTasks.getAdapter() == null){
            ListNewTaskListAdapter newA = new ListNewTaskListAdapter(newList,getActivity(), this);
            this.listViewNewTasks.setAdapter(newA);
        } else {
            ((ListNewTaskListAdapter)listViewNewTasks.getAdapter()).setListNewTask(newList);
        }
        buttonSendAllTasks.setEnabled(!newList.isEmpty());

    }



    public void allTaskSendedFail(){
        Toast.makeText(getActivity(), "Cant send task. Please try again.", Toast.LENGTH_SHORT).show();
    }

    public void removeItem(int itemPosition){
        updateListTask(getPresenter().removeItem(itemPosition));
    }

    public void allTaskSendedDone(){
        Toast.makeText(getActivity(), "all tasks all sended.", Toast.LENGTH_SHORT).show();
    }

    AlertDialog dialog;
    public void showAlert(){
        if (dialog == null) {
            dialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setMessage("sending New Tasks")
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
