/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Thonon
 */
public class DataContent 
{
    private int numRow;
    
    private String content;

    public DataContent(int num,String content)
    {
        this.numRow = num;
        this.content = content;
    }
    
    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
    
    
    
    
    
}
