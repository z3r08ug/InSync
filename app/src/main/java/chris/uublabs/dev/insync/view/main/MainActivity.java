package chris.uublabs.dev.insync.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import chris.uublabs.dev.insync.InSyncApplication;
import chris.uublabs.dev.insync.R;

public class MainActivity extends AppCompatActivity implements MainContract.View
{
    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    @Inject
    MainPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InSyncApplication.get(this).getMainComponent().inject(this);
        
        presenter.attachView(this);
    }
    
    @Override
    public void showError(String error)
    {
    
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.detachView();
    }
}
