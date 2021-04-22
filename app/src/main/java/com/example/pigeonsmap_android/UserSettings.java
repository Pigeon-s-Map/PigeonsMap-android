package com.example.pigeonsmap_android;

public class UserSettings {
    boolean autoLogin;
    boolean savePastRoutes;
    String loginToken;
    int maxRoutesToStore;
    String units;

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
