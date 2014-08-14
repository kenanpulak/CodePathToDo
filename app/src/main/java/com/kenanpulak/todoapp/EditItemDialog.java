package com.kenanpulak.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditItemDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditItemDialog#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EditItemDialog extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText mEditText;

    public interface EditTextDialogListener {
        void onFinishEditDialog(String inputText, int pos);
    }

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemDialog newInstance(String title) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        mEditText = (EditText) view.findViewById(R.id.edit_item_text);
        mEditText.setOnEditorActionListener(this);
        String itemName = getArguments().getString("itemName", "");
        mEditText.setText(itemName);
        mEditText.setSelection(itemName.length());
        String title = getArguments().getString("title", "Edit Task");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditTextDialogListener listener = (EditTextDialogListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString(),getArguments().getInt("pos"));
            dismiss();
            return true;
        }
        return false;
    }

}
