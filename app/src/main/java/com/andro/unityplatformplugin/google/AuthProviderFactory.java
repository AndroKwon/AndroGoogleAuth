package com.andro.unityplatformplugin.google;

import com.andro.unityplatformplugin.AndroAuthProvider;

public class AuthProviderFactory {

    private static AndroGoogleSigninAuth googleAuth = null;

    public static AndroAuthProvider create() {
        if (googleAuth != null)
            return googleAuth;

        googleAuth = new AndroGoogleSigninAuth();
        return googleAuth;
    }

}
