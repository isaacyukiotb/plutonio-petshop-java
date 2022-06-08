/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.PetDAO;
import br.com.fatec.SceneController;
import br.com.fatec.TextFieldFormatter;
import br.com.fatec.model.Cliente;
import br.com.fatec.model.Pet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    ObservableList<String> categoria = FXCollections.observableArrayList();

    PetDAO dao = new PetDAO();
    ClienteDAO clienteDao = new ClienteDAO();

    Pet petEdit = null;

    @FXML
    private TextField txtNome;
    @FXML
    private ComboBox<String> cmbCategoria;

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
    @FXML
    private TextField txtCpfDono;
    @FXML
    private Button btnCancelar;

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
    private void btnCadastrar_click(ActionEvent event) throws IOException {

        if ("".equals(txtCpfDono.getText()) || "".equals(txtNome.getText()) || cmbCategoria.getValue() == null || "".equals(txtRaca.getText()) || (ckFeminino.isSelected() == false && ckMasculino.isSelected() == false)) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("PREENCHA TODOS OS CAMPOS");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Preencha Todos os campos!");
            alerta.showAndWait();
        } else {

            if (petEdit == null) {

                Pet pet = new Pet();
                Cliente dono = new Cliente();
                dono.setCpf(txtCpfDono.getText());

                try {
                    dono = clienteDao.buscaCPF(dono);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    //Logger.getLogger(CadastroPetController.class.getName()).log(Level.SEVERE, null, ex);
                }

                pet.setNome(txtNome.getText());
                pet.setCategoria(cmbCategoria.getValue());
                pet.setRaca(txtRaca.getText());
                pet.setGenero(this.sexo);
                pet.setRestricao(txtRestricao.getText());
                pet.setId_dono(dono.getId());

                try {
                    if (dao.insere(pet)) {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("SUCESSO");
                        alerta.setHeaderText("INFORMACOES");
                        alerta.setContentText("Dados gravados com SUCESSO!");

                        alerta.showAndWait();
                        limparTela();

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
            } else {

                Cliente dono = new Cliente();
                dono.setCpf(txtCpfDono.getText());

                try {
                    dono = clienteDao.buscaCPF(dono);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    //Logger.getLogger(CadastroPetController.class.getName()).log(Level.SEVERE, null, ex);
                }

                petEdit.setNome(txtNome.getText());
                petEdit.setCategoria(cmbCategoria.getValue());
                petEdit.setRaca(txtRaca.getText());
                petEdit.setGenero(this.sexo);
                petEdit.setRestricao(txtRestricao.getText());
                petEdit.setId_dono(dono.getId());

                try {
                    if (dao.altera(petEdit)) {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("SUCESSO");
                        alerta.setHeaderText("INFORMACOES");
                        alerta.setContentText("Dados Alterados com SUCESSO!");

                        alerta.showAndWait();
                        limparTela();
                        sceneController.switchToSceneConsultaDePet(event);

                    } else {
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("ERRO");
                        alerta.setHeaderText("INFORMACOES");
                        alerta.setContentText("Erro ao Alterar os Dados!");

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
    }

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException {
        sceneController.switchToSceneHome(event);

    }

    @FXML
    private void txtCpfDono_KeyRealased(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCpfDono);
        tff.formatter();
    }

    public void onBtnEditar_click(Pet pet) {

        Cliente cliente = new Cliente();
        cliente.setId(pet.getId_dono());

        try {
            cliente = clienteDao.buscaID(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroPetController.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtCpfDono.setText(cliente.getCpf());
        txtNome.setText(pet.getNome());
        txtRaca.setText(pet.getRaca());
        txtRestricao.setText(pet.getRestricao());
        cmbCategoria.setValue(pet.getCategoria());

        if ("M".equals(pet.getGenero())) {
            ckMasculino.setSelected(true);
            this.sexo = pet.getGenero();
        } else {
            ckFeminino.setSelected(true);
            this.sexo = pet.getGenero();
        }

        btnCadastro.setText("Atualizar");

        petEdit = pet;
    }

    public void limparTela() {
        txtCpfDono.setText("");
        txtNome.setText("");
        cmbCategoria.setValue(null);
        txtRaca.setText("");
        ckFeminino.setSelected(false);
        ckMasculino.setSelected(false);
        txtRestricao.setText("");

        petEdit = null;
    }

    @FXML
    private void btnCancelar_click(ActionEvent event) throws IOException {

        if (petEdit != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Cancelar");
            alerta.setHeaderText("Deseja Realmente Cancelar a operação?");

            if (alerta.showAndWait().get() == ButtonType.OK) {
                sceneController.switchToSceneConsultaDePet(event);
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Cancelar");
            alerta.setHeaderText("Deseja Realmente Cancelar a operação?");

            if (alerta.showAndWait().get() == ButtonType.OK) {
                limparTela();
            }
        }

        limparTela();
    }

}
