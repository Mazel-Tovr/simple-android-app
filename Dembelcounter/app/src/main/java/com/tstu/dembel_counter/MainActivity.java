package com.tstu.dembel_counter;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private EditText inputDateBox, daysPastBox, hoursPastBox, secondPastBox, daysLeftBox, hoursLeftBox, secondLeftBox, endDutyDate;
    private ConstraintLayout firstLayout, secondLayout, dutyLayout;
    private volatile boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstLayout = findViewById(R.id.firstLayout);
        secondLayout = findViewById(R.id.secondLayout);
        dutyLayout = findViewById(R.id.constraintLayout2);
        inputDateBox = findViewById(R.id.inputDate);
        daysPastBox = findViewById(R.id.editTextDaysPast);
        hoursPastBox = findViewById(R.id.editTextHoursPast);
        secondPastBox = findViewById(R.id.editTextSecondPast);
        daysLeftBox = findViewById(R.id.editTextDaysLeft);
        hoursLeftBox = findViewById(R.id.editTextHoursLeft);
        secondLeftBox = findViewById(R.id.editTextSecondsLeft);
        endDutyDate = findViewById(R.id.editEndDutyDate);
        inputDateBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        inputDateBox.addTextChangedListener(new TextWatcher() {
            int beforeTextChangedLength;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeTextChangedLength = charSequence.length();
                flag = false;
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
        try {
            System.gc();
            Utils.isValidDate(inputDateBox.getText().toString());
            setVisible(View.VISIBLE);
            Date startDate = format.parse(inputDateBox.getText().toString());
            Date endDate = Utils.getDatePlusYear(startDate);
            endDutyDate.setText(format.format(endDate));
            flag = true;
            new Thread(() -> {
                while (flag) {
                    try {
                        Thread.sleep(1000);
                        long milliseconds = (new Date().getTime() - startDate.getTime());
                        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
                        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) - TimeUnit.DAYS.toHours(days);
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.HOURS.toSeconds(hours);
                        daysPastBox.setText(String.valueOf(days));
                        hoursPastBox.setText(String.valueOf(hours));
                        secondPastBox.setText(String.valueOf(seconds));

                    } catch (InterruptedException ignored) {

                    }

                }
            }).start();

            new Thread(() -> {
                while (flag) {
                    try {
                        Thread.sleep(1000);
                        long milliseconds = (endDate.getTime() - new Date().getTime());
                        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
                        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) - TimeUnit.DAYS.toHours(days);
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.HOURS.toSeconds(hours);
                        daysLeftBox.setText(String.valueOf(days));
                        hoursLeftBox.setText(String.valueOf(hours));
                        secondLeftBox.setText(String.valueOf(seconds));

                    } catch (InterruptedException ignored) {

                    }

                }
            }).start();


        } catch (Exception ignore) {

        }
    }

    private void setVisible(int visible) {
        dutyLayout.setVisibility(visible);
        firstLayout.setVisibility(visible);
        secondLayout.setVisibility(visible);
    }

}
