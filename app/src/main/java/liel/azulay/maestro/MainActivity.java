package liel.azulay.maestro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        liel.azulay.maestro.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton settings = findViewById(R.id.settings);
        getWindow().setStatusBarColor(getColor(R.color.teal_200));
        TextView title = findViewById(R.id.title);

        int currentNightMode = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                setLightTheme(settings, title);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                setDarkTheme(this, settings, title);
                break;
        }

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.sec_color));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.sec_color));
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

        FloatingActionButton start_session_btn = binding.sessionActivityBtn;
        MediaPlayer player = new MediaPlayer();
        settings.setOnClickListener(view -> {
            Intent setting_intent = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(setting_intent);
        });
        AtomicBoolean is_playing = new AtomicBoolean(false);

        start_session_btn.setOnClickListener(view -> {
            Intent record_session = new Intent(getBaseContext(), RecordSession.class);
            startActivity(record_session);
        });

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    public void setDarkTheme(Activity activity, ImageButton settings, TextView title)
    {
        settings.setImageDrawable(AppCompatResources.getDrawable(this,R.drawable.ic_settings));
        activity.findViewById(R.id.view_pager).setBackground(AppCompatResources.getDrawable(this, R.color.dark_mode));
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_mode));
    }

    public void setLightTheme(ImageButton settings, TextView title)
    {
        settings.setImageDrawable(AppCompatResources.getDrawable(this,R.drawable.ic_settings_dark_mode));
        title.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
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

//    private void startPlaying(MediaPlayer player) {
//        try {
//            player.setDataSource(filename);
//            player.prepare();
//            player.start();
//        } catch (IOException ignored) { //TODO:change !
//        }
//    }
//
//    private void stopPlaying(MediaPlayer player) {
//        player.release();
//    }


}