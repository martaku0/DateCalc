package com.example.dateconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button calcBtn;
    private Spinner months_array;
    private Spinner days_array;
    private EditText year;
    private TextView daysResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcBtn = findViewById(R.id.calcBtn);
        months_array = findViewById(R.id.monthsArray);
        days_array = findViewById(R.id.daysArray);
        year = findViewById(R.id.year);
        daysResult = findViewById(R.id.daysResult);

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
                LocalDate currDate = LocalDate.now();
                Log.d("res", currDate.toString());

                int currYear = currDate.getYear();
                int userYear = Integer.parseInt(year.getText().toString());
                int days = 0;
                if(currYear > userYear){
                    int temp = currYear - userYear;
                    days = 365*temp;
                }
                else{
                    int temp = userYear - currYear;
                    days = 365*temp;
                }

                daysResult.setText(String.valueOf(days));
            }
        });
    }
}