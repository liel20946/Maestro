package liel.azulay.maestro;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
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

    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
        {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
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
}