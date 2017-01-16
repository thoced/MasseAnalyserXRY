/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package massiveanalyserxryv2;

import Model.DataContent;
import Model.DataResultat;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import static massiveanalyserxryv2.MainViewController.modelDataSearch;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Thonon
 */
public class ServiceTaskSearch<Object> extends Service
{
    private ObservableList<DataResultat> ob;

    public ObservableList<DataResultat> getOb() {
        return ob;
    }

    public void setOb(ObservableList<DataResultat> ob) {
        this.ob = ob;
    }
       
    
    
    @Override
    protected Task createTask()
    {
       Task task;
        task = new Task() 
        {
            @Override
            protected Object call() throws Exception
            {
                 // récupération de la liste des mots clés
                // si il s'agit d'une base de donnée sélectionné dans la liste
                String path;
                if(modelDataSearch.getAbosoluthPathDb() == null)
                {
                    path = System.getProperty("user.dir");
                    path = path + "/db/";
                    path = path + modelDataSearch.getNameDb();
                }
                else
                {
                    // sinon on crée le path avec le chemin absolu (fichier importé)
                    path = modelDataSearch.getAbosoluthPathDb();
                }
                
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
                
                // Fermeture du workbook
                if(book != null)
                   book.close();
                //tableauResultat.setItems(ob);
                return (Object)ob;
                
            }
            
         
        };
       
       return task;
    }
    
    
   
    
}
