package ro.example.android.news_categories.business;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.example.android.databinding.BusinessFragmentBinding;
import ro.example.android.news_categories.NewsRecyclerViewAdapter;

public class BusinessFragment extends Fragment {

    private BusinessViewModel mViewModel;

    private BusinessFragmentBinding binding;
    private NewsRecyclerViewAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  BusinessFragmentBinding.inflate(inflater, container, false);
        binding.businessRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.businessRecyclerView.setHasFixedSize(true);


        adapter = new NewsRecyclerViewAdapter(getContext());
        binding.businessRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(BusinessViewModel.class);
        mViewModel.viewCreatedGetArticles();
        mViewModel.getArticles().observe(getViewLifecycleOwner(),articleEntities -> {
            adapter.setArticles(articleEntities);
        });

    }

}