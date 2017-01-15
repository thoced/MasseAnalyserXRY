/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Import;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static massiveanalyserxryv2.MainViewController.modelDataSearch;
import org.apache.poi.EncryptedDocumentException;
import static org.apache.poi.hssf.usermodel.HSSFChart.HSSFChartType.Line;
import static org.apache.poi.hssf.usermodel.HSSFShapeTypes.Line;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.Line;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class SheetFrameController implements Initializable, EventHandler {

    @FXML
    private ListView listSheet;
    @FXML
    private ListView listColumn;
    @FXML
    private Button bSuiv;
    @FXML
    private Button bCancel;
    
    private Workbook book;
    
  

    public SheetFrameController() 
    {
        
    }
    
    private void updateListSheet()
    {
        // initalisation du listSheet
        ArrayList l = new ArrayList();
        l.clear();
       
        // chargement du fichier excel
        if(modelDataSearch.getFile() != null)
        {
            try 
            {
                book = WorkbookFactory.create(modelDataSearch.getFile());
                // récupération du nombre de sheet
                int nbSheet = book.getNumberOfSheets();
                // récupération des sheets
                for(int i=0;i<nbSheet;i++)
                {
                    Sheet sh = book.getSheetAt(i);
                    l.add(sh.getSheetName());
                }
                
                
            } catch (FileNotFoundException ex) 
            {
                Logger.getLogger(SheetFrameController.class.getName()).log(Level.SEVERE, null, ex);
                this.alertException(ex.getMessage());
            } catch (IOException ex) {
                this.alertException(ex.getMessage());
                Logger.getLogger(SheetFrameController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidFormatException ex) {
                this.alertException(ex.getMessage());
                Logger.getLogger(SheetFrameController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncryptedDocumentException ex) {
                this.alertException(ex.getMessage());
                Logger.getLogger(SheetFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
       
        ObservableList<String> ol = FXCollections.observableArrayList(l);
        
        listSheet.setItems(ol);
    }
    
    private void alertException(String message)
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Exception Warning");
        alert.setHeaderText("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       // ajout d'un listener d'évenemnt sur le listSheet
        listSheet.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

        // appel du updateListSheet
        this.updateListSheet();
    }  

    
    @Override
    public void handle(Event event)
    {

      if(event.getSource() == listSheet)
      {
        
        if(/*listSheet.getSelectionModel().getSelectedIndex() > -1*/ listSheet.getSelectionModel().getSelectedItem() != null )
        {
            // clear de la liste des columns
            listColumn.getSelectionModel().clearSelection();
            // update de la liste des colonnes
            // récupération du nom du sheet sélectionné
            String sheetName = (String)listSheet.getSelectionModel().getSelectedItem();
            // récupération du sheet
            Sheet sheet = book.getSheet(sheetName);
            // récupération des colonnes du sheet
            int top = sheet.getFirstRowNum();
            Row row = sheet.getRow(top);
            // récupération du nombre de cellule dans la row
            short first = row.getFirstCellNum();
            short last = row.getLastCellNum();
            // on parse la premiure row entre le first et le last
            // création du arraylist
            ArrayList al = new ArrayList();
            al.clear();
            
            // boolean exeption
            boolean catchException = false;
            
            for(int i=first ; i < last ; i++)
            {
                Cell cell = row.getCell(i);
                // on récupère le nom de la cellule
                try
                {
                if(cell.getCellType() == CellType.STRING.getCode())
                {                   
                                      
                    String value = cell.getStringCellValue();
                    // on ajoute la valeur dans le arraylist
                    al.add(value);
                }
                }
                catch(java.lang.NullPointerException nle)
                {
                    catchException = true;
                }
            }
            
            if(catchException)
                 this.alertException("Un probleme est survenu dans la lecture d'une ou plusieurs cellules du fichier");
            
            // on transverse le arraylist dans le observable list
            ObservableList<String> ol = FXCollections.observableArrayList(al);
            // on attache le ol dans le listColumn
            listColumn.setItems(ol);
            
            
        }
        
      
      }
      
      if(event.getSource() == listColumn)
      {
          if(listColumn.getSelectionModel().getSelectedIndex() > -1)
          {
              // un item est sélectionné dans la liste, on enable le bouton suivant
            bSuiv.setDisable(false);
          }
        else
            bSuiv.setDisable(true);
       }
    }
    
    @FXML
    public void OnCancel(ActionEvent e)
    {
        // fermeture de la fenetre
        ((Stage)bCancel.getScene().getWindow()).close();
    }
    
    @FXML
    public void OnSuivant(ActionEvent e) throws IOException
    {
        // inscription dans le model du nom du sheet et du nom de la colonne
        if(!listSheet.getSelectionModel().isEmpty())
            modelDataSearch.setNameSheet((String)listSheet.getSelectionModel().getSelectedItem());
        if(!listColumn.getSelectionModel().isEmpty())
            modelDataSearch.setNameColumn((String)listColumn.getSelectionModel().getSelectedItem());
        
        if(modelDataSearch.getNameSheet() != null && modelDataSearch.getNameColumn() != null)
        {
            // on cache la frame précédente
           // bSuiv.getScene().getWindow().hide();
            // ouverture du frame de sélection de la bd de recherche
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Db/SelectDbView.fxml"));
            // creation de la scene
            AnchorPane anchor = loader.load(); 
            Scene scene = new Scene(anchor);
            // creation du Stage
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Wizard d'importation et recherche");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Ressources/icon.png")));
            stage.showAndWait();
            // fermeture de la vue sheet
            listSheet.getScene().getWindow().hide();
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de sélection");
            alert.setHeaderText("SELECTION D'ONGLET ET DE COLONNE");
            alert.setContentText("Aucun onglet et/ou colonne n'a été sélectionné");
            alert.showAndWait();
            // on quitte le tout sans recherche
            listSheet.getScene().getWindow().hide();
        }
        
        
    }
    
   
    
}
