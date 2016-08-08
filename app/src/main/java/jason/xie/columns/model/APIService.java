package jason.xie.columns.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public interface APIService {

    @GET("api/columns/{id}")
    Observable<Column> getColumnById(@Path("id") String id);

    @GET("api/columns/{id}/posts?")
    Observable<List<Article>> getArticleList(@Path("id") String id, @Query("limit") int limit, @Query("offset") int offset);

    @GET("api/posts/{slug}")
    Observable<ArticleDetail> getArticleContent(@Path("slug") String slug);

    class Factory {
        public static APIService create(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://zhuanlan.zhihu.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(APIService.class);
        }
    }
}
