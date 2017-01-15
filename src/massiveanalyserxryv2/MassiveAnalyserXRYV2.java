/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package massiveanalyserxryv2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.concurrent.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

/**
 *
 * @author Thonon
 */
public class MassiveAnalyserXRYV2 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();
        
        // lancement du logo
        FXMLLoader loaderLogo = new FXMLLoader(getClass().getResource("LogoStartView.fxml"));
        AnchorPane anchorLogo = loaderLogo.load();
        Scene sceneLogo = new Scene(anchorLogo);
        Stage stage = new Stage();
        stage.setScene(sceneLogo);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setIconified(false);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
        
        
        
         // récupération du controller du MainView
        MainViewController mvc = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Massive Analyser XRY");
        primaryStage.setIconified(false);
        primaryStage.show();
       
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
