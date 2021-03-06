/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Servico;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author isaac
 */
public class ServicoDAO implements DAO<Servico> {

    //variaveis auxiliares
    //permite o uso de comandos DML (select, insert, delete e update) para
    //acessar nosso SGBD
    private java.sql.PreparedStatement pst;

    //permite armazenar um conjunto de dados vindo do SGBD para ser
    //manipulado
    private java.sql.ResultSet rs;

    //representar os dados do  meu negócio
    private Servico servico; //meu MODEL   

    @Override
    public boolean insere(Servico obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(Servico obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Servico obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Servico buscaID(Servico obj) throws SQLException {
        String sql = "SELECT * FROM servico "
                + "WHERE id_serv = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando UPDATE
        pst.setInt(1, obj.getId_servico());

        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT

        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        if (rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            servico = new Servico();
            servico.setId_servico(rs.getInt("id_serv"));
            servico.setNome(rs.getString("nome"));
            servico.setPreco(rs.getFloat("preco"));
        } else {
            //não encontrou o registro solicitado
            servico = null;
        }

        //fecha a conexao
        Banco.desconectar();

        //devolve o objeto proprietario
        return servico;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Servico> lista(String criterio) throws SQLException {
        //cria uma lista para armazenar os dados vindos do banco
        ArrayList<Servico> lista = new ArrayList<>();

        String sql = "SELECT * FROM servico ";

        //precisa fazer filtro para listagem
        if (criterio != null && criterio.length() > 0) {
            sql += " WHERE " + criterio;
        }

        //abre a conexao com o banco
        Banco.conectar();

        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT

        //Varre todo o resultado da consulta e coloca cada registro dentro
        //de um objeto e coloca o objeto dentro da coleção
        while (rs.next()) {
            //criar o objeto
            servico = new Servico();

            //mover os dados do resultSet para o objeto proprietário
            servico.setId_servico(rs.getInt("id_serv"));
            servico.setNome(rs.getString("nome"));
            servico.setPreco(rs.getFloat("preco"));

            //move o objeto para a coleção
            lista.add(servico);
        }

        //fecha a conexao
        Banco.desconectar();

        //devolve o objeto proprietario
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ObservableList<Servico> buscaALL() throws SQLException {

        ObservableList<Servico> servicos = FXCollections.observableArrayList();

        String sql = "SELECT * FROM servico "; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando UPDATE
        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT

        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        while (rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            servico = new Servico();
            servico.setId_servico(rs.getInt("id_serv"));
            servico.setNome(rs.getString("nome"));
            servico.setPreco(rs.getFloat("preco"));

            servicos.add(servico);
        }

        Banco.desconectar();

        return servicos;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
