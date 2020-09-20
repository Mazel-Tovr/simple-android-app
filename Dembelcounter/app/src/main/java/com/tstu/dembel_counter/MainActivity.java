package com.tstu.dembel_counter;

import android.text.*;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private EditText inputDateBox, daysPastBox, hoursPastBox, secondPastBox, daysLeftBox, hoursLeftBox, secondLeftBox;
    private ConstraintLayout firstLayout,secondLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstLayout = findViewById(R.id.firstLayout);
        secondLayout = findViewById(R.id.secondLayout);
        inputDateBox = findViewById(R.id.inputDate);
        daysPastBox = findViewById(R.id.editTextDaysPast);
        hoursPastBox = findViewById(R.id.editTextHoursPast);
        secondPastBox = findViewById(R.id.editTextSecondPast);
        daysLeftBox = findViewById(R.id.editTextDaysLeft);
        hoursLeftBox = findViewById(R.id.editTextHoursLeft);
        secondLeftBox = findViewById(R.id.editTextSecondsLeft);
        inputDateBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        inputDateBox.addTextChangedListener(new TextWatcher() {
            int beforeTextChangedLength;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeTextChangedLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                // text is being removed
                if (beforeTextChangedLength > length) return;
                try {
                    Utils.isValidDate(editable.toString());
                } catch (Exception e) {
                    inputDateBox.setError(e.getMessage());
                }

                String str = editable.toString();
                String[] strArr = str.split(Utils.DASH_STRING);
                // only add dash after input year with zero dash and input month with one dash
                if ((length == 2 && strArr.length == 1) || (length == 5 && strArr.length == 2) || (length == 9 && strArr.length == 2)) {
                    inputDateBox.setText(str + Utils.DASH_STRING);
                    inputDateBox.setSelection(inputDateBox.length());
                }

            }
        });

    }

    public void findOutDembelDate(View view) {
        firstLayout.setVisibility(View.VISIBLE);
        secondLayout.setVisibility(View.VISIBLE);
    }
}
