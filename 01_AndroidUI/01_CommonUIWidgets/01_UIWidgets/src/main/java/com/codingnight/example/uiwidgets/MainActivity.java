package com.codingnight.example.uiwidgets;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    TextView display;
    Button btnLeft, btnRight, btnConfirm;
    Switch aSwitch;
    ProgressBar progressBar;
    EditText editText;
    RadioGroup radioGroup;
    ImageView imageView;
    SeekBar seekBar;
    CheckBox checkBoxYuwen, checkBoxShuxue, checkBoxYingyu;
    RatingBar ratingBar;
    ToggleButton toggleButton;

    String yuwen = "";
    String shuxue = "";
    String yingyu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
        btnLeft = findViewById(R.id.button1);
        btnRight = findViewById(R.id.button2);
        btnConfirm = findViewById(R.id.button3);
        aSwitch = findViewById(R.id.switch2);
        progressBar = findViewById(R.id.progressBar5);
        editText = findViewById(R.id.editTextNumber2);
        radioGroup = findViewById(R.id.radioGroup);
        imageView = findViewById(R.id.imageView1);
        seekBar = findViewById(R.id.seekBar3);
        checkBoxYuwen = findViewById(R.id.checkBox2);
        checkBoxShuxue = findViewById(R.id.checkBox3);
        checkBoxYingyu = findViewById(R.id.checkBox4);
        ratingBar = findViewById(R.id.ratingBar2);
        toggleButton = findViewById(R.id.toggleButton);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(R.string.button1);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                display.setText(R.string.button2);
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    display.setText(R.string.on);
                } else {
                    display.setText(R.string.off);
                }
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    display.setText(R.string.on);
                } else {
                    display.setText(R.string.off);
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    s = "0";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(Integer.valueOf(s), true);
                } else {
                    progressBar.setProgress(Integer.valueOf(s));
                }
                display.setText(s);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton4) {
                    imageView.setImageResource(R.drawable.telegramlogo);
                } else {
                    imageView.setImageResource(R.drawable.applelogo);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                display.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBoxYuwen.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    yuwen = getResources().getString(R.string.checkbox1);
                } else {
                    yuwen = "";
                }
                display.setText(yuwen + shuxue + yingyu);
            }
        });
        checkBoxShuxue.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    shuxue = getResources().getString(R.string.checkbox2);
                } else {
                    shuxue = "";
                }
                display.setText(yuwen + shuxue + yingyu);
            }
        });
        checkBoxYingyu.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    yingyu = getResources().getString(R.string.checkbox3);
                } else {
                    yingyu = "";
                }
                display.setText(yuwen + shuxue + yingyu);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getApplicationContext(), String.valueOf(v), Toast.LENGTH_SHORT).show();
            }
        });
    }
}