package nguyen.son.com.themoviedemo.home;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */

public class HomePresenter implements HomeContract.Presenter {
    private WeakReference<HomeContract.View> mViewWeakReference;
    private IRepository mRepository;
    private int currentPage = 1;
    private String mDateFilter;
    //todo will remove CompositeDisposable if using RxLifecycle
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public HomePresenter(HomeContract.View view, IRepository homeRepository) {
        this.mViewWeakReference = new WeakReference<HomeContract.View>(view);
        mRepository = homeRepository;
    }

    @Override
    public void loadMovie() {

        mRepository.getNowPlaying(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        processMovies(movies, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        HomeContract.View view = mViewWeakReference.get();
                        if (view == null) {
                            return;
                        }
                        view.showEmptyView();
                    }

                    @Override
                    public void onComplete() {
                        //do nothing
                    }
                });
    }


    @Override
    public void loadMore() {
        if (mDateFilter != null && mDateFilter.length() > 0) {
            return;
        }
        currentPage++;
        mRepository.getNowPlaying(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        processMovies(movies, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        HomeContract.View view = mViewWeakReference.get();
                        if (view == null) {
                            return;
                        }
                        view.showEmptyView();
                    }

                    @Override
                    public void onComplete() {
                        //do nothing
                    }
                });
    }

    @Override
    public void filter(String dateFilter) {
        mDateFilter = dateFilter;
        mRepository.filter(dateFilter).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        processMovies(movies, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        HomeContract.View view = mViewWeakReference.get();
                        if (view == null) {
                            return;
                        }
                        view.showEmptyView();
                    }

                    @Override
                    public void onComplete() {
                        //do nothing
                    }
                });
    }

    private void processMovies(List<Movie> movies, boolean append) {
        HomeContract.View view = mViewWeakReference.get();
        if (view == null) {
            return;
        }
        if (movies == null || movies.isEmpty()) {
            view.showEmptyView();
        } else {

            view.showNowPlayingMovies(movies, append);
        }

    }

    @Override
    public void cleanFilter() {
        mDateFilter = null;
        currentPage = 1;
        HomeContract.View view = mViewWeakReference.get();
        if (view == null) {
            return;
        }
        view.updateFilter();
        loadMovie();
    }

    @Override
    public void release() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }

    }
}
