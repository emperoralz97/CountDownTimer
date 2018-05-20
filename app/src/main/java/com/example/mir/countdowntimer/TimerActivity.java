package com.example.mir.countdowntimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TimerActivity extends AppCompatActivity {
    private TextView timer;
    private static boolean tap = false;
    private static long progress = 0;
    static CountDownTimer cntTimer =  null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        timer = (TextView) findViewById(R.id.timerText);
        final Button timerButton = (Button) findViewById(R.id.button);
        seekBar.setMax(3600);
        seekBar.setProgress(60);
        setTimer(seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTimer((long)progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tap == false){
                    seekBar.setEnabled(false);
                    cntTimer = new CountDownTimer(seekBar.getProgress()*1000, 1000){
                        public void onTick(long millisUntilFinished) {

                            progress = millisUntilFinished/1000;
                            setTimer(progress);
                        }

                        public void onFinish() {
                            timer.setText("00 : 00 : 00");
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.air_horn);
                            mediaPlayer.start();

                            timerButton.setText("START");
                            seekBar.setEnabled(true);

                        }
                    }.start();
                    tap = true;
                    timerButton.setText("STOP");
                }
                else{
                    cntTimer.cancel();
                    seekBar.setEnabled(true);
                    seekBar.setProgress((int)progress);
                    timerButton.setText("START");
                    tap = false;
                }

            }
        });
    }

    public void setTimer(long seconds){
        NumberFormat f = new DecimalFormat("00");
        long hr = seconds / 3600;
        seconds = seconds % 3600;
        long min = seconds / 60;
        seconds = seconds % 60;
        timer.setText(f.format(hr)+" : "+f.format(min)+" : "+f.format(seconds));
    }
}
