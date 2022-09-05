package liel.azulay.maestro.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import liel.azulay.maestro.R;


public class SectionsPagerAdapter extends FragmentStateAdapter
{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};

    public SectionsPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        if (position == 0)
        {
            return new MainFragment();
        }
        else
        {
            return new LogsFragment();
        }

    }

    @Override
    public int getItemCount()
    {
        return 2;
    }
}