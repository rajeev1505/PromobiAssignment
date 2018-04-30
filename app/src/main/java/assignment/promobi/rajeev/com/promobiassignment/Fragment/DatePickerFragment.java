package assignment.promobi.rajeev.com.promobiassignment.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by rparuthi on 3/18/2017.
 */

public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener  ondateSet;
    private int year, month, day;
    DatePickerDialog datePickerDialog;

    public DatePickerFragment() {}

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }

}
