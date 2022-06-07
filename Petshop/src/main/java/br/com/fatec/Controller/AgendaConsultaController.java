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
import br.com.fatec.TextFieldFormatter;
import br.com.fatec.model.Agenda;
import br.com.fatec.model.Cliente;
import br.com.fatec.model.Funcionario;
import br.com.fatec.model.Pet;
import br.com.fatec.model.Servico;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author isaac
 */
public class AgendaConsultaController implements Initializable {

    SceneController sceneController = new SceneController();

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
    private ComboBox<Servico> cmbServico;
    @FXML
    private ComboBox<String> cmbHorario;

    @FXML
    private TextArea txtObservacao;
    @FXML
    private ComboBox<Funcionario> cmbFuncionario;
    //Variables
    Locale s = new Locale("PT", "BR");

    Agenda agendaEdit = null;

    //Currents
    Pet currentPet;
    Funcionario currentFuncionaro;

    //Lists
    ObservableList<Pet> pet = FXCollections.observableArrayList();

    ObservableList<String> horario = FXCollections.observableArrayList();
    ObservableList<Funcionario> funcionario = FXCollections.observableArrayList();
    ObservableList<Servico> servicos = FXCollections.observableArrayList();

    //DAOS
    Cliente cliente = new Cliente();
    ServicoDAO servicoDao = new ServicoDAO();
    ClienteDAO clienteDao = new ClienteDAO();
    PetDAO petDao = new PetDAO();
    AgendaDAO agendaDAO = new AgendaDAO();
    FuncionarioDAO funcionarioDao = new FuncionarioDAO();

    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //PetDAO petdao = new PetDAO();
        if (agendaEdit != null) {
            cmbHorario.setValue(agendaEdit.getHora());
        }

