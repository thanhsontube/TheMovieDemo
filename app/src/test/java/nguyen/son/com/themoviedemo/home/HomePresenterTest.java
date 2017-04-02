package nguyen.son.com.themoviedemo.home;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import nguyen.son.com.themoviedemo.response.Movie;
import nguyen.son.com.themoviedemo.response.MovieResponse;

/**
 * Created by sonnt on 4/2/17.
 */
public class HomePresenterTest {
    @Mock
    HomeContract.View mView;

    @Mock
    IRepository mRepository;

    @Mock
    MovieResponse mMovieResponse;

    HomePresenter mPresenter;


    @Before
    public void setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) {
                return Schedulers.trampoline();
            }
        });
        MockitoAnnotations.initMocks(this);
        mPresenter = new HomePresenter(mView, mRepository);
        Whitebox.setInternalState(mPresenter, "currentPage", 1);
    }

    @Test
    public void shouldShowListOfMovies() {
        Whitebox.setInternalState(mPresenter, "currentPage", 1);
        Whitebox.setInternalState(mPresenter, "mDateFilter", "");

        final List<Movie> movies = Arrays.asList(new Movie());
        Mockito.when(mRepository.getNowPlaying(Mockito.anyInt())).thenReturn(Observable.just(movies));
        mPresenter.loadMovie();
        Mockito.verify(mView).showNowPlayingMovies(movies, false);
    }

    @Test
    public void clean() {
        //given
        final List<Movie> movies = Arrays.asList(new Movie());
        Mockito.when(mRepository.getNowPlaying(Mockito.anyInt())).thenReturn(Observable.just(movies));

        //then
        mPresenter.cleanFilter();

        //that
        Assert.assertEquals(Whitebox.getInternalState(mPresenter, "currentPage"), 1);
        Assert.assertEquals(Whitebox.getInternalState(mPresenter, "mDateFilter"), null);
        Mockito.verify(mView).updateFilter();
        Mockito.verify(mView).showNowPlayingMovies(movies, false);
    }

}