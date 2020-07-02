package ro.example.android.news_categories.headlines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ro.example.android.App;
import ro.example.android.data.NewsRepository;
import ro.example.android.data.local.ArticleEntity;
import timber.log.Timber;

public class HeadlinesFragmentViewModel extends ViewModel {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private MutableLiveData<String> descriptionLiveData = new MutableLiveData<>("My initial VM description");
    private MutableLiveData<List<ArticleEntity>> articlesListLiveData = new MutableLiveData<>();

    private String text;

    private NewsRepository newsRepository;
    Disposable listenDisposable;
    Disposable loadDataDisposable;

    public HeadlinesFragmentViewModel() {
        newsRepository = App.get().getRepository();

        // Start listening to article updates - we will be notified with a new list of articles
        // every time something is written into the database
        listenDisposable = newsRepository.listenToAllArticles("headlines")
                // When the results come back, make sure we switch to main thread to handle them
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    Timber.d("Received response: %s", articles);

                    // Just keep a list of article names
//                    List<String> articleNames = articles.stream()
//                            .map(articleEntity -> articleEntity.title.substring(0, 8))
//                            .collect(Collectors.toList());

                    articlesListLiveData.setValue(new ArrayList<>(articles));

//                    descriptionLiveData.setValue("Received " + articles.size() + " articles at " +
//                            dateTimeFormatter.format(LocalTime.now()) + ": " + articleNames);
                }, throwable -> {
                    // Handle the error
                    Timber.e(throwable, "Received error while fetching articles:");
                    descriptionLiveData.setValue("An error has occurred: " + throwable.getMessage());
                });
    }

    public void viewCreatedGetArticles(){
        loadDataDisposable = newsRepository.fetchHeadLines()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Data fetch completed");
                }, throwable -> {
                    Timber.e(throwable, "Data fetch failed");
                });
    }


    public void onButtonClicked() {
        Timber.d("Button was clicked!");

        // Start fetching articles and listen to know if the update operation has succeeded or not
        // TODO: Expose synchronization status via a spinner
        loadDataDisposable = newsRepository.fetchHeadLines()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Data fetch completed");
                }, throwable -> {
                    Timber.e(throwable, "Data fetch failed");
                });
    }

    public String getTitle() {
        return "My VM title";
    }

    public LiveData<String> getDescription() {
        return descriptionLiveData;
    }
    public LiveData<List<ArticleEntity>> getArticles() {
        return articlesListLiveData;
    }
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        Timber.d("Text updated: %s", text);
        this.text = text;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Make sure that we cancel any subscription / operation so it doesn't run after the view
        // model is not visible and has been cleared
        // TODO: Replace with a single `CompositeDisposable`

        //!!!!!!!!!!!!!!!!!!!!EROARE
        if(listenDisposable !=null && loadDataDisposable!=null){
            listenDisposable.dispose();
            loadDataDisposable.dispose();
        }

    }
}
