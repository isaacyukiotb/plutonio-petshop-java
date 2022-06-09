/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Cliente;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Collection;

/**
 *
 * @author isaac
 */
public class ClienteDAO implements DAO<Cliente> {

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
    public boolean insere(Cliente obj) throws SQLException {
        String sql = "INSERT INTO cliente (cpf, rg, nome, data_nasc, email, telefone,cep,endereco, cidade, bairro, uf, numero) "
                + " VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?)"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando INSERT
        pst.setString(1, obj.getCpf());
        pst.setString(2, obj.getRg());
        pst.setString(3, obj.getNome());
        pst.setString(4, obj.getDataNasc());
        pst.setString(5, obj.getEmail());
        pst.setString(6, obj.getTelefone());
        pst.setString(7, obj.getCep());
        pst.setString(8, obj.getEndereco());
        pst.setString(9, obj.getCidade());
        pst.setString(10, obj.getBairro());
        pst.setString(11, obj.getUf());
        pst.setString(12, obj.getNumero());

        //obtem os dados via composição
        //pst.setInt(2, obj.getProprietario().getCodProprietario()); //FK
        //pst.setString(3, obj.getModelo());
        //pst.setDouble(4, obj.getValor());
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update

        //fecha a conexao
        Banco.desconectar();

        //devolve se funcionoou ou nao
        return res != 0;
    }

    @Override
    public boolean remove(Cliente obj) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando DELETE
        pst.setInt(1, obj.getId());

        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update

        //fecha a conexao
        Banco.desconectar();

        //devolve se funcionoou ou nao
        return res != 0;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Cliente obj) throws SQLException {
        String sql = "UPDATE cliente SET id_cliente = ?, cpf = ?, rg = ?, nome = ?, data_nasc = ?, cep = ?, email = ?, telefone=?,endereco=?,cidade=?,bairro=?,uf=?,numero=?"
                + "WHERE id_cliente = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando UPDATE
        pst.setInt(1, obj.getId());
        pst.setString(2, obj.getCpf());
        pst.setString(3, obj.getRg());
        pst.setString(4, obj.getNome());
        pst.setString(5, obj.getDataNasc());
        pst.setString(6, obj.getCep());
        pst.setString(7, obj.getEmail());
        pst.setString(8, obj.getTelefone());
        pst.setString(9, obj.getEndereco());
        pst.setString(10, obj.getCidade());
        pst.setString(11, obj.getBairro());
        pst.setString(12, obj.getUf());
        pst.setString(13, obj.getNumero());

        pst.setInt(14, obj.getId());

        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update

        //fecha a conexao
        Banco.desconectar();

        //devolve se funcionoou ou nao
        return res != 0;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente buscaID(Cliente obj) throws SQLException {
        String sql = "SELECT * FROM cliente "
                + "WHERE id_cliente = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando UPDATE
        pst.setInt(1, obj.getId());

        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT

        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        if (rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            cliente = new Cliente();

            cliente.setId(rs.getInt("id_cliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setRg(rs.getString("rg"));
            cliente.setNome(rs.getString("nome"));
            cliente.setDataNasc(rs.getString("data_nasc"));
            cliente.setCep(rs.getString("cep"));
            cliente.setEmail(rs.getString("email"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setCidade(rs.getString("cidade"));
            cliente.setBairro(rs.getString("bairro"));
            cliente.setUf(rs.getString("uf"));
            cliente.setNumero(rs.getString("numero"));
        

        } else {
            //não encontrou o registro solicitado
            cliente = null;
        }

        //fecha a conexao
        Banco.desconectar();

        //devolve o objeto proprietario
        return cliente;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection lista(String criterio) throws SQLException {
        //cria uma lista para armazenar os dados vindos do banco
        ArrayList<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM cliente ";

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
            cliente = new Cliente();

            //mover os dados do resultSet para o objeto proprietário
            cliente.setId(rs.getInt("id_cliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setRg(rs.getString("rg"));
            cliente.setNome(rs.getString("nome"));
            cliente.setDataNasc(rs.getString("data_nasc"));
            cliente.setCep(rs.getString("cep"));
            cliente.setEmail(rs.getString("email"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setCidade(rs.getString("cidade"));
            cliente.setBairro(rs.getString("bairro"));
            cliente.setUf(rs.getString("uf"));
            cliente.setNumero(rs.getString("numero"));

            //move o objeto para a coleção
            lista.add(cliente);
        }

        //fecha a conexao
        Banco.desconectar();

        //devolve o objeto proprietario
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Cliente buscaCPF(Cliente obj) throws SQLException {
        String sql = "SELECT * FROM cliente "
                + "WHERE cpf = ?"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando UPDATE
        pst.setString(1, obj.getCpf());

        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT

        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        if (rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            cliente = new Cliente();

            cliente.setId(rs.getInt("id_cliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setRg(rs.getString("rg"));
            cliente.setNome(rs.getString("nome"));
            cliente.setDataNasc(rs.getString("data_nasc"));
            cliente.setCep(rs.getString("cep"));
            cliente.setEmail(rs.getString("email"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setCidade(rs.getString("cidade"));
            cliente.setBairro(rs.getString("bairro"));
            cliente.setUf(rs.getString("uf"));
            cliente.setNumero(rs.getString("numero"));

        } else {
            //não encontrou o registro solicitado
            cliente = null;
        }

        //fecha a conexao
        Banco.desconectar();

        //devolve o objeto proprietario
        return cliente;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
