package chris.uublabs.dev.insync.di.login;

import chris.uublabs.dev.insync.data.remote.RemoteDataSource;
import chris.uublabs.dev.insync.view.login.LoginPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 1/15/2018.
 */
@Module
public class LoginModule
{
    @Provides
    LoginPresenter providerLoginPresenter(RemoteDataSource remoteDataSource)
    {
        return new LoginPresenter(remoteDataSource);
    }
}
