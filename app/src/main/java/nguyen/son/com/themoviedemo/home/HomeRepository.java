package nguyen.son.com.themoviedemo.home;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import nguyen.son.com.themoviedemo.api.IMovieApi;
import nguyen.son.com.themoviedemo.response.Movie;
import nguyen.son.com.themoviedemo.response.MovieResponse;

/**
 * Created by sonnt on 4/2/17.
 */

public class HomeRepository implements IRepository {

    @NonNull IMovieApi mApi;

    public HomeRepository(IMovieApi api) {
        mApi = api;
    }

    @Override
    public Observable<List<Movie>> getNowPlaying(int page) {
        return mApi.getNowPlaying(page)
                .flatMap(new Function<MovieResponse, ObservableSource<List<Movie>>>() {
                    @Override
                    public ObservableSource<List<Movie>> apply(MovieResponse movieResponse) throws Exception {
                        return Observable.just(movieResponse.getResults());
                    }
                });
    }

    @Override
    public Observable<List<Movie>> filter(String dateFilter) {
        return mApi.filter(dateFilter, dateFilter, dateFilter, dateFilter)
                .flatMap(new Function<MovieResponse, ObservableSource<List<Movie>>>() {
                    @Override
                    public ObservableSource<List<Movie>> apply(MovieResponse movieResponse) throws Exception {
                        return Observable.just(movieResponse.getResults());
                    }
                });
    }
}
