/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package massiveanalyserxryv2;

import Import.SheetFrameController;
import Model.DataContent;
import Model.DataResultat;
import Model.DataSearch;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Thonon
 */
public class MainViewController implements Initializable
{
    public static DataSearch modelDataSearch;
    @FXML
    private AnchorPane anchor;
    @FXML
    private TableView tableauResultat;
  
 
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
       ((TableColumn)tableauResultat.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<DataResultat,Integer>("numRow"));
               
       ((TableColumn)tableauResultat.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<DataResultat,String>("content"));
               
       ((TableColumn)tableauResultat.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<DataResultat,Integer>("keyWord"));
               
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
    
    private void startSearch() throws FileNotFoundException, IOException, InvalidFormatException
    {
        // récupération de la liste des mots clés
        String path = System.getProperty("user.dir");
        path = path + "/db/";
        path = path + modelDataSearch.getNameDb();
        
        
        
        ArrayList<String> keyWords = new ArrayList<String>();
        keyWords.clear();
        // lecture 
        for(String line : Files.readAllLines(Paths.get(path)))
        {
            keyWords.add(line);
        }
        
        // récupération de la liste des contents du tableau excel
         Workbook book = WorkbookFactory.create(modelDataSearch.getFile());
        // récupération du sheet
         Sheet sheet = book.getSheet(modelDataSearch.getNameSheet());
         // récupération de la colonne
         int top = sheet.getFirstRowNum();
         int down = sheet.getLastRowNum();
         Row row = sheet.getRow(top);
         // on parse les column jusqu'a ce que le nom soit le meme que celui dans le modele
         short start = row.getFirstCellNum();
         short end = row.getLastCellNum();
         int indiceColumn = -1;
         for(short i=start ; i <= end ; i++)
         {
             if(row.getCell(i).getStringCellValue().equals(modelDataSearch.getNameColumn()))
             {
                 // on connait l'indice de column
                 indiceColumn = i;
                 break;
             }
         }
         
         // création de la liste des contents
         ArrayList<DataContent> listContent = new ArrayList<DataContent>();
         listContent.clear();
         for(int j=top;j<=down;j++)
         {
             if(sheet.getRow(j) != null)
             {
                if((sheet.getRow(j).getCell(indiceColumn).getCellType() == CellType.STRING.getCode()))
                {
                    DataContent data = new DataContent(j,sheet.getRow(j).getCell(indiceColumn).getStringCellValue());
                    listContent.add(data);
                }
             }
         }
     
         // création de l'observable list
         ObservableList<DataResultat> ob = FXCollections.observableArrayList();
         
         // recherches
         for(DataContent content : listContent)
         {
             for(String key : keyWords)
             {
                 if(key.isEmpty())
                     continue;
                 
                    int res = content.getContent().toLowerCase().indexOf(key.toLowerCase());
                    if(res != -1)
                    {
                        DataResultat data = new DataResultat(content.getNumRow() + 1,content.getContent(),key); // +1 car dans le fichie excel les row commence à 1 et pas à 0
                        ob.add(data);
                    }
             }
         }
         
         tableauResultat.setItems(ob);
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
                this.startSearch();
           }
        }
    }

  

   
   
    
   
    
    
}
