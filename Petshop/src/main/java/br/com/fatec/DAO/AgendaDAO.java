/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Agenda;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author isaac
 */
public class AgendaDAO implements DAO<Agenda> {

    //variaveis auxiliares
    //permite o uso de comandos DML (select, insert, delete e update) para
    //acessar nosso SGBD
    private java.sql.PreparedStatement pst;

    //permite armazenar um conjunto de dados vindo do SGBD para ser
    //manipulado
    private java.sql.ResultSet rs;

    //representar os dados do  meu negócio
    private Agenda agenda;

    @Override
    public boolean insere(Agenda obj) throws SQLException {
        String sql = "INSERT INTO agenda (data, hora, observacao, id_pet, id_func,id_cli,id_serv) "
                + " VALUES (?,?,?,?,?,?,?)"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando INSERT
        pst.setString(1, obj.getData());
        pst.setString(2, obj.getHora());
        pst.setString(3, obj.getObservacao());
        pst.setInt(4, obj.getId_pet());
        pst.setInt(5, obj.getId_func());
        pst.setInt(6, obj.getId_cli());
        pst.setInt(7, obj.getId_serv());

        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update

        //fecha a conexao
        Banco.desconectar();

        //devolve se funcionou ou nao
        return res != 0;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(Agenda obj) throws SQLException {
        String sql = "DELETE FROM agenda WHERE id_agend = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando DELETE
        pst.setInt(1, obj.getId_agenda());

        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update

        //fecha a conexao
        Banco.desconectar();

        //devolve se funcionoou ou nao
        return res != 0;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Agenda obj) throws SQLException {
        String sql = "UPDATE agenda SET id_agend = ?, data=?,hora=?,observacao=?, id_pet=?,id_cli=?,id_func=?,id_serv=? "
                + "WHERE id_agend = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando UPDATE
        pst.setInt(1, obj.getId_agenda());
        pst.setString(2, obj.getData());
        pst.setString(3, obj.getHora());
        pst.setString(4, obj.getObservacao());
        pst.setInt(5, obj.getId_pet());
        pst.setInt(6, obj.getId_cli());
        pst.setInt(7, obj.getId_func());
        pst.setInt(8, obj.getId_serv());
        pst.setInt(9, obj.getId_agenda());

        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update

        //fecha a conexao
        Banco.desconectar();

        //devolve se funcionoou ou nao
        return res != 0;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Agenda buscaID(Agenda obj) throws SQLException {
        String sql = "SELECT * FROM agenda "
                + "WHERE id_agend = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando UPDATE
        pst.setInt(1, obj.getId_agenda());

        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT

        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        if (rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            agenda = new Agenda();
            agenda.setId_agenda(rs.getInt("id_agend"));
            agenda.setData(rs.getString("data"));
            agenda.setHora(rs.getString("hora"));
            agenda.setObservacao(rs.getString("observacao"));
            agenda.setId_pet(rs.getInt("id_pet"));
            agenda.setId_func(rs.getInt("id_func"));
            agenda.setId_cli(rs.getInt("id_cli"));
            agenda.setId_serv(rs.getInt("id_serv"));
        } else {
            //não encontrou o registro solicitado
            agenda = null;
        }

        //fecha a conexao
        Banco.desconectar();

        //devolve o objeto proprietario
        return agenda;

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Agenda> lista(String criterio) throws SQLException {
        //cria uma lista para armazenar os dados vindos do banco
        ArrayList<Agenda> lista = new ArrayList<>();

        String sql = "SELECT * FROM agenda ";

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
            agenda = new Agenda();

            //mover os dados do resultSet para o objeto proprietário
            agenda.setId_agenda(rs.getInt("id_agend"));
            agenda.setData(rs.getString("data"));
            agenda.setHora(rs.getString("hora"));
            agenda.setObservacao(rs.getString("observacao"));
            agenda.setId_pet(rs.getInt("id_pet"));
            agenda.setId_func(rs.getInt("id_func"));
            agenda.setId_cli(rs.getInt("id_cli"));
            agenda.setId_serv(rs.getInt("id_serv"));

            //move o objeto para a coleção
            lista.add(agenda);
        }

        //fecha a conexao
        Banco.desconectar();

        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
