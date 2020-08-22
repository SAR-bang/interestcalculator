package com.example.interest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.content.res.Configuration;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    public TextView interest, amount;
    private EditText principle, rate, time;
    private Button calculate,reset;
    private String principleamt,rates;
    private String times;
    private RadioButton year,month,day;
    private RadioGroup radioGroup;
    private Switch aSwitch;
    private ProgressBar bar;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        aSwitch = findViewById(R.id.switchlang);
        interest = findViewById(R.id.textView1);
        amount = findViewById(R.id.textView2);
        bar = findViewById(R.id.progress);
        layout = findViewById(R.id.layoutt);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Locale locale = new Locale("ne-rNP");
                    Configuration config = getBaseContext().getResources().getConfiguration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                }else{
                    Locale locale = new Locale("ne-rNP");
                    Configuration config = getBaseContext().getResources().getConfiguration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                }
            }
        });

        principle = findViewById(R.id.editText1);
        rate = findViewById(R.id.editText2);
        time = findViewById(R.id.editText3);

        calculate = findViewById(R.id.calculateBtn);
        reset = findViewById(R.id.resetBtn);

        radioGroup = findViewById(R.id.radioGroup);

        year = findViewById(R.id.radioButton1);
        month = findViewById(R.id.radioButton2);
        day = findViewById(R.id.radioButton3);

        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                principleamt= principle.getText().toString().trim();
                rates = rate.getText().toString().trim();
                times = time.getText().toString().trim();


                if (verifyInput(principleamt,rates,times)=="1") {
                    float timesf = Float.parseFloat(times);
                    int btn=0;

                    if (month.isChecked()){
                        btn=1;
                    }else if (day.isChecked()){
                        btn=2;
                    }

                    if (btn == 2){
                        timesf = (timesf/360);
                    }else if (btn==1){
                        timesf = (timesf/12);
                    }

                    bar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bar.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            reset.setVisibility(View.VISIBLE);
                        }
                    },3000);

                    calculateSI(principleamt, rates, timesf);
                }else{
                    System.out.println("error");
                    return;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                principle.setText("");
                rate.setText("");
                time.setText("");
                year.setChecked(true);
                layout.setVisibility(View.GONE);
                reset.setVisibility(View.GONE);
                interest.setText("0.0000000");
                amount.setText("0.000000");
                aSwitch.setChecked(false);
            }
        });
    }

    private void calculateSI(String principleamt, String rates, float tim) {
        float prin = Float.parseFloat(principleamt);
        float rat = Float.parseFloat(rates);
        float SI = (((prin * rat * tim)/100));
        System.out.println(SI);
        interest.setText(String.valueOf(SI));
        amount.setText(String.valueOf(SI+prin));
        return;

    }

    private String verifyInput(String principleamt, String rates, String times) {
        if (principleamt.isEmpty()){
            principle.setError("principle required");
            return "";
        }
        else if (rates.isEmpty()){
            principle.setError("principle required");
            return "required rate";
        }else if (times.isEmpty()){
            time.setError("principle required");
            return "required time";
        }else{
            return "1";
        }
    }
}
