package com.andro.unityplatformplugin.google;

import android.content.Context;
import android.content.Intent;

import com.andro.unityplatformplugin.AndroActivity;
import com.andro.unityplatformplugin.AndroAuthProvider;
import com.andro.unityplatformplugin.AndroAuthProviderCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AndroGoogleSigninAuth implements AndroAuthProvider {

    private GoogleSignInClient googleSignInClient;

    private static AndroAuthProviderCallback onLoginCallback;

    public void initialize(Context context, String jsonData)
    {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(AndroActivity.activity, gso);
    }

    public void login(AndroAuthProviderCallback callback)
    {
        onLoginCallback = callback;

        Intent signInIntent = googleSignInClient.getSignInIntent();

        AndroActivity.REQUEST_CODE = AndroActivity.REQUEST_AUTH_GOOGLE;
        AndroActivity.activity.startActivityForResult(signInIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully
            onLoginCallback.OnCallback(true, account.getIdToken());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            onLoginCallback.OnCallback(false, "");
        }
    }

    public void logout(final AndroAuthProviderCallback callback)
    {
        googleSignInClient.signOut()
                .addOnCompleteListener(AndroActivity.activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        callback.OnCallback(true, "");
                    }
                });
    }
}
