/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.AgendaDAO;
import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.PetDAO;
import br.com.fatec.SceneController;
import br.com.fatec.model.Agenda;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class ConsultasDePetsController implements Initializable {

    SceneController sceneController = new SceneController();
    AgendaDAO agendaDao = new AgendaDAO();
    PetDAO petDao = new PetDAO();
    ClienteDAO clienteDao = new ClienteDAO();

    Pet currentPet = new Pet();

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
    private TableView<Pet> tbvPet;
    @FXML
    private TableColumn<Pet, String> nome;
    @FXML
    private TableColumn<Pet, String> genero;
    @FXML
    private TableColumn<Pet, String> raca;
    @FXML
    private TableColumn<Pet, String> cpf_dono;

    private Stage stage;
    private Scene scene;
    private Parent root;

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
        sceneController.switchToSceneConsultaTipos(event);
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

        txtDados.setText("");
        argumentos = "";

    }

    @FXML
    private void btnDeletar_click(ActionEvent event) {

        Cliente cliente = new Cliente();
        cliente.setId(currentPet.getId_dono());

        ArrayList<Agenda> agendas = new ArrayList();

        try {
            cliente = clienteDao.buscaID(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasDePetsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            agendas.addAll(agendaDao.lista("id_pet =" + currentPet.getId_pet()));
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasDePetsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Excluir");
        alerta.setHeaderText("Deseja Realmente deletar o PET:");
        alerta.setContentText("CPF do Dono: " + cliente.getCpf() + "\n\n" + "Nome do Pet: " + currentPet.getNome() + "\n" + "G??nero do Pet: " + currentPet.getGenero() + "\n" + "Ra??a do Pet: " + currentPet.getRaca());

        if (alerta.showAndWait().get() == ButtonType.OK) {
            for (Agenda agenda : agendas) {
                try {
                    agendaDao.remove(agenda);
                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasDePetsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
    private void btnEditar_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../cadastroPet.fxml"));
        root = loader.load();

        CadastroPetController cadastroPetController = loader.getController();

        cadastroPetController.onBtnEditar_click(currentPet);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Atualizar Pet");
        stage.setResizable(false);
        stage.centerOnScreen();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
         
        Cliente clienteDono = new Cliente();
        for (Pet pet : lista) {
            clienteDono.setId(pet.getId_dono());
            try {
                clienteDono = clienteDao.buscaID(clienteDono);
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasDePetsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            pet.setCpf_dono(clienteDono.getCpf());
        }
        
        obsPets.addAll(lista);

        nome.setCellValueFactory(new PropertyValueFactory<Pet, String>("nome"));
        genero.setCellValueFactory(new PropertyValueFactory<Pet, String>("genero"));
        raca.setCellValueFactory(new PropertyValueFactory<Pet, String>("raca"));
        cpf_dono.setCellValueFactory(new PropertyValueFactory<Pet, String>("cpf_dono"));

        tbvPet.setItems(obsPets);

        tbvPet.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pet>() {

            @Override
            public void changed(ObservableValue<? extends Pet> ov, Pet t, Pet t1) {
                currentPet = (Pet) tbvPet.getSelectionModel().getSelectedItem();
            }

        });
    }

}
