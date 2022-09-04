package liel.azulay.maestro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import liel.azulay.maestro.ui.main.SectionsPagerAdapter;
import liel.azulay.maestro.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{

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
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        ImageButton settings = findViewById(R.id.settings);

        settings.setOnClickListener(view -> {
            Intent setting_intent = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(setting_intent);
        });

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                       .setAction("Action", null).show());
    }
}