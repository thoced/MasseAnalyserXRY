/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Help;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class HelpController implements Initializable{
        

    @FXML
    private AnchorPane anchore;
    
    

   @FXML
   public void handleCloseHelp()
   {
       // fermeture
       anchore.getScene().getWindow().hide();
   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
