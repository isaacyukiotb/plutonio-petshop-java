/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Agenda;
import br.com.fatec.model.Cliente;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
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
    private Cliente cliente;

    @Override
    public boolean insere(Agenda obj) throws SQLException {
        String sql = "INSERT INTO agenda (data, hora, observacao, id_pet, id_func) "
                + " VALUES (?,?,?,?,?)"; //a ? indica parametros

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Agenda obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Agenda buscaID(Agenda obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Agenda> lista(String criterio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
