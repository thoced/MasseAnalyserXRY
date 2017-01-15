/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Thonon
 */
public class DataResultat
{
    private SimpleIntegerProperty numRow;
    
    private SimpleStringProperty content;
    
    private SimpleStringProperty keyWord;
    
    public DataResultat(int numRow,String content,String keyWord)
    {
        this.numRow = new SimpleIntegerProperty(numRow);
        
        this.content = new SimpleStringProperty(content);
        
        this.keyWord = new SimpleStringProperty(keyWord);
    }

    public int getNumRow() {
        return numRow.get();
    }

    public void setNumRow(int numRow) {
        this.numRow.set(numRow);
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getKeyWord() {
        return keyWord.get();
    }

    public void setKeyWord(String key) {
        this.keyWord.set(key);
    }

    @Override
    public String toString() {
        
        // utilisé pour l'impression
        return "Num ligne: " + numRow.get() + System.getProperty("line.separator") + "Contenu: " + content.get() + System.getProperty("line.separator") + System.getProperty("line.separator");
    }
    
    
}
