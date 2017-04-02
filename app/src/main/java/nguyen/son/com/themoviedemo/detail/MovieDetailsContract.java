package nguyen.son.com.themoviedemo.detail;

import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */

public class MovieDetailsContract {
    public interface View {

        void showMovieBackground(String path);

        void showTitle(String title);

        void showOverview(String overview);

        void showOriginalTitle(String originalTitle);
    }

    public interface Presenter {

        void setMovieData(Movie movie);

        void updateLayout();
    }
}
