package controllers;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // model

    private ObjectProperty<Tab> selectedTab = new SimpleObjectProperty<>();

    //logic
    
    private MapProperty<Tab, EditorController> controllers = new SimpleMapProperty<>(FXCollections.observableHashMap());

    // view
    
    @FXML
    private TabPane editTabPane;

    @FXML
    private BorderPane root;

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onCloseAction(ActionEvent event) {

    }

    @FXML
    void onCloseAllAction(ActionEvent event) {

    }

    @FXML
    void onCopyAction(ActionEvent event) {
        controllers.get(selectedTab.get()).copy();

    }

    @FXML
    void onCutAction(ActionEvent event) {

//        Tab selectedTab = editTabPane.getSelectionModel().getSelectedItem();
//        EditorController controller = controllers.get(selectedTab);
//        controller.cut();

        controllers.get(selectedTab.get()).cut();

    }

    @FXML
    void onNewAction(ActionEvent event) {

        newFile();

    }

    private EditorController newFile() {
        EditorController editorController = new EditorController();

        Tab newTab = new Tab();
        newTab.setContent(editorController.getRoot());
        newTab.textProperty().bind(editorController.nameProperty());

        editTabPane.getTabs().add(newTab);
        controllers.put(newTab, editorController);
        editTabPane.getSelectionModel().select(newTab);  // seleccionamos la pestaña que acabamos de añadir

        return editorController;
    }

    @FXML
    void onOpenAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todosl los archivos", "*.*"));
        File file = fileChooser.showOpenDialog(getRoot().getScene().getWindow());

        if (file != null) {
            EditorController controller = newFile();
            controller.setFile(file);
        }
    }

    @FXML
    void onPasteAction(ActionEvent event) {
        controllers.get(selectedTab.get()).paste();

    }

    @FXML
    void onRedoAction(ActionEvent event) {
        controllers.get(selectedTab.get()).redo();

    }

    @FXML
    void onSaveAction(ActionEvent event) {

    }

    @FXML
    void onUndoAction(ActionEvent event) {
        controllers.get(selectedTab.get()).undo();

    }

    public TabPane getEditTabPane() {
        return editTabPane;
    }

    public void setEditTabPane(TabPane editTabPane) {
        this.editTabPane = editTabPane;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Cierra las pestañas default de inicio
        editTabPane.getTabs().clear();

        newFile();

        selectedTab.bind(editTabPane.getSelectionModel().selectedItemProperty());
        
    }
}
