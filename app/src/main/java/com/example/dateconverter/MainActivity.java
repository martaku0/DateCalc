package com.example.dateconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button calcBtn;
    private Spinner months_array;
    private Spinner days_array;
    private EditText year;
    private TextView daysResult;
    private TextView resultsTxt;


    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        Log.d("test", String.valueOf(diffInMillies));
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }


    public void setDaysNumber(){

        Integer[] months31Arr = {1,3,5,7,8,10,12};
        List<Integer> months31 = Arrays.asList(months31Arr);

        long t = months_array.getSelectedItemId()+1;
        Integer t1 = Integer.parseInt(String.valueOf(t));
        int max = 31;

        if(!months31.contains(t1)){
            Log.d("test", "1");
            if(t1 != 2){
                max = 30;
            }
            else{
                if((Integer.parseInt(year.getText().toString())%4 == 0 && Integer.parseInt(year.getText().toString())%100 != 0) || (Integer.parseInt(year.getText().toString())%100 == 0 && Integer.parseInt(year.getText().toString())%400 == 0)){
                    max = 29;
                }
                else{
                    max = 28;
                }
            }
        }

        ArrayList<String> daysTemp = new ArrayList<String>();
        for (int d = 1; d <= max; d++) {
            daysTemp.add(Integer.toString(d));
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (MainActivity.this, android.R.layout.simple_spinner_item,
                        daysTemp);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        days_array.setAdapter(spinnerArrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcBtn = findViewById(R.id.calcBtn);
        months_array = findViewById(R.id.monthsArray);
        days_array = findViewById(R.id.daysArray);
        year = findViewById(R.id.year);
        daysResult = findViewById(R.id.daysResult);
        resultsTxt = findViewById(R.id.resultsTxt);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        months_array.setAdapter(adapter);

        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        days_array.setAdapter(adapterDays);



        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userYear = Integer.parseInt(year.getText().toString());
                int userMonth = Integer.parseInt(String.valueOf(months_array.getSelectedItemId()));
                int userDay = Integer.parseInt(String.valueOf(days_array.getSelectedItemId()+1));

                Date currDate = new Date();

                Calendar cal = Calendar.getInstance();
                cal.set(userYear, userMonth, userDay);
                Date userDate = cal.getTime();

                long difference = getDateDiff(userDate,currDate, TimeUnit.DAYS);
                Log.d("res", String.valueOf(userDate) + " | " + String.valueOf(currDate));


                daysResult.setText(String.valueOf(difference));
                resultsTxt.setVisibility(View.VISIBLE);
                daysResult.setVisibility(View.VISIBLE);
            }
        });

        year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!year.getText().toString().equals("")){
                    months_array.setVisibility(View.VISIBLE);
                    days_array.setVisibility(View.VISIBLE);
                    calcBtn.setVisibility(View.VISIBLE);
                    setDaysNumber();
                }
                else{
                    months_array.setVisibility(View.INVISIBLE);
                    days_array.setVisibility(View.INVISIBLE);
                    calcBtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        months_array.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setDaysNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}