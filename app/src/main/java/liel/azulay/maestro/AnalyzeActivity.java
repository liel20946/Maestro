package liel.azulay.maestro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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

        analyze();
    }

    public void setDarkTheme(ActionBar actionBar)
    {
        actionBar.setTitle(HtmlCompat.fromHtml("<font color='#121212'>Analyze</font>",
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
        File recording_file = new File( getExternalCacheDir().getAbsolutePath() + "/audiorecordtest.3gp");
        byte[] b = new byte[1024];
        FileInputStream fis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            fis = new FileInputStream(recording_file);
        } catch (FileNotFoundException ignored){

        }
        if (fis != null)
        {
            try
            {
                for (int readNum; (readNum = fis.read(b)) != -1;) {
                    bos.write(b, 0, readNum);
                }
            }catch (IOException ignored)
            {
            }

        }
        ArrayList<Float> hertz_arr = new ArrayList<>();
        byte[] bytes = bos.toByteArray();
        int last_val = bytes[0];
        int last_pos = 0;
        for (int i=0;i< bytes.length;i++)
        {
            if ((bytes[i]>0 && last_val <=0) || (bytes[i] < 0 && last_val >=0))
            {
                int elapsed_steps = i - last_pos;
                last_pos = i;
                float hertz = 1/((float)elapsed_steps/44100);
                hertz_arr.add(hertz);
            }
            last_val = bytes[i];
        }

    }
}