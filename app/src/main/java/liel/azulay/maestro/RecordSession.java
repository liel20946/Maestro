package liel.azulay.maestro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.os.Bundle;

import android.os.Handler;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

public class RecordSession extends AppCompatActivity
{
    private String file_name =  null;
    private int seconds = 0;
    private int current_amp = 0;
    private boolean running;
    private  MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_session);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            int currentNightMode = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    // Night mode is not active, we're using the light theme
                    setLightTheme(actionBar);
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    // Night mode is active, we're using dark theme
                    setDarkTheme(actionBar);
                    break;
            }
        }
        file_name = getExternalCacheDir().getAbsolutePath() +  "/audiorecordtest.3gp";
        recorder = new MediaRecorder();
        Button record_btn = findViewById(R.id.record_btn);
        record_btn.setOnClickListener(view -> {
            if (record_btn.getText() == getText(R.string.record_session))
            {
                record_btn.setText(R.string.stop_recording);
                running = true;
                runTimer();
                startRecording(recorder);
            }
            else
            {
                running = false;
                stopRecording(recorder);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

    public void setDarkTheme(ActionBar actionBar)
    {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(HtmlCompat.fromHtml("<font color='#121212'>Settings</font>",
                                               HtmlCompat.FROM_HTML_MODE_LEGACY));
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_dark_mode);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(),
                                                                                 R.color.teal_200)));

    }

    public void setLightTheme(ActionBar actionBar)
    {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(HtmlCompat.fromHtml("<font color='#ffffff'>Settings</font>",
                                               HtmlCompat.FROM_HTML_MODE_LEGACY));
        actionBar.setHomeAsUpIndicator(R.drawable.back_btn);
    }

    private void runTimer()
    {
        final TextView timeView = findViewById(R.id.stop_watch);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run()
            {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                if (running) {
                    seconds++;
                    String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                    // Set the text view text.
                    timeView.setText(time);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void startRecording(MediaRecorder recorder) {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(file_name);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            return;
        }
        recorder.start();
    }

    private void stopRecording(MediaRecorder recorder) {
        recorder.stop();
        recorder.release();
    }

    private void start_drawing()
    {
        SurfaceView surface = findViewById(R.id.visualizerLineBar);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1f);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run()
            {
                current_amp = recorder.getMaxAmplitude();
                canvas.drawLine(0,0,20,current_amp,paint);

                handler.postDelayed(this, 500);
            }
        });
    }
}