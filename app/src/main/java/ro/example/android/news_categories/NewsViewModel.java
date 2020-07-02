package ro.example.android.news_categories;

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

public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<ArticleEntity>> articlesLiveData = new MutableLiveData<>();

    private String category;
    NewsRepository newsRepository;
    Disposable listenDisposable;
    Disposable loadDataDisposable;

    public NewsViewModel(){
        newsRepository = App.get().getRepository();

        listenDisposable = newsRepository.listenToAllArticles(this.category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articleEntities -> {
                    articlesLiveData.setValue(new ArrayList<>(articleEntities));

                }, throwable -> {
                    // Handle the error
                    Timber.e(throwable, "Received error while fetching articles:");

                });
    }

    public void viewCreatedGetArticles(String category){
        this.category = category;
        loadDataDisposable = newsRepository.fetchArticlesByCategory("sports")
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
}
