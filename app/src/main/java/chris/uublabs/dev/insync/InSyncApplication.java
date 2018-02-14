package chris.uublabs.dev.insync;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import chris.uublabs.dev.insync.di.app.AppComponent;
import chris.uublabs.dev.insync.di.app.AppModule;
import chris.uublabs.dev.insync.di.app.DaggerAppComponent;
import chris.uublabs.dev.insync.di.login.LoginComponent;
import chris.uublabs.dev.insync.di.login.LoginModule;
import chris.uublabs.dev.insync.di.main.MainComponent;
import chris.uublabs.dev.insync.di.main.MainModule;
import chris.uublabs.dev.insync.util.Constants;

/**
 * Created by chris on 1/15/2018.
 */

public class InSyncApplication extends Application
{
    private AppComponent appComponent;
    private MainComponent mainComponent;
    private LoginComponent loginComponent;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    
        AppModule appModule = new AppModule(Constants.BASE_URL);
        
        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
    
        AppEventsLogger.activateApp(this);
    }
    
    public static InSyncApplication get(Context context)
    {
        return (InSyncApplication) context.getApplicationContext();
    }
    
    public MainComponent getMainComponent()
    {
        mainComponent = appComponent.add(new MainModule());
        return mainComponent;
    }
    
    public void clearMainComponent()
    {
        mainComponent = null;
    }
    
    public LoginComponent getLoginComponent()
    {
        loginComponent = appComponent.add(new LoginModule());
        return loginComponent;
    }
    
    public void clearLoginComponent()
    {
        loginComponent = null;
    }
    
    
}
