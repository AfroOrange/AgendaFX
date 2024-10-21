package controllers;

import dad.AgendaTab;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
        editTabPane.getTabs().remove(selectedTab.get());
    }

    @FXML
    void onCloseAllAction(ActionEvent event) {

        editTabPane.getTabs().clear();

    }

    private EditorController getSelectedEditor() {
        return ((AgendaTab) selectedTab.get()).getController();
    }

    @FXML
    void onCopyAction(ActionEvent event) {
        getSelectedEditor().copy();
    }

    @FXML
    void onCutAction(ActionEvent event) {
        getSelectedEditor().cut();
    }

    @FXML
    void onNewAction(ActionEvent event) {
        newTab();
    }

    private AgendaTab newTab() {
        AgendaTab newTab = new AgendaTab();
        editTabPane.getTabs().add(newTab);
        editTabPane.getSelectionModel().select(newTab);  // seleccionamos la pestaña que acabamos de añadir
        return newTab;
    }

    @FXML
    void onOpenAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*"));
        File file = fileChooser.showOpenDialog(getRoot().getScene().getWindow());

        if (file != null) {
            AgendaTab tab = new AgendaTab();
            tab.getController().open(file);

        }
    }

    @FXML
    void onPasteAction(ActionEvent event) {
        getSelectedEditor().paste();

    }

    @FXML
    void onRedoAction(ActionEvent event) {
       getSelectedEditor().redo();

    }

    @FXML
    void onSaveAction(ActionEvent event) {
        if (getSelectedEditor().getFile() != null)
            getSelectedEditor().save();
        else
            onSaveAsAction(event);

    }


    @FXML
    void onSaveAsAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*"));
        File file = fileChooser.showSaveDialog(getRoot().getScene().getWindow());

        if (file != null) {
            getSelectedEditor().setFile(file);
            getSelectedEditor().save();
        }
    }

    @FXML
    void onUndoAction(ActionEvent event) {
        getSelectedEditor().undo();
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

        newTab();

        selectedTab.bind(editTabPane.getSelectionModel().selectedItemProperty());
        
    }
}
