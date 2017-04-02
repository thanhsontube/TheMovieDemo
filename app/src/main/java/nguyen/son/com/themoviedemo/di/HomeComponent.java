package nguyen.son.com.themoviedemo.di;

import dagger.Subcomponent;
import nguyen.son.com.themoviedemo.HomeActivity;
import nguyen.son.com.themoviedemo.di.scope.ActivityScoped;

/**
 * Created by sonnt on 4/2/17.
 */

@ActivityScoped
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}
