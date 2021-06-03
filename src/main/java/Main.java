import controller.Controller;
import finder.Finder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage){
        try {
            var loader = new FXMLLoader(getClass().getClassLoader().getResource("window.fxml"));

            Parent root=loader.load();

            var finder= new Finder();
            var ctrl=(Controller)loader.getController();
            ctrl.setFinder(finder);



            var stage=new Stage();
            ctrl.setStage(stage);
            var scene=new Scene(root);
            stage.setScene(scene);
            stage.setTitle("File analyser");
            stage.show();

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
