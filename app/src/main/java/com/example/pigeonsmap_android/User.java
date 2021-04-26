package com.example.pigeonsmap_android;

import java.util.List;

public class User {
    String username = "guest";
    UserSettings settings;
    List<Warning> warnings;
    Location location;
    Route route;
    List<Route> savedRoutes;

    public User()
    {
        this.settings = null;
        this.warnings = null;
        this.location = null;
        this.route = null;
        this.savedRoutes = null;
    }

    public User(String username, UserSettings settings, List<Warning> warnings, Location location
                        , Route route, List<Route> savedRoutes)
    {
        this.username = username;
        this.settings = settings;
        this.warnings = warnings;
        this.location = location;
        this.route = route;
        this.savedRoutes = savedRoutes;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    public void setSettings(boolean autoLogin, boolean savePastRoutes, String loginToken,
                            int maxRoutesToStore, String units) {
        settings.setAutoLogin(autoLogin);
        settings.setLoginToken(loginToken);
        settings.setMaxRoutesToStore(maxRoutesToStore);
        settings.setSavePastRoutes(savePastRoutes);
        settings.setUnits(units);
    }

    public boolean login(String str)
    {
        return false;
    }

    public boolean updateLocation(Location location)
    {
        this.location = location;
        return true;
    }

    public boolean createWarning(String text, Location location, boolean isPrivate)
    {
        Warning newWarning = new Warning();
        newWarning.setLocation(location);
        newWarning.setText(text);
        newWarning.setPrivate(isPrivate);
        warnings.add(newWarning);
        return true;
    }


}
