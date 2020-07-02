package ro.example.android.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArticleEntity {
    public final String title;
    public final String sourceName;
    public final String urlToImage;
    public String category;
    @PrimaryKey
    @NonNull
    public final String url;

    public ArticleEntity(String title,  String sourceName, String urlToImage, String url, String category) {
        this.title = title;
        this.sourceName = sourceName;
        this.urlToImage = urlToImage;
        this.url = url;
        this.category = "";
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
