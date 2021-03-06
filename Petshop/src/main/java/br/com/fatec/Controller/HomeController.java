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
 * @author isaac
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    SceneController sceneController = new SceneController();
    @FXML
    private Button btnConsultar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
        
    }

    @FXML
    private void switchPage_cadastroPet(ActionEvent event) throws IOException {
        sceneController.switchToSceneCadastroPet(event);
    }

    @FXML
    private void switchPage_agendaServico(ActionEvent event) throws IOException{
        sceneController.switchToSceneAgendaConsulta(event);
    }

    @FXML
    private void switchPage_cadastroCliente(ActionEvent event) throws IOException{
        sceneController.switchToSceneCadastroCliente(event);
    }

    @FXML
    private void switchPage_cadastroFuncionario(ActionEvent event) throws IOException{
        sceneController.switchToSceneCadastroFuncionario(event);
    }

    @FXML
    private void switchPage_consultaTipos(ActionEvent event) throws IOException{
        sceneController.switchToSceneConsultaTipos(event);
    }

}
