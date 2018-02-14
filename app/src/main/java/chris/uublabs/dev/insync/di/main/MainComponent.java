package chris.uublabs.dev.insync.di.main;


import chris.uublabs.dev.insync.view.main.MainActivity;
import dagger.Subcomponent;

/**
 * Created by chris on 1/15/2018.
 */
@Subcomponent(modules = MainModule.class)
public interface MainComponent
{
    void inject(MainActivity mainActivity);
}
