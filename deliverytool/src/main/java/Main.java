import javafx.application.Application;
import javafx.stage.Stage;
import model.Pizzaverwaltung;

import java.sql.SQLException;

public class Main extends Application {

    private Pizzaverwaltung pizzaverwaltung;

    {
        try {
            pizzaverwaltung = new Pizzaverwaltung();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("DeliveryTool");
        final MainWindow mainWindow = MainWindow.showScene(primaryStage);
        mainWindow.setPizzaverwaltung(pizzaverwaltung);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
