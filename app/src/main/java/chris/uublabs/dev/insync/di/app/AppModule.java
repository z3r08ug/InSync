package chris.uublabs.dev.insync.di.app;

import chris.uublabs.dev.insync.data.remote.RemoteDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 1/15/2018.
 */
@Module
public class AppModule
{
    String baseURL;
    
    public AppModule(String baseURL)
    {
        this.baseURL = baseURL;
    }
    
    @Provides
    RemoteDataSource providesRemoteDataSource()
    {
        return new RemoteDataSource(baseURL);
    }
}
