package nguyen.son.com.themoviedemo.api;

import io.reactivex.Observable;
import nguyen.son.com.themoviedemo.response.MovieResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sonnt on 4/2/17.
 */

public interface IMovieApi {
    String BASE_URL = "http://api.themoviedb.org/3/";
    String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    String API_PARAM = "api_key";
    String API_KEY = "404e1344dfd13852c89bc99b77479bf0";

    @GET("movie/now_playing")
    Observable<MovieResponse> getNowPlaying(@Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResponse> filter(@Query("primary_release_date.gte") String primaryPeleaseDateGte,
                                     @Query("primary_release_date.lte") String primaryPeleaseDateLte,
                                     @Query("release_date.gte") String releaseDateGte,
                                     @Query("release_date.lte") String releaseDateLte);
}
