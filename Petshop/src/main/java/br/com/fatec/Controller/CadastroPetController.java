/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.PetDAO;
import br.com.fatec.SceneController;
import br.com.fatec.TextFieldFormatter;
import br.com.fatec.model.Pet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class CadastroPetController implements Initializable {
    
    
    SceneController sceneController = new SceneController();
    
    @FXML
    private TextField txtNome;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private TextField txtIdDono;
    @FXML
    private CheckBox ckMasculino;
    @FXML
    private CheckBox ckFeminino;
    @FXML
    private TextField txtRaca;
    @FXML
    private TextArea txtRestricao;
    @FXML
    private Button btnCadastro;

    private String sexo;

    
    ObservableList<String> categoria = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoria.add("Anfibios");
        categoria.add("Aves");
        categoria.add("Mamiferos");
        categoria.add("Peixes");
        categoria.add("Repteis");

        cmbCategoria.setItems(categoria);

    }

    @FXML
    private void ckSetMasculino(ActionEvent event) {
        if (ckMasculino.isSelected()) {
            this.sexo = "M";
            ckFeminino.setSelected(false);
        }
    }

    @FXML
    private void ckSetFeminino(ActionEvent event) {
        if (ckFeminino.isSelected()) {
            this.sexo = "F";
            ckMasculino.setSelected(false);
        }
    }

    @FXML
    private void btnCadastrar_click(ActionEvent event) {
        Pet pet = new Pet();
        PetDAO dao = new PetDAO();

        pet.setNome(txtNome.getText());
        pet.setCategoria(cmbCategoria.getValue());
        pet.setRaca(txtRaca.getText());
        pet.setGenero(this.sexo);
        pet.setRestricao(txtRestricao.getText());
        pet.setId_dono(Integer.parseInt(txtIdDono.getText()));
        
        try{
            if(dao.insere(pet)){
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

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException{
        sceneController.switchToSceneHome(event);
        
    }

    @FXML
    private void txtCpfDono_KeyRealased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtIdDono);
        tff.formatter();
    }

}
