/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Import;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Thonon
 */
public class ImportController implements Initializable
{
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button bImport;
    
    private File file;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
     
    }
    
      @FXML
      public void handleWizardNext(ActionEvent event) throws IOException
      {
          // on ferme le stage current
          Stage currentStage = (Stage)anchor.getScene().getWindow();
          // on charge le suivant
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/Import/SheetColumnView.fxml"));
          AnchorPane pane = loader.load();
          // creation de la scene
          Scene scene = new Scene(pane);
          // creation du stage
          //Stage stage = new Stage();
         // stage.setScene(scene);
          //stage.initModality(Modality.APPLICATION_MODAL);
          //stage.showAndWait();
          currentStage.setScene(scene);
          
      }
      
      @FXML
      public void openFile(ActionEvent event) throws IOException
      {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choisissez le fichier à importer");
        file = fc.showOpenDialog(anchor.getScene().getWindow());
        if(file != null)
        {
            // si un fichier est sélectionné, on enable le bouton import
            bImport.setDisable(false);

        }
          
      }
      
    
}
