/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.model.Cliente;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class CadastroClienteController implements Initializable {
    
    @FXML
    private ImageView imgBuscar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtRg;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtDataNasc;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCep;
    @FXML
    private Button btnCadastrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    @FXML
    private void btnCadastrar_click(ActionEvent event) {
        Cliente cliente = new Cliente();
        ClienteDAO dao = new ClienteDAO();
        
        cliente.setCpf(txtCpf.getText());
        cliente.setRg(txtRg.getText());
        cliente.setNome(txtNome.getText());
        cliente.setDataNasc(txtDataNasc.getText());
        cliente.setCep(txtCep.getText());
        cliente.setEmail(txtEmail.getText());
        
        try{
            if(dao.insere(cliente)){
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("SUCESSO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Dados gravados com SUCESSO!");
                
                alerta.showAndWait();
         
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("ERRO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Erro ao gravar os Dados!");
                
                alerta.showAndWait();
            }
            
        }catch(SQLException ex){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("ERRO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Erro na gravacao: " + ex.getMessage());
                
                alerta.showAndWait();
            
        }
             
    }
    
   
    
}
