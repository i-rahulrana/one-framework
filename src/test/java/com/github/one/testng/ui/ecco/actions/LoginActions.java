package com.github.one.testng.ui.ecco.actions;

import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;

public class LoginActions {
    private final PlatformType platformType;

    public LoginActions() {
        this.platformType = ParallelSession.getSession ().getPlatformType ();
    }
}
