package assignment.promobi.rajeev.com.promobiassignment.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Calendar;

import assignment.promobi.rajeev.com.promobiassignment.Filters.ArticleFilter;
import assignment.promobi.rajeev.com.promobiassignment.R;


/**
 * Created by rparuthi on 3/18/2017.
 */

public class SettingsFragment extends DialogFragment {

    TextView etDateText;
    Spinner sprSortOrder;

    CheckBox chkArts;
    CheckBox chkSports;
    CheckBox chkFashion;

    Button btnSave;

    ArrayAdapter<CharSequence> spinnerAdapter;
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

    }

    public SettingsFragment(){}


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        etDateText = (TextView) view.findViewById(R.id.etDate);


        chkArts = (CheckBox) view.findViewById(R.id.chkbox_arts);
        chkFashion = (CheckBox) view.findViewById(R.id.chkbox_fashion);
        chkSports = (CheckBox)view.findViewById(R.id.chkbox_sports);

        btnSave = (Button)view.findViewById(R.id.btnSave);
        //Set spinner Data
        loadSortOrder(view);
        return view;
    }


    private void loadSortOrder(View view){
        sprSortOrder = (Spinner) view.findViewById(R.id.sort_spinner);

        spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sortorder_array, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sprSortOrder.setAdapter(spinnerAdapter);
    }

    public void onCheckboxClicked(View view){
        boolean isChecked = ((CheckBox)view).isChecked();

        switch (view.getId()){
            case R.id.chkbox_arts:
                ArticleFilter.getArticleFilterInstance().setArts(isChecked);
                break;
            case R.id.chkbox_fashion:
                ArticleFilter.getArticleFilterInstance().setFashion(isChecked);
                break;
            case R.id.chkbox_sports:
                ArticleFilter.getArticleFilterInstance().setSports(isChecked);
                break;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        //Set listeners
        setListeners();

        //Set Data
        setData();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }


    private void setData(){

        ArticleFilter settings = ArticleFilter.getArticleFilterInstance();
        etDateText.setText(settings.getBeginDate());

        int sprPosition = spinnerAdapter.getPosition(settings.getSortOrder());
        sprSortOrder.setSelection(sprPosition);

        chkSports.setChecked(ArticleFilter.getArticleFilterInstance().isSports());

        chkFashion.setChecked(ArticleFilter.getArticleFilterInstance().isFashion());

        chkArts.setChecked(ArticleFilter.getArticleFilterInstance().isArts());

    }

    private void setListeners(){

        //OnClick listener to open DatePicker
        etDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        chkArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });

        chkFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });

        chkSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClick(v);
            }
        });

    }

    private void onSaveClick(View v){

        ArticleFilter.getArticleFilterInstance().setBeginDate((String.valueOf(etDateText.getText())));

        ArticleFilter.getArticleFilterInstance().setSortOrder(String.valueOf(sprSortOrder.getSelectedItem()));


        dismiss();
    }

    private void showDatePicker(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        final Calendar cal = Calendar.getInstance();

        Bundle args = new Bundle();

        args.putInt("year",cal.get(Calendar.YEAR));
        args.putInt("month",cal.get(Calendar.MONTH));
        args.putInt("day",cal.get(Calendar.DAY_OF_MONTH));

        datePickerFragment.setArguments(args);

        DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                etDateText.setText(String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth)
                        + "/" + String.valueOf(year));
            }
        };

        datePickerFragment.setCallBack(ondate);
        datePickerFragment.show(getFragmentManager(),"Date Picker");
    }

}
