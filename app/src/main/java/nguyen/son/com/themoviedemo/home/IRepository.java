package nguyen.son.com.themoviedemo.home;

import java.util.List;

import io.reactivex.Observable;
import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */

public interface IRepository {
    Observable<List<Movie>> getNowPlaying(int page);

    Observable<List<Movie>> filter(String dateFilter);
}
