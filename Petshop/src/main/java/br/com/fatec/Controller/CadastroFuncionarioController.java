/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.CepDAO;
import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.SceneController;
import br.com.fatec.TextFieldFormatter;
import br.com.fatec.model.Cep;
import br.com.fatec.model.Funcionario;
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
public class CadastroFuncionarioController implements Initializable {

    FuncionarioDAO dao = new FuncionarioDAO();
    SceneController sceneController = new SceneController();

    Funcionario funcionarioEdit = null;

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
    private TextField txtBairro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtUf;
    @FXML
    private TextField txtEndereco;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtCargo;
    @FXML
    private TextField txtTelefone;
    @FXML
    private Button btnCancelar;
    @FXML
    private DatePicker dpDataNasc;
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
    private void txtCep_KeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("#####-###");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCep);
        tff.formatter();

    }

    @FXML
    private void btnCadastrar_click(ActionEvent event) {

        if (funcionarioEdit == null) {
            LocalDate data = dpDataNasc.getValue();
            String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Funcionario novoFuncionario = new Funcionario(FuncionarioDAO.idCount++, txtNome.getText(), txtCargo.getText(), txtTelefone.getText(), txtEmail.getText(), txtCpf.getText(), txtRg.getText(), dataPickerString, txtCep.getText(), txtEndereco.getText(), txtBairro.getText(), txtCidade.getText(), txtUf.getText(), txtNumero.getText());

            try {
                dao.insere(novoFuncionario);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("SUCESSO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Dados gravados com SUCESSO!");
                alerta.showAndWait();
                limparCampos();

            } catch (Exception ex) {
                Logger.getLogger(CadastroFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            
            LocalDate data = dpDataNasc.getValue();
            String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            funcionarioEdit.setNome(txtNome.getText());
            funcionarioEdit.setCargo(txtCargo.getText());
            funcionarioEdit.setTelefone(txtTelefone.getText());
            funcionarioEdit.setEmail(txtEmail.getText());
            funcionarioEdit.setCpf(txtCpf.getText());
            funcionarioEdit.setRg(txtRg.getText());
            funcionarioEdit.setData_nasc(dataPickerString);
            funcionarioEdit.setCep(txtCep.getText());
            funcionarioEdit.setEndereco(txtEndereco.getText());
            funcionarioEdit.setBairro(txtBairro.getText());
            funcionarioEdit.setCidade(txtCidade.getText());
            funcionarioEdit.setUf(txtUf.getText());
            funcionarioEdit.setNumero(txtNumero.getText());

            try {
                dao.altera(funcionarioEdit);
            } catch (Exception ex) {
                Logger.getLogger(CadastroFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException {
        sceneController.switchToSceneHome(event);
    }

    @FXML
    private void btnCancelar_click(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cancelar");
        alerta.setHeaderText("Deseja Realmente Cancelar a operação?");

        if (alerta.showAndWait().get() == ButtonType.OK) {
            limparCampos();
        }

    }

    @FXML
    private void txtTelefone_KeyReleased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("(##)#####-####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTelefone);
        tff.formatter();
    }

    public void limparCampos() {
        txtBairro.setText("");
        txtCargo.setText("");
        txtCep.setText("");
        txtCidade.setText("");
        txtCpf.setText("");
        dpDataNasc.setValue(null);
        txtEmail.setText("");
        txtEndereco.setText("");
        txtNome.setText("");
        txtRg.setText("");
        txtTelefone.setText("");
        txtUf.setText("");
    }

    public void onBtnEditar_click(Funcionario func) {

        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(func.getId_funcionario());

        try {
            funcionario = dao.buscaID(funcionario);
        } catch (Exception ex) {
            Logger.getLogger(CadastroPetController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        String date = funcionario.getData_nasc();
        LocalDate localDate = LocalDate.parse(date, formatter);

        txtNome.setText(funcionario.getNome());
        txtRg.setText(funcionario.getRg());
        txtTelefone.setText(funcionario.getTelefone());
        txtCpf.setText(funcionario.getCpf());
        dpDataNasc.setValue(localDate);
        txtEmail.setText(funcionario.getEmail());
        txtCargo.setText(funcionario.getCargo());
        txtCep.setText(funcionario.getCep());
        txtEndereco.setText(funcionario.getEndereco());
        txtCidade.setText(funcionario.getCidade());
        txtBairro.setText(funcionario.getBairro());
        txtUf.setText(funcionario.getUf());
        txtNumero.setText(funcionario.getNumero());

        btnCadastrar.setText("Atualizar");
        funcionarioEdit = funcionario;

    }

}
