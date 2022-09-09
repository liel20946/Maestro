package liel.azulay.maestro.ui.record;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import liel.azulay.maestro.R;

public class WaveView extends View
{
    private final Paint paint;
    private final ArrayList<Float> amp_list = new ArrayList<>();
    private final ArrayList<RectF> spikes = new ArrayList<>();

    private final float radius = 6f;
    private final float width = 9f;
    private final float screen_width;
    private final int max_spikes;
    private final float distance = 6f;

    public WaveView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.sec_color));
        screen_width = getResources().getDisplayMetrics().widthPixels;
        max_spikes = (int) (screen_width / (width + distance));
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        spikes.forEach(react -> canvas.drawRoundRect(react,radius,radius,paint));
    }

    public void add_amp(float amp)
    {
        float screen_height = 300f;
        float norm = Math.min(amp / 10, screen_height);
        amp_list.add(norm);
        spikes.clear();
        ArrayList<Float> current_amps = get_last(amp_list,max_spikes);
        for (int i=0;i<current_amps.size();i++)
        {
            float left = screen_width - i*(width+distance);
            float top = screen_height / 2 - current_amps.get(i) / 2;
            float right = left + width;
            float bottom = top + current_amps.get(i);
            spikes.add(new RectF(left,top,right,bottom));
        }

        invalidate();
    }

    private ArrayList<Float> get_last(ArrayList<Float> list ,int n)
    {
        int new_start = list.size() - n;
        if (new_start < 0)
        {
            return list;
        }

        ArrayList<Float> new_list = new ArrayList<>();
        for (;new_start<list.size();new_start++)
        {
            new_list.add(list.get(new_start));
        }
        return new_list;
    }
}
