package nguyen.son.com.themoviedemo.detail;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */
public class MovieDetailsPresenterTest {
    @Mock
    MovieDetailsContract.View mView;


    MovieDetailsPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new MovieDetailsPresenter(mView);
    }

    @Test
    public void setMovieData() throws Exception {
        Movie movie = createAMovie();
        mPresenter.setMovieData(movie);
        Assert.assertEquals(mPresenter.getMovie(), movie);

    }

    @Test
    public void updateLayout() throws Exception {
        Movie movie = createAMovie();
        mPresenter.setMovieData(movie);
        mPresenter.updateLayout();
        Mockito.verify(mView).showTitle("title");
        Mockito.verify(mView).showMovieBackground("http://image.tmdb.org/t/p/w500/path?api_key=404e1344dfd13852c89bc99b77479bf0");
        Mockito.verify(mView).showOverview("overview");

    }

    private Movie createAMovie() {
        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setOverview("overview");
        movie.setBackdropPath("/path");
        return movie;
    }
}