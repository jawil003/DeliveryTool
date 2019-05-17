package App;

import Preloader.SplashScreen;
import com.sun.javafx.application.LauncherImpl;

public class Main {

    public static void main(String[] args) {
        //start JavaFX and create JavaFX window
        LauncherImpl.launchApplication(JavaFXApplication.class, SplashScreen.class, args);
    }


}
