/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package massiveanalyserxryv2;

import Import.SheetFrameController;
import Model.DataResultat;
import Model.DataSearch;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


/**
 *
 * @author Thonon
 */
public class MainViewController implements Initializable, EventHandler
{
    public static DataSearch modelDataSearch;
    @FXML
    private AnchorPane anchor;
    @FXML
    private TableView tableauResultat;
    
    private  ServiceTaskSearch search;
  
 
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
       ((TableColumn)tableauResultat.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<DataResultat,Integer>("numRow"));
               
       ((TableColumn)tableauResultat.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<DataResultat,String>("content"));
               
       ((TableColumn)tableauResultat.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<DataResultat,Integer>("keyWord"));
               
    }
    
    @FXML
    public void handleCloseApp(ActionEvent event)
    {
        // fermeture de l'application
        anchor.getScene().getWindow().hide();
       
    }
    
    @FXML
    public void handleOpenHelp(ActionEvent event) throws IOException
    {
        // chargement du de la vue
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Help/HelpView.fxml"));
        // chargement 
        AnchorPane pane = loader.load();
       
        // création de la scene
        Scene scene = new Scene(pane);
        // ajout dans le parentstage
        // save la currentScene
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }
    
    
    
    @FXML
    public void handleOpenWizardImport(ActionEvent event) throws IOException, FileNotFoundException, InvalidFormatException
    {
        modelDataSearch = new DataSearch();
        
             // ouverture du systeme d'ouverture de fichier
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier Excel (XLS)", "*.xls"),
                new FileChooser.ExtensionFilter("Fichier Excel (XLSX)", "*.xlsx"),
                new FileChooser.ExtensionFilter("Fichier Excel (XLTX)",".*.xltx"));
        fc.setTitle("Choisissez le fichier à importer");
            
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        
        modelDataSearch.setFile(fc.showOpenDialog(anchor.getScene().getWindow()));
       

        if(modelDataSearch.getFile() != null)
        {
            // chargement de la vue sheet
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/Import/SheetColumnView.fxml"));
            // set du File
              AnchorPane pane = loader.load();
              
              SheetFrameController sfc = loader.getController();
    
            // lancement du wizard d'importation
              Scene scene = new Scene(pane);
              Stage stage = new Stage();
              stage.setScene(scene);
            // titre de la vue
              stage.setTitle("Wizard d'importation et recherche");
              stage.showAndWait();
              
           // fin du wizard, on regarde si le model est rempli et prêt
           if(modelDataSearch.isBeReady())
           {
 
                // effacement du tableau
                tableauResultat.getItems().clear();
                // lancement de la recherche
                search = new ServiceTaskSearch();
                search.addEventHandler(EventType.ROOT, this);
               
                // création de l'observable list
                ObservableList<DataResultat> ob = FXCollections.observableArrayList();
                tableauResultat.setItems(ob);
                // set de l'observable list au service pour mise à jour
                search.setOb(ob);
                search.start();
           }
        }
    }

    @Override
    public void handle(Event event) 
    {
        if(event.getSource() == search)
        {
                    
           if(search.getState() == Worker.State.SUCCEEDED)
           {
               // le thread de recherche à terminé et a retourné un code succeeded
               // on récupère la valeur observable list
               System.out.println("SUCCED");
               tableauResultat.refresh();
          }
        }
    }

  
}
