package controller;

import finder.Finder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Controller {
    private Finder finder;
    private Stage stage;
    private FileChooser fileChooser;
    private File file;

    @FXML private Text fileToBeAnalysed;
    @FXML private Text attentionText;
    @FXML private TextArea foundCNPText;
    @FXML private Text okText;
    @FXML private Button chooseAFileButton;
    @FXML private Button analyseButton;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setFinder(Finder finder) {
        this.finder = finder;
    }

    @FXML
    public void initialize() {
        fileChooser=new FileChooser();

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
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            fileToBeAnalysed.setText("Analysed file:\n" + file.getName());
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
