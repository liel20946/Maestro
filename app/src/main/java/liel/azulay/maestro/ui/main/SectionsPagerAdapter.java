package liel.azulay.maestro.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class SectionsPagerAdapter extends FragmentStateAdapter
{

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