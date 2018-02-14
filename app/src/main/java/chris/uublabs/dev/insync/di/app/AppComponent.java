package chris.uublabs.dev.insync.di.app;

import chris.uublabs.dev.insync.di.login.LoginComponent;
import chris.uublabs.dev.insync.di.login.LoginModule;
import chris.uublabs.dev.insync.di.main.MainComponent;
import chris.uublabs.dev.insync.di.main.MainModule;
import dagger.Component;

/**
 * Created by chris on 1/15/2018.
 */

@Component(modules = AppModule.class)
public interface AppComponent
{
    MainComponent add(MainModule mainModule);
    LoginComponent add(LoginModule loginModule);
}
