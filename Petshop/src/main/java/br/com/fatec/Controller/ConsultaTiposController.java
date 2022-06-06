/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.SceneController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author rafit
 */
public class ConsultaTiposController implements Initializable {
    
    SceneController sceneController = new SceneController();
    
    @FXML
    private Button btnConsultaFunc;
    @FXML
    private Button btnConsultaCli;
    @FXML
    private Button btnConsultaPet;
    @FXML
    private Button btnConsultaAgen;
    @FXML
    private Button btnVoltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnConsultaFunc_click(ActionEvent event) {
    }

    @FXML
    private void btnConsultaCli_click(ActionEvent event) {
    }

    @FXML
    private void btnConsultaPet_click(ActionEvent event) throws IOException{
        sceneController.switchToSceneConsultaDePet(event);
    }

    @FXML
    private void btnConsultaAgen_click(ActionEvent event) {
    }

    @FXML
    private void switchPage_home(ActionEvent event)throws IOException {
        sceneController.switchToSceneHome(event);
    }
    
}