        setCmbHorario();
        setCmbFuncionario();
        setCmbServico();

    }

    @FXML
    private void btnCadastrar_click(ActionEvent event) throws IOException {

        if (agendaEdit == null) {
            Agenda agenda = new Agenda();
            LocalDate data = dpData.getValue();
            String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            Cliente cliente = new Cliente();
            cliente.setCpf(txtCpfDono.getText());

            try {
                cliente = clienteDao.buscaCPF(cliente);
            } catch (SQLException ex) {
                Logger.getLogger(AgendaConsultaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            agenda.setData(dataPickerString);
            agenda.setHora(cmbHorario.getValue());
            agenda.setObservacao(txtObservacao.getText());
            agenda.setId_pet(cmbPet.getValue().getId_pet());
            agenda.setId_func(cmbFuncionario.getValue().getId_funcionario());
            agenda.setId_cli(cliente.getId());
            agenda.setId_serv(cmbServico.getValue().getId_servico());

            try {
                agendaDAO.insere(agenda);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("SUCESSO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Agenda cadastrada com SUCESSO !!!");

                alerta.showAndWait();
                limparCampos();

            } catch (SQLException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("ERRO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Erro ao cadastrar!" + ex.getMessage());

                alerta.showAndWait();
            }
        } else {
            LocalDate data = dpData.getValue();
            String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            Cliente cliente = new Cliente();
            cliente.setCpf(txtCpfDono.getText());

            try {
                cliente = clienteDao.buscaCPF(cliente);
            } catch (SQLException ex) {
                Logger.getLogger(AgendaConsultaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            agendaEdit.setData(dataPickerString);
            agendaEdit.setHora(cmbHorario.getValue());
            agendaEdit.setObservacao(txtObservacao.getText());
            agendaEdit.setId_pet(cmbPet.getValue().getId_pet());
            agendaEdit.setId_func(cmbFuncionario.getValue().getId_funcionario());
            agendaEdit.setId_cli(cliente.getId());
            agendaEdit.setId_serv(cmbServico.getValue().getId_servico());

            try {
                agendaDAO.altera(agendaEdit);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("SUCESSO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Agenda Alterada com SUCESSO !!!");

                alerta.showAndWait();
                sceneController.switchToSceneConsultaDeServicos(event);
                limparCampos();

            } catch (SQLException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("ERRO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Erro ao Alterar!" + ex.getMessage());

                alerta.showAndWait();
            }
        }
    }

    private void setCmbServico() {
        ServicoDAO dao = new ServicoDAO();

        try {
            servicos = dao.buscaALL();
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro ao procurar!" + ex.getMessage());

            alerta.showAndWait();

        }

        cmbServico.setItems(servicos);
    }

    private void setCmbFuncionario() {

        funcionario.addAll(FuncionarioDAO.funcionarios);

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

    private void clearCmbHorario() {
        horario.remove("9:00");
        horario.remove("10:00");
        horario.remove("11:00");
        horario.remove("12:00");
        horario.remove("13:00");
        horario.remove("14:00");
        horario.remove("15:00");
        horario.remove("16:00");
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
    }

    @FXML
    private void cmbServico_selected(ActionEvent event) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(s);

        Servico servico = cmbServico.getValue();

        Float valor = servico.getPreco();
        lblPreco.setText(defaultFormat.format(valor));
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

    @FXML
    private void dpData_selected(ActionEvent event) {

        atualizaHorarios();

    }

    @FXML
    private void cmbPet_selected(ActionEvent event) {
        currentPet = cmbPet.getValue();
    }

    @FXML
    private void cmbFuncionario_selected(ActionEvent event) {
        currentFuncionaro = cmbFuncionario.getValue();
    }

    @FXML
    private void btnCancelar_click(ActionEvent event) throws IOException {

        if (agendaEdit != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Cancelar");
            alerta.setHeaderText("Deseja Realmente Cancelar a operação?");

            if (alerta.showAndWait().get() == ButtonType.OK) {
                sceneController.switchToSceneConsultaDeServicos(event);
                limparCampos();
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

    public void limparCampos() {
        txtCpfDono.setText("");
        txtObservacao.setText("");
        dpData.setValue(null);
        cmbHorario.setValue("");
        cmbPet.setValue(null);
        cmbServico.setValue(null);
        cmbFuncionario.setValue(null);
        lblPreco.setText("R$: 00,00");

        agendaEdit = null;
    }

    public void onBtnEditar_click(Agenda agenda) {

        Cliente dono = new Cliente();
        Pet pet = new Pet();
        Servico servico = new Servico();

        pet.setId_pet(agenda.getId_pet());

        try {
            pet = petDao.buscaID(pet);
        } catch (SQLException ex) {
            Logger.getLogger(AgendaConsultaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        dono.setId(agenda.getId_cli());

        try {
            dono = clienteDao.buscaID(dono);
        } catch (SQLException ex) {
            Logger.getLogger(AgendaConsultaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        servico.setId_servico(agenda.getId_serv());

        try {
            servico = servicoDao.buscaID(servico);
        } catch (SQLException ex) {
            Logger.getLogger(AgendaConsultaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(agenda.getId_func());

        try {
            funcionario = funcionarioDao.buscaID(funcionario);
        } catch (Exception ex) {
            Logger.getLogger(AgendaConsultaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = agenda.getData();
        LocalDate localDate = LocalDate.parse(date, formatter);

        dpData.setValue(localDate);
        cmbHorario.setValue(agenda.getHora());
        txtObservacao.setText(agenda.getObservacao());
        cmbPet.setValue(pet);
        txtCpfDono.setText(dono.getCpf());
        cmbServico.setValue(servico);
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(s);
        Float valor = servico.getPreco();
        lblPreco.setText(defaultFormat.format(valor));
        cmbFuncionario.setValue(funcionario);

        btnCadastrar.setText("Atualizar");
        agendaEdit = agenda;

        atualizaHorarios();
    }

    public void atualizaHorarios() {
        clearCmbHorario();
        setCmbHorario();
        ObservableList<Agenda> horasMarcadas = FXCollections.observableArrayList();
        LocalDate data = dpData.getValue();
        String dataPickerString = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        try {

            horasMarcadas.addAll(agendaDAO.lista("data=" + "'" + dataPickerString + "'"));

        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERRO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Erro ao Buscar os Dados!" + ex.getMessage());

            alerta.showAndWait();
        }

        for (Agenda horaMarcada : horasMarcadas) {
            horario.remove(horaMarcada.getHora());
        }
    }
}
