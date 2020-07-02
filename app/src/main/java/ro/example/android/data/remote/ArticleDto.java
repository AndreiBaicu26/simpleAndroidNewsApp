package ro.example.android.data.remote;

import com.google.gson.annotations.SerializedName;

import ro.example.android.data.local.ArticleEntity;

public class ArticleDto {
    public final String title;
    @SerializedName("description")
    public final String sourceName;
    public final String urlToImage;
    public final String url;
    public final Source source;

    public ArticleDto(String title, String sourceName, String urlToImage, String url, Source source) {
        this.title = title;
        this.sourceName = sourceName;
        this.urlToImage = urlToImage;
        this.url = url;
        this.source = source;
    }

    public static class Source {
        public final String id;
        public final String name;

        public Source(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public ArticleEntity toEntity() {

        return new ArticleEntity(title, this.source.name, urlToImage, url,"");
    }
}
