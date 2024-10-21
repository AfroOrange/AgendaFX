package dad;

import controllers.EditorController;
import javafx.scene.control.Tab;

public class AgendaTab extends Tab {
    private final EditorController controller;

    public AgendaTab() {
        super();
        controller = new EditorController();
        setContent(controller.getRoot());
        textProperty().bind(controller.nameProperty());
    }

    public EditorController getController() {
        return controller;
    }
}
