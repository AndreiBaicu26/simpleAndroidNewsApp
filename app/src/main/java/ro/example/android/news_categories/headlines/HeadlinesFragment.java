package ro.example.android.news_categories.headlines;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.example.android.BR;
import ro.example.android.databinding.FragmentHeadlinesBinding;
import ro.example.android.news_categories.NewsRecyclerViewAdapter;
import ro.example.android.news_categories.business.BusinessViewModel;


public class HeadlinesFragment extends Fragment {

    private FragmentHeadlinesBinding binding;

    private HeadlinesFragmentViewModel viewModel;

    private NewsRecyclerViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the view, setup the binding and set it on the activity

        binding = FragmentHeadlinesBinding.inflate(inflater, container, false);
        binding.headlinesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.headlinesRecyclerView.setHasFixedSize(true);
        adapter = new NewsRecyclerViewAdapter(getContext());

        // Initialise the View Model
        viewModel = new ViewModelProvider(this).get(HeadlinesFragmentViewModel.class);
        viewModel.getArticles().observe(getViewLifecycleOwner(), articleEntities -> adapter.setArticles(articleEntities));
        // Bind the View Model to the View
        binding.setVariable(BR.viewModel,viewModel);

        binding.setLifecycleOwner(this); // Only needed if using LiveData, to allow fo observing
        binding.headlinesRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HeadlinesFragmentViewModel.class);
        viewModel.viewCreatedGetArticles();
        viewModel.getArticles().observe(getViewLifecycleOwner(),articleEntities -> {
            adapter.setArticles(articleEntities);
        });

    }
}