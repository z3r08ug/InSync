package chris.uublabs.dev.insync.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chris on 1/15/2018.
 */

public class RemoteDataSource
{
    private static String baseURL;
    
    public RemoteDataSource(String baseURL)
    {
        this.baseURL = baseURL;
    }
    
    public static Retrofit create()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                //add converter to parse the response
                .addConverterFactory(GsonConverterFactory.create())
                //add call adapter to convert the response to RxJava observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        
        return retrofit;
    }
    
//    public static Observable<BingSearch> getkeywordResponse(String search, Integer num)
//    {
//        Retrofit retrofit = create();
//        BingSearchRemoteService remoteService = retrofit.create(BingSearchRemoteService.class);
//        return remoteService.getBingkeywordResponse(search, num);
//    }
}
