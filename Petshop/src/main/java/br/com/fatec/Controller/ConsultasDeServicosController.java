/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.AgendaDAO;
import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.DAO.PetDAO;
import br.com.fatec.DAO.ServicoDAO;
import br.com.fatec.SceneController;
import br.com.fatec.model.Agenda;
import br.com.fatec.model.Cliente;
import br.com.fatec.model.Funcionario;
import br.com.fatec.model.Pet;
import br.com.fatec.model.Servico;
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
public class ConsultasDeServicosController implements Initializable {

    SceneController sceneController = new SceneController();

    PetDAO petDao = new PetDAO();
    ClienteDAO clienteDao = new ClienteDAO();
    AgendaDAO agendaDao = new AgendaDAO();
    FuncionarioDAO funcionarioDao = new FuncionarioDAO();
    ServicoDAO servicoDao = new ServicoDAO();
    Agenda currentAgenda = new Agenda();

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
    private TableView<Agenda> tbvPet;
    @FXML
    private TableColumn<Agenda, String> data;
    @FXML
    private TableColumn<Agenda, String> hora;
    @FXML
    private TableColumn<Agenda, String> pet;
    @FXML
    private TableColumn<Agenda, String> servico;
    @FXML
    private TableColumn<Agenda, String> funcionario;
    @FXML
    private TableColumn<Agenda, String> cpfDono;

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
        carregaAgendas("");
        carregaCmbOptions();
    }

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException {
        sceneController.switchToSceneConsultaTipos(event);
    }

    private void carregaCmbOptions() {

        ObservableList<String> obsTipos = FXCollections.observableArrayList();
        obsTipos.add("Todos");
        obsTipos.add("id_agend");
        obsTipos.add("data");
        obsTipos.add("hora");
        obsTipos.add("observacao");

        cmTipoPesquisa.setItems(obsTipos);

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void btnPesquisar_click(ActionEvent event) {
        if (cmTipoPesquisa.getValue() == "Todos") {
            argumentos = "";
            carregaAgendas(argumentos);
        } else {
            argumentos = "";
            argumentos = cmTipoPesquisa.getValue();
            argumentos += " = " + "'" + txtDados.getText() + "'";
            carregaAgendas(argumentos);
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

        Agenda agenda = new Agenda();
        agenda.setId_agenda(currentAgenda.getId_agenda());

        try {
            agenda = agendaDao.buscaID(agenda);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Excluir");
        alerta.setHeaderText("Deseja Realmente deletar o PET:");
        alerta.setContentText("Id da consulta: " + agenda.getId_agenda() + "\n\n" + "Data: " + currentAgenda.getData() + "\n" + "Hora: " + currentAgenda.getHora() + "\n" + "Observa????o:  " + currentAgenda.getObservacao());

        if (alerta.showAndWait().get() == ButtonType.OK) {
            try {
                agendaDao.remove(currentAgenda);
                Alert alerta3 = new Alert(Alert.AlertType.INFORMATION);
                alerta3.setTitle("Sucesso!");
                alerta3.setHeaderText("INFORMACOES");
                alerta3.setContentText("Dados deletados com Sucesso! ");
                alerta3.showAndWait();
                carregaAgendas("");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../agendaConsulta.fxml"));
        root = loader.load();

        AgendaConsultaController agendaConsultaController = loader.getController();

        agendaConsultaController.onBtnEditar_click(currentAgenda);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Atualizar Servi??o");
        stage.setResizable(false);
        stage.centerOnScreen();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void carregaAgendas(String args) {

        ObservableList<Agenda> obsAgenda = FXCollections.observableArrayList();
        ArrayList<Agenda> lista = new ArrayList<>();

        try {
            lista.addAll(agendaDao.lista(args));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro na consulta: " + ex.getMessage());
            alerta.showAndWait();
        }

        Cliente clienteDono = new Cliente();
        Funcionario funcionarioAgenda;
        Servico servicoAgenda = new Servico();
        Pet petAgenda = new Pet();

        for (Agenda agenda : lista) {

            funcionarioAgenda= new Funcionario(agenda.getId_func());
            clienteDono.setId(agenda.getId_cli());
            servicoAgenda.setId_servico(agenda.getId_serv());
            petAgenda.setId_pet(agenda.getId_pet());

            try {
                funcionarioAgenda = funcionarioDao.buscaID(funcionarioAgenda);
            } catch (Exception ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("ERRO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Erro na consulta: " + ex.getMessage());
                alerta.showAndWait();
            }

            try {
                servicoAgenda = servicoDao.buscaID(servicoAgenda);
                petAgenda = petDao.buscaID(petAgenda);
                clienteDono = clienteDao.buscaID(clienteDono);

            } catch (SQLException ex) {
                Logger.getLogger(ConsultasDeServicosController.class.getName()).log(Level.SEVERE, null, ex);
            }

            agenda.setCpfCliente(clienteDono.getCpf());
            agenda.setNomeFunc(funcionarioAgenda.getNome());
            agenda.setNomePet(petAgenda.getNome());
            agenda.setTipoServ(servicoAgenda.getNome());

        }

        obsAgenda.addAll(lista);

        data.setCellValueFactory(new PropertyValueFactory<Agenda, String>("data"));
        hora.setCellValueFactory(new PropertyValueFactory<Agenda, String>("hora"));
        pet.setCellValueFactory(new PropertyValueFactory<Agenda, String>("nomePet"));
        servico.setCellValueFactory(new PropertyValueFactory<Agenda, String>("tipoServ"));
        funcionario.setCellValueFactory(new PropertyValueFactory<Agenda, String>("nomeFunc"));
        cpfDono.setCellValueFactory(new PropertyValueFactory<Agenda, String>("cpfCliente"));

        tbvPet.setItems(obsAgenda);

        tbvPet.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agenda>() {

            @Override
            public void changed(ObservableValue<? extends Agenda> ov, Agenda t, Agenda t1) {
                currentAgenda = (Agenda) tbvPet.getSelectionModel().getSelectedItem();
            }

        });
    }

}
