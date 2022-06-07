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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class ConsultasDeClientesController implements Initializable {

    SceneController sceneController = new SceneController();

    PetDAO petDao = new PetDAO();
    ClienteDAO clienteDao = new ClienteDAO();

    Cliente currentCliente = new Cliente();

    String argumentos = "";

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
    private TableView<Cliente> tbvPet;
    @FXML
    private TableColumn<Cliente, String> nome;
    @FXML
    private TableColumn<Cliente, String> genero;

    /**
     * Initializes the controller class.
     *
     */
    ObservableList<Pet> list = FXCollections.observableArrayList();
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregaClientes("");
        carregaCmbOptions();
    }

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException {
        sceneController.switchToSceneConsultaTipos(event);
    }

    private void carregaCmbOptions() {

        ObservableList<String> obsTipos = FXCollections.observableArrayList();
        obsTipos.add("Todos");
        obsTipos.add("id_cliente");
        obsTipos.add("cpf");
        obsTipos.add("rg");
        obsTipos.add("nome");
        obsTipos.add("data_nasc");
        obsTipos.add("email");
        obsTipos.add("cep");

        cmTipoPesquisa.setItems(obsTipos);

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void btnPesquisar_click(ActionEvent event) {
        if (cmTipoPesquisa.getValue() == "Todos") {
            argumentos = "";
            carregaClientes(argumentos);
        } else {
            argumentos = "";
            argumentos = cmTipoPesquisa.getValue();
            argumentos += " = " + "'" + txtDados.getText() + "'";
            carregaClientes(argumentos);
        }
    }

    @FXML
    private void txtDados_KeyReleased(KeyEvent event) {
        argumentos = "";
    }

    @FXML
    private void cmTipoPesquisa_selected(ActionEvent event) {

        txtDados.setText("");
        argumentos = "";

    }

    @FXML
    private void btnDeletar_click(ActionEvent event) {
        /*
        Cliente cliente = new Cliente();
        cliente.setId(currentCliente.getId_dono());

        try {
            cliente = clienteDao.buscaID(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasDePetsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Excluir");
        alerta.setHeaderText("Deseja Realmente deletar o PET:");
        alerta.setContentText("CPF do Dono: " + cliente.getCpf() + "\n\n" + "Nome do Pet: " + currentPet.getNome() + "\n" + "Gênero do Pet: " + currentPet.getGenero() + "\n" + "Raça do Pet: " + currentPet.getRaca());

        if (alerta.showAndWait().get() == ButtonType.OK) {
            try {
                petDao.remove(currentCliente);
                Alert alerta3 = new Alert(Alert.AlertType.INFORMATION);
                alerta3.setTitle("Sucesso!");
                alerta3.setHeaderText("INFORMACOES");
                alerta3.setContentText("Dados deletados com Sucesso! ");
                alerta3.showAndWait();
                carregaClientes("");
            } catch (SQLException ex) {
                Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                alerta2.setTitle("ERRO");
                alerta2.setHeaderText("INFORMACOES");
                alerta2.setContentText("Erro ao excluir: " + ex.getMessage());

                alerta2.showAndWait();
            }
        }
         */

    }

    @FXML
    private void btnEditar_click(ActionEvent event) throws IOException {
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../cadastroCliente.fxml"));
        root = loader.load();
        
        CadastroClienteController cadastroClienteController = loader.getController();
        
        cadastroClienteController.onBtnEditar_click(currentCliente);
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage.setTitle("Cadastro de Pets");
        //stage.setResizable(false);
        //stage.centerOnScreen();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    public void carregaClientes(String args) {
        ObservableList<Cliente> obsCliente = FXCollections.observableArrayList();
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            lista.addAll(clienteDao.lista(args));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro na consulta: " + ex.getMessage());
            alerta.showAndWait();
        }
        obsCliente.addAll(lista);

        nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        genero.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));

        tbvPet.setItems(obsCliente);

        tbvPet.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cliente>() {

            @Override
            public void changed(ObservableValue<? extends Cliente> ov, Cliente t, Cliente t1) {
                currentCliente = (Cliente) tbvPet.getSelectionModel().getSelectedItem();
            }

        });
    }

}
