/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Help;

import Core.ControllerBase;
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
public class HelpController extends ControllerBase{

    @FXML
    private AnchorPane anchore;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        super.initialize(location, resources); //To change body of generated methods, choose Tools | Templates.
        
        
    }

   @FXML
   public void handleCloseHelp()
   {
       // fermeture
       anchore.getScene().getWindow().hide();
   }
    
}
