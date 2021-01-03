package tugraz.digitallibraries.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable
{
    @FXML
    Button about_close_button_;

    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    void aboutCloseClicked(ActionEvent event)
    {
        Stage stage = (Stage) about_close_button_.getScene().getWindow();
        stage.close();
    }
}
