package nguyen.son.com.themoviedemo.di.detail;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import nguyen.son.com.themoviedemo.detail.MovieDetailsContract;
import nguyen.son.com.themoviedemo.detail.MovieDetailsPresenter;

/**
 * Created by sonnt on 4/2/17.
 */
@Module(includes = DetailModule.Declaration.class)
public class DetailModule {
    MovieDetailsContract.View mView;

    @Module
    public interface Declaration {
        @Binds
        MovieDetailsContract.Presenter bindTo(MovieDetailsPresenter presenter);
    }

    public DetailModule(MovieDetailsContract.View view) {
        mView = view;
    }

    @Provides
    MovieDetailsContract.View provideView() {
        return mView;
    }

}
