/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.SceneController;
import br.com.fatec.model.Funcionario;
import java.io.IOException;
import java.net.URL;
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
public class ConsultasDeFuncionariosController implements Initializable {

    SceneController sceneController = new SceneController();
    Funcionario currentFuncionario = new Funcionario();
    FuncionarioDAO funcionarioDao = new FuncionarioDAO();
    String argumentos = "";
    String tipo = "";

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    private TableView<Funcionario> tbvPet;
    @FXML
    private TableColumn<Funcionario, String> nome;

    @FXML
    private TableColumn<Funcionario, String> cpf;
    @FXML
    private TableColumn<Funcionario, String> rg;
    @FXML
    private TableColumn<Funcionario, String> dataNasc;
    @FXML
    private TableColumn<Funcionario, String> cargo;
    /**
     * Initializes the controller class.
     *
     */
    ObservableList<Funcionario> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregaFuncionarios("","");
        carregaCmbOptions();
    }

    @FXML
    private void switchPage_home(ActionEvent event) throws IOException {
        sceneController.switchToSceneConsultaTipos(event);
    }

    private void carregaCmbOptions() {

        ObservableList<String> obsTipos = FXCollections.observableArrayList();
        obsTipos.add("Todos");
        obsTipos.add("id_funcionario");
        obsTipos.add("cpf");  
        obsTipos.add("nome");    
        obsTipos.add("email");
        obsTipos.add("cargo");

        cmTipoPesquisa.setItems(obsTipos);

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void btnPesquisar_click(ActionEvent event) {
        if (cmTipoPesquisa.getValue() == "Todos") {
            argumentos = "";
            tipo = "";
            carregaFuncionarios(tipo,argumentos);
        } else {
            argumentos = "";
            tipo = cmTipoPesquisa.getValue();
            argumentos +=txtDados.getText();
            
            carregaFuncionarios(tipo,argumentos);
        }
    }

    @FXML
    private void txtDados_KeyReleased(KeyEvent event) {
        argumentos = "";
        tipo="";
    }

    @FXML
    private void cmTipoPesquisa_selected(ActionEvent event) {

        txtDados.setText("");
        argumentos = "";
        tipo="";

    }

    @FXML
    private void btnDeletar_click(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Excluir");
        alerta.setHeaderText("Deseja Realmente deletar o Funcionario:");
        alerta.setContentText("CPF: " + currentFuncionario.getCpf() + "\n\n" + "Nome: " + currentFuncionario.getNome() + "\n" + "Data Nasc. : " + currentFuncionario.getData_nasc() + "\n" + "Email: " + currentFuncionario.getEmail());

        if (alerta.showAndWait().get() == ButtonType.OK) {
            try {
                funcionarioDao.remove(currentFuncionario);
                carregaFuncionarios("", "");
            } catch (Exception ex) {
                Logger.getLogger(ConsultasDeFuncionariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void btnEditar_click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../cadastroFuncionario.fxml"));
        root = loader.load();

        CadastroFuncionarioController cadastroFuncionarioController = loader.getController();

        cadastroFuncionarioController.onBtnEditar_click(currentFuncionario);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Atualizar Funcionario");
        stage.setResizable(false);
        stage.centerOnScreen();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void carregaFuncionarios(String tipo,String args) {
        ObservableList<Funcionario> obsCliente = FXCollections.observableArrayList();
        ArrayList<Funcionario> lista = new ArrayList<>();
        try {
            lista.addAll(funcionarioDao.lista(tipo,args));
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro na consulta: " + ex.getMessage());
            alerta.showAndWait();
        }

        obsCliente.addAll(lista);

        nome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
        cpf.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("cpf"));
        rg.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("rg"));
        dataNasc.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("data_nasc"));
        cargo.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("cargo"));

        tbvPet.setItems(obsCliente);

        tbvPet.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Funcionario>() {

            @Override
            public void changed(ObservableValue<? extends Funcionario> ov, Funcionario t, Funcionario t1) {
                currentFuncionario = (Funcionario) tbvPet.getSelectionModel().getSelectedItem();

            }

        });
    }

}
