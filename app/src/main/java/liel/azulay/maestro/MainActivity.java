package liel.azulay.maestro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import liel.azulay.maestro.databinding.ActivityMainBinding;
import liel.azulay.maestro.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private String filename =  null;

    public static void setLightStatusBar(View view, Activity activity)
    {
        int flags = view.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        view.setSystemUiVisibility(flags);
        activity.getWindow().setStatusBarColor(activity.getColor(R.color.white));
        TextView title = view.findViewById(R.id.title);
        title.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.teal_200));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        liel.azulay.maestro.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLightStatusBar(this.findViewById(R.id.main_view), this);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
        {
            if (position == 0)
            {
                tab.setText(R.string.home);
            }
            else
                {
                    tab.setText(R.string.logs);
                }
        }).attach();
        filename = getExternalCacheDir().getAbsolutePath() +  "/audiorecordtest.3gp";
        FloatingActionButton fab = binding.fab;
        FloatingActionButton play = binding.play;
        ImageButton settings = findViewById(R.id.settings);
        MediaRecorder recorder = new MediaRecorder();
        MediaPlayer player = new MediaPlayer();
        settings.setOnClickListener(view -> {
            Intent setting_intent = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(setting_intent);
        });
        AtomicBoolean is_recording = new AtomicBoolean(false);
        AtomicBoolean is_playing = new AtomicBoolean(false);

        fab.setOnClickListener(view -> {
            if (!is_recording.get())
            {
                startRecording(recorder);
                is_recording.set(true);
            }
            else
            {
                stopRecording(recorder);
                is_recording.set(false);
            }
        });

        play.setOnClickListener(view -> {
            if (!is_playing.get())
            {
                startPlaying(player);
                is_recording.set(true);
            }
            else
            {
                stopPlaying(player);
                is_playing.set(false);
            }
        });

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION)
        {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    private void startRecording(MediaRecorder recorder) {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filename);
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

    private void startPlaying(MediaPlayer player) {
        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
        } catch (IOException ignored) { //TODO:change !
        }
    }

    private void stopPlaying(MediaPlayer player) {
        player.release();
    }


}