package rs.raf.dnevnjak.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.dnevnjak.fragment.AccountFragment;
import rs.raf.dnevnjak.fragment.CalendarFragment;
import rs.raf.dnevnjak.fragment.ScheduleFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;

    public CustomPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public CustomPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_1: fragment = new CalendarFragment(); break;
            case FRAGMENT_2: fragment = new ScheduleFragment(); break;
            default: fragment = new AccountFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case FRAGMENT_1: return "1";
            case FRAGMENT_2: return "2";
            default: return "3";
        }
    }
}
