/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Db;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import static massiveanalyserxryv2.MainViewController.modelDataSearch;

/**
 *
 * @author Thonon
 */
public class SelectDbController implements Initializable
{
    @FXML
    private ListView listDb;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
      // chargement des db
       // obtention des noms de fichiers retrouvés dans le répertoire situé au même niveau que l'application
       String pathApp = System.getProperty("user.dir");
        // si le chemin existe, on regarde le répertoire db
       if(pathApp != null)
       {
           // listing du répertoire en question
             String[] dir = new java.io.File(pathApp + "/db/.").list();
             
             if(dir != null)
             {
                 ArrayList listDir = new ArrayList();
                 listDir.clear();
                              
                for(String f : dir)
                {
                    listDir.add(f);
                }
                
                ObservableList<String> ol = FXCollections.observableArrayList(listDir);
                listDb.setItems(ol);
             }

       }
    }
    
     @FXML
    public void handleCancel()
    {
        // Cancel et fermeture
        modelDataSearch.setBeReady(false);
        listDb.getScene().getWindow().hide();
    }
    
    @FXML
    public void handleCloseAndSearch()
    {
        // enregistrement dans le model du nom de la db
        if(!listDb.getSelectionModel().isEmpty())
        {
         modelDataSearch.setNameDb((String)listDb.getSelectionModel().getSelectedItem());
         modelDataSearch.setBeReady(true);
         // fermeture de la vue
         listDb.getScene().getWindow().hide();
        }
        else
        {
            modelDataSearch.setBeReady(false);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERREUR DB");
            alert.setHeaderText("SELECTION DE DB");
            alert.setContentText("Veuillez sélectionner une base de donnée de recherche");
            alert.showAndWait();

        }
            
    }
    
    
}
