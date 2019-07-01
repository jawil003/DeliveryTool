/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package App;

import Preloader.SplashScreenController;
import com.sun.javafx.application.LauncherImpl;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class Main {

    public static void main(String[] args) {
        //start JavaFX and create JavaFX window
        //LauncherImpl.launchApplication(JavaFXApplication.class, SplashScreenController.class, args);
        LauncherImpl.launchApplication(JavaFXApplication.class, SplashScreenController.class, args);
    }


}
