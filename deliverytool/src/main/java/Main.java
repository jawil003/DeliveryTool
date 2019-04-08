import javafx.application.Application;
import javafx.stage.Stage;
import model.Pizzaverwaltung;

public class Main extends Application {

    private Pizzaverwaltung pizzaverwaltung;

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
