/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.PetDAO;
import br.com.fatec.model.Cliente;
import br.com.fatec.model.Pet;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class AgendaConsultaController implements Initializable {

    @FXML
    private TextField txtCpfDono;
    @FXML
    private DatePicker dpData;
    @FXML
    private Label lblPreco;
    @FXML
    private Button btnCadastrar;
    @FXML
    private ComboBox<Pet> cmbPet;
    @FXML
    private ComboBox<String> cmbServico;
    @FXML
    private ComboBox<String> cmbHorario;

    @FXML
    private TextArea txtObservacao;
    @FXML
    private ComboBox<String> cmbFuncionario;
    
    //Lists
    ObservableList<Pet> pet = FXCollections.observableArrayList();
    
    ObservableList<String> horario = FXCollections.observableArrayList();
    ObservableList<String> funcionario = FXCollections.observableArrayList();
    ObservableList<String> servico = FXCollections.observableArrayList();
    
    //DAOS
    Cliente cliente = new Cliente();
    ClienteDAO clienteDao = new ClienteDAO();
    PetDAO petDao = new PetDAO();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //PetDAO petdao = new PetDAO();
        setCmbHorario();
        setCmbFuncionario();
        setCmbServico();

    }
    
    

    @FXML
    private void btnCadastrar_click(ActionEvent event) {
    }

    private void setCmbServico() {
        servico.add("Banho");
        servico.add("Tosa");
        servico.add("Banho e Tosa");
        servico.add("Tosa Higienica");
        cmbServico.setItems(servico);
    }

    private void setCmbFuncionario() {
        funcionario.add("Marcio");
        funcionario.add("Mario");
        funcionario.add("Flavio");
        funcionario.add("Maria");
        funcionario.add("Julia");

        cmbFuncionario.setItems(funcionario);
    }

    private void setCmbHorario() {
        horario.add("9:00");
        horario.add("10:00");
        horario.add("11:00");
        horario.add("12:00");
        horario.add("13:00");
        horario.add("14:00");
        horario.add("15:00");
        horario.add("16:00");

        cmbHorario.setItems(horario);
    }


    @FXML
    private void txtCpfDono_focus(ActionEvent event) {
        
        
        cliente.setCpf(txtCpfDono.getText());
        
        try {
            
            cliente = clienteDao.buscaCPF(cliente);
            pet = petDao.buscaDono(cliente);
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro ao gravar os Dados!" + ex.getMessage());

            alerta.showAndWait();
        }
        
        cmbPet.setItems(pet);
        txtObservacao.setText(cliente.getEmail());
    }
    

}
