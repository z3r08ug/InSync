package chris.uublabs.dev.insync.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chris on 1/27/2018.
 * This is a singleton class designed to handle facebook interaction
 *
 */

public class FacebookHandler
{

    private static final String TAG = "FacebookHandlerTag";
    //TODO put our app url here
    private static final String APP_URL = "http://www.smartmemeapp.com";
    private AccessToken accessToken=null;
    private CallbackManager callbackManager;
    private static FacebookHandler handler=null;

    private FacebookHandler()
    {
    
    }
    public static synchronized FacebookHandler getInstance()
    {
        if(handler==null)
            handler = new FacebookHandler();
        return handler;
    }

    //Call in onCreate stage to register facebook login button
    public void registerLoginButton(LoginButton fbLoginButton, final FacebookLoginListener facebookLoginListener)
    {
        callbackManager = CallbackManager.Factory.create();
        //Ask for email and user_likes permissions
        //We will ask for publish to wall permission later on when user is ready to post
        fbLoginButton.setReadPermissions("email","user_likes");
        fbLoginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        Log.d(TAG, "facebook:onSuccess: "+loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        facebookLoginListener.onSuccess();
                    }

                    @Override
                    public void onCancel()
                    {
                        Log.d(TAG, "onCancel: ");
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        Log.d(TAG, "facebook:nError: ",exception);
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token)
    {
        Log.d(TAG, "handleFacebookAccessToken: "+token);
        accessToken = token;
    }

    public AccessToken getAccessToken()
    {
        //If user is already logged in, set accessToken to current one
        if(accessToken==null)
            if(AccessToken.getCurrentAccessToken()!=null)
                accessToken= AccessToken.getCurrentAccessToken();

        return accessToken;
    }

    public void getPublishPermission(Activity activity, final FacebookPermissionListener facebookPermissionListener)
    {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Log.d(TAG, "onSuccess: getPublishPermission: onSuccess");
                facebookPermissionListener.onPermissionSuccess();
            }

            @Override
            public void onCancel()
            {
                Log.d(TAG, "onSuccess: getPublishPermission: onCancel");
            }

            @Override
            public void onError(FacebookException error)
            {
                Log.d(TAG, "onSuccess: getPublishPermission: onError");
            }
        });
        LoginManager.getInstance().logInWithPublishPermissions(activity, Arrays.asList("publish_actions"));
    }

    public Set<String> getCurrentPermissions()
    {
        if(accessToken==null)
            return new HashSet<>();
        if(accessToken.getCurrentAccessToken()==null)
            return new HashSet<>();
        return accessToken.getCurrentAccessToken().getPermissions();
    }

    public Set<String> getDeclinedPermissions()
    {
        if(accessToken==null)
            return new HashSet<>();
        if(accessToken.getCurrentAccessToken()==null)
            return new HashSet<>();
        return accessToken.getCurrentAccessToken().getDeclinedPermissions();
    }

    //Make Post to Facebook
    public void shareDialog(Bitmap bitmap, Activity activity)
    {
        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        ShareContent shareContent = new ShareMediaContent.Builder()
                .addMedium(sharePhoto)
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
    }

    //Make Link post to Facebook
    public void shareDialog(String url, Activity activity)
    {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(url))
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    public void getName(final FacebookListener facebookListener)
    {
        if(accessToken==null)
            if(AccessToken.getCurrentAccessToken()!=null)
                accessToken= AccessToken.getCurrentAccessToken();
        if(accessToken!=null)
        {
            GraphRequest request = GraphRequest.newMeRequest(accessToken,
                    new GraphRequest.GraphJSONObjectCallback()
                    {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response)
                        {
                            Log.e(TAG, object.toString());
                            Log.e(TAG, response.toString());

                            try
                            {
                                String userID = object.getString("id");
                                String name = "";
                                if(object.has("name"))
                                    name = object.getString("name");
                                Log.d(TAG, "onCompleted: id "+userID);
                                Log.d(TAG, "onCompleted: name "+name);
                                //Return first name only
                                int index = name.indexOf(" ");
                                if(index!=-1)name = name.substring(0,index);
                                facebookListener.receiveFacebookName(name);
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });

            request.executeAsync();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLoggedIn() {
        return (getAccessToken()!=null);
    }

    public void logout() {
        LoginManager.getInstance().logOut();
    }

    //Any Class that wants to get back the facebook name must implement this interface
    //Result of a getName() request will be return to the interface
    public interface FacebookListener
    {
        void receiveFacebookName(String name);
    }

    //Any Class that wants to listen to facebook login events can implement this interface
    public interface FacebookLoginListener
    {
        void onSuccess();
    }

    //Any Class that wants to listen to facebook permission request can implement this method
    public interface FacebookPermissionListener
    {
        void onPermissionSuccess();
    }

}