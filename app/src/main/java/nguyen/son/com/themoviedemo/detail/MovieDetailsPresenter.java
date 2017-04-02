package nguyen.son.com.themoviedemo.detail;

import android.support.annotation.VisibleForTesting;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {
    private WeakReference<MovieDetailsContract.View> mViewWeakReference;

    @VisibleForTesting
    private Movie mMovie;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.View view) {
        this.mViewWeakReference = new WeakReference<MovieDetailsContract.View>(view);
    }

    @Override
    public void setMovieData(Movie movie) {
        this.mMovie = movie;
    }

    @Override
    public void updateLayout() {
        MovieDetailsContract.View view = mViewWeakReference.get();
        if (view == null) {
            return;
        }

        if (mMovie == null) {
            return;
        }

        view.showMovieBackground(mMovie.resolvePath(mMovie.getBackdropPath()));
        view.showTitle(mMovie.getTitle());
        view.showOverview(mMovie.getOverview());
        view.showOriginalTitle(mMovie.getOriginalTitle());
    }

    @VisibleForTesting
    public Movie getMovie() {
        return mMovie;
    }
}
