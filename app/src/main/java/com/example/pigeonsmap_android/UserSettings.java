package com.example.pigeonsmap_android;

public class UserSettings {
    boolean autoLogin;
    boolean savePastRoutes;
    String loginToken;
    int maxRoutesToStore;
    String units;

    public UserSettings()
    {
        this.autoLogin = false;
        this.savePastRoutes = false;
        this.loginToken = null;
        this.maxRoutesToStore = 0;
        this.units = null;
    }

    public UserSettings(boolean autoLogin, boolean savePastRoutes, String loginToken, int maxRoutesToStore
                        , String units)
    {
        this.autoLogin = autoLogin;
        this.savePastRoutes = savePastRoutes;
        this.loginToken = loginToken;
        this.maxRoutesToStore = maxRoutesToStore;
        this.units = units;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public void setSavePastRoutes(boolean savePastRoutes) {
        this.savePastRoutes = savePastRoutes;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public void setMaxRoutesToStore(int maxRoutesToStore) {
        this.maxRoutesToStore = maxRoutesToStore;
    }

    public void setUnits(String units) {
        this.units = units;
    }

}
