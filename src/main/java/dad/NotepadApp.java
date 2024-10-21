package dad;

import controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NotepadApp extends Application {

    private final RootController rootController = new RootController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene notepadScene = new Scene(rootController.getRoot());

        Stage notepadStage = new Stage();
        notepadStage.setTitle("Notepad Bichado");
        notepadStage.setScene(notepadScene);
        notepadStage.show();

    }
}
