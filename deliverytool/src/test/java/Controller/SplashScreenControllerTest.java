package Controller;

import Preloader.SplashScreen;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class SplashScreenControllerTest extends ApplicationTest {
    SplashScreen s;

    @Override
    public void start(Stage stage) throws Exception {
        s=new SplashScreen();
        s.start(stage);
    }

    @Test
    @SneakyThrows
    public void versionLabelNotNull(){
       assertNotNull(s.getVersionLabel());
    }
}
