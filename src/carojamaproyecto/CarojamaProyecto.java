/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carojamaproyecto;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 *
 * @author WIN 10
 */
public class CarojamaProyecto extends Application {
    
    @Override
     public void start(Stage stage) throws Exception {
        
        Parent root = new Principal();
        Scene scene = new Scene(root, 1080, 550);
        stage.setScene(scene);
       
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();

      
    }
    
}
