package controller;

import finder.Finder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class Controller {
    Finder finder;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    Stage stage;

    public void setFinder(Finder finder) {
        this.finder = finder;
    }

    FileChooser fileChooser = new FileChooser();
    @FXML
    Text fileToBeAnalysed;

    @FXML
    Text attentionText;

    @FXML
    TextArea foundCNPText;

    @FXML
    Text okText;

    @FXML
    Button chooseAFileButton;
    @FXML
    Button analyseButton;

    File file;

    @FXML
    public void initialize() {
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        chooseAFileButton.setText("Choose a file...");

        chooseAFileButton.setOnAction(
                e -> chooseFile()
        );

        analyseButton.setOnAction(e->startAnalysing());

        attentionText.setVisible(false);
        foundCNPText.setVisible(false);
        okText.setVisible(false);
    }


    private void chooseFile() {
        var file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            fileToBeAnalysed.setText("Analysed file:\n" + file.getName());
            this.file = file;
        }
    }

    private void startAnalysing() {
        var result = finder.analyseFile(file);
        if (result == null) {
            attentionText.setVisible(false);
            foundCNPText.setVisible(false);
            okText.setVisible(true);
        } else {
            okText.setVisible(false);
            attentionText.setVisible(true);
            foundCNPText.setVisible(true);
            var text = "";
            for (var r : result) text += r + "\n";
            foundCNPText.setText(text);
        }
    }
}
