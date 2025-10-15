package ca.umanitoba.cs.veranyan.ui;

import ca.umanitoba.cs.veranyan.logic.ProfileRegistry;

public class MainMenu {
    private final ProfileRegistry profileRegistry;

    public MainMenu(){
        profileRegistry = new ProfileRegistry();
    }

    public void startProgram(){
        var loginDisplay = new LoginDisplay(profileRegistry);

        loginDisplay.startLogin();
    }
}
