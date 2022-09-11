package liel.azulay.maestro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class AnalyzeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            int currentNightMode = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode)
            {
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

    public void setDarkTheme(ActionBar actionBar)
    {
        actionBar.setTitle(HtmlCompat.fromHtml("<font color='#121212'>Settings</font>",
                                               HtmlCompat.FROM_HTML_MODE_LEGACY));
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_dark_mode);
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(),
                                                                                 R.color.teal_200)));

    }

    public void setLightTheme(ActionBar actionBar)
    {
        actionBar.setTitle(HtmlCompat.fromHtml("<font color='#ffffff'>Analyze</font>",
                                               HtmlCompat.FROM_HTML_MODE_LEGACY));
        actionBar.setHomeAsUpIndicator(R.drawable.back_btn);
    }

    private void analyze()
    {

    }
}