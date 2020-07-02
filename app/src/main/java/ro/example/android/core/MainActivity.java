package ro.example.android.core;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ro.example.android.DepthPageTransformer;
import ro.example.android.NewsCategoriesAdapter;
import ro.example.android.R;
import ro.example.android.databinding.ActivityMainBinding;


public class MainActivity extends FragmentActivity {

    private NewsCategoriesAdapter newsCategoriesAdapter;
    private ViewPager2 viewPager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewPager = binding.viewPager2;


        TabLayout tabLayout = binding.tabs;

        setUpViewPager(viewPager);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager, (tab, position) -> {
                    switch (position){
                        case 0:{
                            tab.setText("Headlines");
                            break;
                        }
                        case 1:{
                            tab.setText("Sports");
                            break;
                        }
                        case 2:{
                            tab.setText("Business");
                            break;
                        }
                        case 3:{
                            tab.setText("Technology");
                            break;
                        }
                    }
                }
        );
         tabLayoutMediator.attach();

        setMarginsForTabs(tabLayout);


    }

    private void setMarginsForTabs(TabLayout tabLayout){
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 20, 50, 20);
            tab.requestLayout();
        }
    }

    private void setUpViewPager(ViewPager2 viewPager) {
        viewPager.setAdapter(new NewsCategoriesAdapter(this));
        viewPager.setPageTransformer(new DepthPageTransformer());
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

}