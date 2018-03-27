package com.josepadilla.technical_test;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import nucleus5.view.NucleusAppCompatActivity;

public class MainActivity extends NucleusAppCompatActivity {

    private boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = findViewById(R.id.FrameLayoutAddMultipleTask) != null;
    }

    public void showAddNewTaskFragment(String taskID, String taskName){
        if (isTablet){
            FragmentAddMultipleTaskView newFragment = new FragmentAddMultipleTaskView();
            Bundle args = new Bundle();
            args.putString(FragmentAddMultipleTaskView.ARG_TASK_ID, taskID);
            args.putString(FragmentAddMultipleTaskView.ARG_TASK_NAME, taskName);
            newFragment.setArguments(args);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.FrameLayoutAddMultipleTask, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else {
            startActivity(new Intent(this, AddNewTasksActivity.class).putExtra(FragmentAddMultipleTaskView.ARG_TASK_NAME, taskName)
                    .putExtra(FragmentAddMultipleTaskView.ARG_TASK_ID, taskID));

        }
    }


}
