package ro.example.android.news_categories.technology;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.example.android.databinding.TechnologyFragmentBinding;
import ro.example.android.news_categories.NewsRecyclerViewAdapter;
import ro.example.android.news_categories.sports.SportsFragment;

public class TechnologyFragment extends Fragment {

    private TechnologyViewModel mViewModel;
    private TechnologyFragmentBinding binding;
    private NewsRecyclerViewAdapter adapter;

    public static SportsFragment newInstance() {
        return new SportsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  TechnologyFragmentBinding.inflate(inflater, container, false);
        binding.technologyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.technologyRecyclerView.setHasFixedSize(true);


        adapter = new NewsRecyclerViewAdapter(getContext());
        binding.technologyRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(TechnologyViewModel.class);
        mViewModel.viewCreatedGetArticles();
        mViewModel.getArticles().observe(getViewLifecycleOwner(),articleEntities -> {
            adapter.setArticles(articleEntities);
        });

    }

}