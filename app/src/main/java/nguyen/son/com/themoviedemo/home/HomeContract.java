package nguyen.son.com.themoviedemo.home;

import java.util.List;

import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */

public interface HomeContract {
    interface View {

        void showEmptyView();

        void showNowPlayingMovies(List<Movie> results, boolean append);

        void updateFilter();

        void releaseReferences();

    }

    interface Presenter {

        void loadMovie();

        void loadMore();

        void filter(String dateFilter);

        void cleanFilter();

        void release();
    }
}
