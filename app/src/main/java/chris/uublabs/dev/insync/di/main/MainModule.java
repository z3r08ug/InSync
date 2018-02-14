package chris.uublabs.dev.insync.di.main;

import chris.uublabs.dev.insync.data.remote.RemoteDataSource;
import chris.uublabs.dev.insync.view.main.MainPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 1/15/2018.
 */
@Module
public class MainModule
{
    @Provides
    MainPresenter providerMainPresenter(RemoteDataSource remoteDataSource)
    {
        return new MainPresenter(remoteDataSource);
    }
}
