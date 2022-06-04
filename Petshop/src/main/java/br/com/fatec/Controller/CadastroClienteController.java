/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.CepDAO;
import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.SceneController;
import br.com.fatec.TextFieldFormatter;
import br.com.fatec.model.Cep;
import br.com.fatec.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class CadastroClienteController implements Initializable {
    
    SceneController sceneController = new SceneController();
    
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtRg;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCep;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtUf;
    @FXML
    private TextField txtEndereco;
    @FXML
    private DatePicker dpDataNasc;
    @FXML
    private TextField txtTelefone;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtNumero;

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
        LocalDate data = dpDataNasc.getValue();
        String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        cliente.setCpf(txtCpf.getText());
        cliente.setRg(txtRg.getText());
        cliente.setNome(txtNome.getText());
        cliente.setDataNasc(dataPickerString);
        cliente.setCep(txtCep.getText());
        cliente.setEmail(txtEmail.getText());

        try {
            if (dao.insere(cliente)) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("SUCESSO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Dados gravados com SUCESSO!");

                alerta.showAndWait();

            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("ERRO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Erro ao gravar os Dados!");

                alerta.showAndWait();
            }

        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro na gravacao: " + ex.getMessage());

            alerta.showAndWait();

        }

    }

    @FXML
    private void txtCep_enter(ActionEvent event) {
        CepDAO cepDao = new CepDAO();
        Cep cep = new Cep();

        cep.setCep(txtCep.getText());

        try {
            cep = cepDao.buscaID(cep);
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro ao Buscar: " + ex.getMessage());
            alerta.showAndWait();
        }
        
        txtCep.setText(cep.getCep());
        txtEndereco.setText(cep.getEndereco());
        txtCidade.setText(cep.getCidade());
        txtBairro.setText(cep.getBairro());
        txtUf.setText(cep.getUf());
        
        
    }

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException{
        sceneController.switchToSceneHome(event);
    }

    @FXML
    private void txtRg_KeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##.###.###-#");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtRg);
        tff.formatter();
    }

    @FXML
    private void txtCpf_KeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCpf);
        tff.formatter();
    }

    @FXML
    private void txtCep_KeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("#####-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCep);
        tff.formatter();
    }

    @FXML
    private void txtTelefone_KeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("(##)#####-####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTelefone);
        tff.formatter();
    }

    @FXML
    private void btnCancelar_click(ActionEvent event) {
    }

}
