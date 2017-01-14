/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package massiveanalyserxryv2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Thonon
 */
public class LogoStartController implements Initializable {

    @FXML
    private AnchorPane anchor;
    
    private int tempsLogo = 5000;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
      
        this.start();
    }
    
    public void start()
    {
        Service<Object> serv = new Service<Object>()
        {
            @Override
            protected void succeeded() 
            {

                super.succeeded(); //To change body of generated methods, choose Tools | Templates.
                anchor.getScene().getWindow().hide();
            }

            @Override
            protected Task createTask()
            {
                return new Task() 
                {
                    @Override
                    protected Object call() throws Exception 
                    {
                        Thread.sleep(tempsLogo);
                        return null;
                    }
                };
            }
        };
        
        serv.start();
           
    }
   
}

