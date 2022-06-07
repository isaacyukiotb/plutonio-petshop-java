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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class CadastroClienteController implements Initializable {

    SceneController sceneController = new SceneController();

    Cliente clienteEdit = null;

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
    private void btnCadastrar_click(ActionEvent event) throws IOException {

        ClienteDAO dao = new ClienteDAO();

        if (clienteEdit != null) {
            LocalDate data = dpDataNasc.getValue();
            String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            clienteEdit.setCpf(txtCpf.getText());
            clienteEdit.setRg(txtRg.getText());
            clienteEdit.setNome(txtNome.getText());
            clienteEdit.setDataNasc(dataPickerString);
            clienteEdit.setCep(txtCep.getText());
            clienteEdit.setEmail(txtEmail.getText());
            clienteEdit.setTelefone(txtTelefone.getText());
            clienteEdit.setEndereco(txtEndereco.getText());
            clienteEdit.setCidade(txtCidade.getText());
            clienteEdit.setBairro(txtBairro.getText());
            clienteEdit.setUf(txtUf.getText());
            clienteEdit.setNumero(txtNumero.getText());

            try {
                dao.altera(clienteEdit);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("SUCESSO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Dados Alterados com SUCESSO!");
                alerta.showAndWait();
                limparCampos();
                clienteEdit = null;
                sceneController.switchToSceneConsultaDeClientes(event);
            } catch (SQLException ex) {
                Logger.getLogger(CadastroClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Cliente cliente = new Cliente();
            LocalDate data = dpDataNasc.getValue();
            String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            cliente.setCpf(txtCpf.getText());
            cliente.setRg(txtRg.getText());
            cliente.setNome(txtNome.getText());
            cliente.setDataNasc(dataPickerString);
            cliente.setCep(txtCep.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setTelefone(txtTelefone.getText());
            cliente.setEndereco(txtEndereco.getText());
            cliente.setCidade(txtCidade.getText());
            cliente.setBairro(txtBairro.getText());
            cliente.setUf(txtUf.getText());
            cliente.setNumero(txtNumero.getText());
            
            try {

                if (dao.insere(cliente)) {

                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("SUCESSO");
                    alerta.setHeaderText("INFORMACOES");
                    alerta.setContentText("Dados gravados com SUCESSO!");

                    alerta.showAndWait();
                    limparCampos();

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
    private void switchPage_home(ActionEvent event) throws IOException {
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
    private void btnCancelar_click(ActionEvent event) throws IOException {
        if (clienteEdit != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Cancelar");
            alerta.setHeaderText("Deseja Realmente Cancelar a operação?");

            if (alerta.showAndWait().get() == ButtonType.OK) {
                sceneController.switchToSceneConsultaDeClientes(event);
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Cancelar");
            alerta.setHeaderText("Deseja Realmente Cancelar a operação?");

            if (alerta.showAndWait().get() == ButtonType.OK) {
                limparCampos();
            }
        }

    }

    public void onBtnEditar_click(Cliente cliente) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = cliente.getDataNasc();
        LocalDate localDate = LocalDate.parse(date, formatter);

        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        txtRg.setText(cliente.getRg());
        dpDataNasc.setValue(localDate);
        txtCep.setText(cliente.getCep());
        txtEmail.setText(cliente.getEmail());
        txtTelefone.setText(cliente.getTelefone());
        txtEndereco.setText(cliente.getEndereco());
        txtCidade.setText(cliente.getCidade());
        txtBairro.setText(cliente.getBairro());
        txtUf.setText(cliente.getUf());
        txtNumero.setText(cliente.getNumero());
            

        clienteEdit = cliente;

        btnCadastrar.setText("Atualizar");
    }

    private void limparCampos() {

        txtNome.setText("");
        txtRg.setText("");
        txtTelefone.setText("");
        txtCpf.setText("");
        dpDataNasc.setValue(null);
        txtEmail.setText("");
        txtCep.setText("");
        txtEndereco.setText("");
        txtCidade.setText("");
        txtBairro.setText("");
        txtUf.setText("");
        txtNumero.setText("");
        
        clienteEdit = null;

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
