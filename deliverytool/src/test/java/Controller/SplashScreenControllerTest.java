package Controller;

import Preloader.SplashScreenController;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Jannik Will
 * @version 1.3
 */

public class SplashScreenControllerTest extends ApplicationTest {
    SplashScreenController s;

    @Override
    public void start(Stage stage) throws Exception {
        s = new SplashScreenController();
        s.start(stage);
    }

    @Test
    @SneakyThrows
    public void versionLabelNotNull(){
        assertNotNull(s.getVersionLabel());
    }
}
