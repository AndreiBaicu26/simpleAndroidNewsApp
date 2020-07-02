package ro.example.android.data.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines?country=us")
    Single<ArticlesResponse> fetchCategory(@Query("apiKey") String apiKey,
                                           @Query("category") String query);


    @GET("top-headlines?country=us")
    Single<ArticlesResponse> fetchTopHeadlines(@Query("apiKey") String apiKey);
}
