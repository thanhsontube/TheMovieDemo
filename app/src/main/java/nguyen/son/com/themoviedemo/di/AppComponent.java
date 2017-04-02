package nguyen.son.com.themoviedemo.di;

import javax.inject.Singleton;

import dagger.Component;
import nguyen.son.com.themoviedemo.di.detail.DetailComponent;
import nguyen.son.com.themoviedemo.di.detail.DetailModule;

/**
 * Created by sonnt on 4/2/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    HomeComponent plus(HomeModule module);

    DetailComponent plus(DetailModule module);
}
