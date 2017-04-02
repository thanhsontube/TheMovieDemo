package nguyen.son.com.themoviedemo.di;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import nguyen.son.com.themoviedemo.api.IMovieApi;
import nguyen.son.com.themoviedemo.home.HomeContract;
import nguyen.son.com.themoviedemo.home.HomePresenter;
import nguyen.son.com.themoviedemo.home.HomeRepository;
import nguyen.son.com.themoviedemo.home.IRepository;

/**
 * Created by sonnt on 4/2/17.
 */
@Module(includes = HomeModule.Declaration.class)
public class HomeModule {
    HomeContract.View mView;

    @Module
    public interface Declaration {
        @Binds
        HomeContract.Presenter bindTo(HomePresenter presenter);
    }

    public HomeModule(HomeContract.View view) {
        mView = view;
    }

    @Provides
    HomeContract.View provideView() {
        return mView;
    }

    @Provides
    IRepository provideRepository(IMovieApi api) {
        return new HomeRepository(api);
    }


}
