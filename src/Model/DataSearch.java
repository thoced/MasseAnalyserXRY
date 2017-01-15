/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;

/**
 *
 * @author Thonon
 */
public class DataSearch 
{
    private File file;
    
    private String nameSheet;
    
    private String  nameColumn;
    
    private String nameDb;
    
    private String abosoluthPathDb;
    
    private boolean beReady = false;

    public DataSearch() 
    {
        
    }

    public String getAbosoluthPathDb() {
        return abosoluthPathDb;
    }

    public void setAbosoluthPathDb(String abosoluthPathDb) {
        this.abosoluthPathDb = abosoluthPathDb;
    }
    
    

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getNameSheet() {
        return nameSheet;
    }

    public void setNameSheet(String nameSheet) {
        this.nameSheet = nameSheet;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

   
    public String getNameDb() {
        return nameDb;
    }

    public void setNameDb(String nameDb) {
        this.nameDb = nameDb;
    }

    public boolean isBeReady() {
        return beReady;
    }

    public void setBeReady(boolean beReady) {
        this.beReady = beReady;
    }
    
    

}
