import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
      //setting the title of the program
    primaryStage.setTitle("DeliveryTool");
      //show the first window of the software, where you can pick the pizzas the consumer wants
    final MainWindow mainWindow = MainWindow.showScene(primaryStage);
  }
}
