/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.PetDAO;
import br.com.fatec.SceneController;
import br.com.fatec.model.Cliente;
import br.com.fatec.model.Pet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class ConsultasController implements Initializable {

    SceneController sceneController = new SceneController();

    PetDAO petDao = new PetDAO();
    ClienteDAO clienteDao = new ClienteDAO();

    Pet currentPet = new Pet();

    String argumentos = "";

    @FXML
    private Label lblConsulta;
    @FXML
    private TextField txtDados;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnEditar;
    @FXML
    private ComboBox<String> cmTipoPesquisa;
    @FXML
    private TableView<Pet> tbvPet;
    @FXML
    private TableColumn<Pet, String> nome;
    @FXML
    private TableColumn<Pet, String> genero;

    /**
     * Initializes the controller class.
     *
     */
    ObservableList<Pet> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregaPets("");
        carregaCmbOptions();
    }

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException {
        sceneController.switchToSceneHome(event);
    }

    private void carregaCmbOptions() {

        ObservableList<String> obsTipos = FXCollections.observableArrayList();
        obsTipos.add("Todos");
        obsTipos.add("id_pet");
        obsTipos.add("nome");
        obsTipos.add("categoria");
        obsTipos.add("raca");
        obsTipos.add("genero");
        obsTipos.add("id_dono");

        cmTipoPesquisa.setItems(obsTipos);

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void btnPesquisar_click(ActionEvent event) {
        if (cmTipoPesquisa.getValue() == "Todos") {
            argumentos = "";
            carregaPets(argumentos);
        } else {
            argumentos = "";
            argumentos = cmTipoPesquisa.getValue();
            argumentos += " = " + "'" + txtDados.getText() + "'";
            carregaPets(argumentos);
        }
    }

    @FXML
    private void txtDados_KeyReleased(KeyEvent event) {
        argumentos = "";
    }

    @FXML
    private void cmTipoPesquisa_selected(ActionEvent event) {
        argumentos = "";
    }

    @FXML
    private void btnDeletar_click(ActionEvent event) {

        Cliente cliente = new Cliente();
        cliente.setId(currentPet.getId_dono());

        try {
            cliente = clienteDao.buscaID(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Excluir");
        alerta.setHeaderText("Deseja Realmente deletar o PET:");
        alerta.setContentText("CPF do Dono: " + cliente.getCpf() + "\n\n" + "Nome do Pet: " + currentPet.getNome() + "\n" + "Gênero do Pet: " + currentPet.getGenero() + "\n" + "Raça do Pet: " + currentPet.getRaca());

        if (alerta.showAndWait().get() == ButtonType.OK) {
            try {
                petDao.remove(currentPet);
                Alert alerta3 = new Alert(Alert.AlertType.INFORMATION);
                alerta3.setTitle("Sucesso!");
                alerta3.setHeaderText("INFORMACOES");
                alerta3.setContentText("Dados deletados com Sucesso! ");
                alerta3.showAndWait();
                carregaPets("");
            } catch (SQLException ex) {
                Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                alerta2.setTitle("ERRO");
                alerta2.setHeaderText("INFORMACOES");
                alerta2.setContentText("Erro ao excluir: " + ex.getMessage());

                alerta2.showAndWait();
            }
        }

    }

    @FXML
    private void btnEditar_click(ActionEvent event) {
    }

    public void carregaPets(String args) {
        ObservableList<Pet> obsPets = FXCollections.observableArrayList();
        ArrayList<Pet> lista = new ArrayList<>();
        try {
            lista.addAll(petDao.lista(args));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro na consulta: " + ex.getMessage());
            alerta.showAndWait();
        }
        obsPets.addAll(lista);

        nome.setCellValueFactory(new PropertyValueFactory<Pet, String>("nome"));
        genero.setCellValueFactory(new PropertyValueFactory<Pet, String>("genero"));

        tbvPet.setItems(obsPets);

        tbvPet.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pet>() {

            @Override
            public void changed(ObservableValue<? extends Pet> ov, Pet t, Pet t1) {
                currentPet = (Pet) tbvPet.getSelectionModel().getSelectedItem();
                lblConsulta.setText(currentPet.getNome());
            }

        });
    }

}
