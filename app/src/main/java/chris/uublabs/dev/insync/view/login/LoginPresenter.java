package chris.uublabs.dev.insync.view.login;

import javax.inject.Inject;

import chris.uublabs.dev.insync.data.remote.RemoteDataSource;

/**
 * Created by chris on 1/15/2018.
 */

public class LoginPresenter implements LoginContract.Presenter
{
    RemoteDataSource remoteDataSource;
    LoginContract.View view;
    public static final String TAG = LoginPresenter.class.getSimpleName() + "_TAG";
    
    @Inject
    public LoginPresenter(RemoteDataSource remoteDataSource)
    {
        this.remoteDataSource = remoteDataSource;
    }
    
    public LoginPresenter()
    {
    
    }
    
    @Override
    public void attachView(LoginContract.View view)
    {
        this.view = view;
    }
    
    @Override
    public void detachView()
    {
        this.view = null;
    }
}
