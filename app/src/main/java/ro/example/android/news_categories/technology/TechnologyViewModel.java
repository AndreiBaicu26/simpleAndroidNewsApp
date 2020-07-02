package ro.example.android.news_categories.technology;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ro.example.android.App;
import ro.example.android.data.NewsRepository;
import ro.example.android.data.local.ArticleEntity;
import timber.log.Timber;

public class TechnologyViewModel extends ViewModel {

    private MutableLiveData<List<ArticleEntity>> articlesLiveData = new MutableLiveData<>();


    NewsRepository newsRepository;
    Disposable listenDisposable;
    Disposable loadDataDisposable;

    public TechnologyViewModel(){
        newsRepository = App.get().getRepository();

        listenDisposable = newsRepository.listenToAllArticles("technology")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articleEntities -> {
                    articlesLiveData.setValue(new ArrayList<>(articleEntities));

                }, throwable -> {
                    // Handle the error
                    Timber.e(throwable, "Received error while fetching articles:");

                });
    }

    public void viewCreatedGetArticles(){
        loadDataDisposable = newsRepository.fetchArticlesByCategory("technology")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Data fetch completed");
                }, throwable -> {
                    Timber.e(throwable, "Data fetch failed");
                });
    }

    public LiveData<List<ArticleEntity>> getArticles(){
        return this.articlesLiveData;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        // Make sure that we cancel any subscription / operation so it doesn't run after the view
        // model is not visible and has been cleared
        // TODO: Replace with a single `CompositeDisposable`
        if(listenDisposable !=null || loadDataDisposable!=null){
            listenDisposable.dispose();
            loadDataDisposable.dispose();
        }
    }
}