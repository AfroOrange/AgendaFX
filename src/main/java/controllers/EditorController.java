package controllers;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

    // model

    private final ObjectProperty<File> file = new SimpleObjectProperty<>();
    private final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper("Untitled");
    private final StringProperty content = new SimpleStringProperty();
    private final BooleanProperty hasChanges = new SimpleBooleanProperty();


    //view

    @FXML
    private TextArea editArea;

    @FXML
    private AnchorPane root;

    public EditorController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditorView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // bindings
       file.addListener(this::onFileChange);

        editArea.textProperty().bindBidirectional(content);

        content.addListener(this::onContentChanged);

        name.bind(Bindings.createStringBinding(this::updateName, file, hasChanges));
    }

    public AnchorPane getRoot() {
        return root;
    }

    // listeners

    private void onFileChange(Observable observable, File oldValue, File newValue) {
        if (newValue != null) {
            open();
        }
    }

    private String updateName() {
        return (file.get() == null ? "Untitled" :
                file.get().getName() + (hasChanges.get() ? "*" : ""));
    }

    private void onContentChanged(Observable observable, String oldValue, String newValue) {
            hasChanges.set(true);
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public TextArea getEditArea() {
        return editArea;
    }

    public void setEditArea(TextArea editArea) {
        this.editArea = editArea;
    }

    // logic

    public void open() {
        File file = this.file.get();
        try {
            this.content.set(Files.readString(file.toPath()));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cut() {
        editArea.cut();
    }
    public void paste() {
        editArea.paste();
    }
    public void copy() {
        editArea.copy();
    }
    public void undo() {
        editArea.undo();
    }
    public void redo() {
        editArea.redo();
    }

    // getters & setters for file
    public File getFile() {
        return file.get();
    }

    public ObjectProperty<File> fileProperty() {
        return file;
    }

    public void setFile(File file) {
        this.file.set(file);
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringProperty nameProperty() {
        return name.getReadOnlyProperty();
    }
}
