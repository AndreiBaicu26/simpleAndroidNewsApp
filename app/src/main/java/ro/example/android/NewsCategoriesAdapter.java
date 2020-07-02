package ro.example.android;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ro.example.android.news_categories.business.BusinessFragment;
import ro.example.android.news_categories.headlines.HeadlinesFragment;
import ro.example.android.news_categories.sports.SportsFragment;
import ro.example.android.news_categories.technology.TechnologyFragment;


public class NewsCategoriesAdapter extends FragmentStateAdapter {



    public NewsCategoriesAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new HeadlinesFragment();
            case 1:
                return new SportsFragment();
            case 2:
                return new BusinessFragment();
            case 3:
                return new TechnologyFragment();
            case 4:
                return new SportsFragment();
            default:
                return new BusinessFragment();

        }

    }
}
