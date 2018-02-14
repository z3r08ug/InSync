package chris.uublabs.dev.insync.di.login;


import chris.uublabs.dev.insync.view.login.LoginActivity;
import chris.uublabs.dev.insync.view.main.MainActivity;
import dagger.Subcomponent;

/**
 * Created by chris on 1/15/2018.
 */
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent
{
    void inject(LoginActivity loginActivity);
}
