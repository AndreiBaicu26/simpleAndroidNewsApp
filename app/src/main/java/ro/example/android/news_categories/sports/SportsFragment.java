package ro.example.android.news_categories.sports;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.example.android.databinding.SportsFragmentBinding;
import ro.example.android.news_categories.NewsRecyclerViewAdapter;

public class SportsFragment extends Fragment {

    private SportsViewModel mViewModel;
    private SportsFragmentBinding binding;
    private NewsRecyclerViewAdapter adapter;

    public static SportsFragment newInstance() {
        return new SportsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  SportsFragmentBinding.inflate(inflater, container, false);
        binding.sportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.sportsRecyclerView.setHasFixedSize(true);


        adapter = new NewsRecyclerViewAdapter(getContext());
        binding.sportsRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SportsViewModel.class);
        mViewModel.viewCreatedGetArticles();
        mViewModel.getArticles().observe(getViewLifecycleOwner(),articleEntities -> {
            adapter.setArticles(articleEntities);
        });

    }

}