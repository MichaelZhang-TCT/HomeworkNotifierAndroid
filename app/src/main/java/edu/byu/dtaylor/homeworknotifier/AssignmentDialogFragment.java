package edu.byu.dtaylor.homeworknotifier;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AssignmentDialogFragment extends DialogFragment {

    private NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String name = getArguments().getString("name");
        String desc = getArguments().getString("description");
        String dueDate = getArguments().getString("dueDate");
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_assignment_dialog, null);
        TextView dialogTitle = (TextView)view.findViewById(R.id.dialog_title);
        TextView dialogDescription = (TextView)view.findViewById(R.id.dialog_description);
        if(name != null && desc != null && dueDate != null) {
            dialogDescription.setText(desc);
            dialogTitle.setText(name);
        }
        else
        {
            dialogTitle.setText("ERROR");
            dialogDescription.setText("Invalid arguments");
        }
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(mListener != null)
                            mListener.onDialogPositiveClick(AssignmentDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mListener != null)
                            mListener.onDialogNegativeClick(AssignmentDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    //Listener for callback on clicking to respond to activity on dialog complete
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if( activity instanceof NoticeDialogListener)
            mListener = (NoticeDialogListener) activity;
    }
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
}
