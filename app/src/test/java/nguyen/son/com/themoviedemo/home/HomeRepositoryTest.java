package nguyen.son.com.themoviedemo.home;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Observable;
import nguyen.son.com.themoviedemo.api.IMovieApi;
import nguyen.son.com.themoviedemo.response.Movie;
import nguyen.son.com.themoviedemo.response.MovieResponse;

/**
 * Created by sonnt on 4/2/17.
 */
public class HomeRepositoryTest {
    @Mock IMovieApi mApi;
    @InjectMocks HomeRepository mHomeRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getNowPlaying() throws Exception {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setResults(new ArrayList<Movie>());
        Mockito.when(mApi.getNowPlaying(Mockito.anyInt())).thenReturn(Observable.just(movieResponse));
        mHomeRepository.getNowPlaying(1)
                .test()
                .assertValue(movieResponse.getResults())
                .assertComplete();

    }

    @Test
    public void filter() throws Exception {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setResults(new ArrayList<Movie>());
        Mockito.when(mApi.filter(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(Observable.just(movieResponse));
        mHomeRepository.filter("2017-12-12")
                .test()
                .assertValue(movieResponse.getResults())
                .assertComplete();

    }

}