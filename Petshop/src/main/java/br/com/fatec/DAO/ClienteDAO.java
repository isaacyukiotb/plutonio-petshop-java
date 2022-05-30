/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Cliente;
import java.sql.SQLException;
import java.util.Collection;
import br.com.fatec.model.Proprietario;
import br.com.fatec.model.Veiculo;

import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;

import java.util.Collection;


/**
 *
 * @author isaac
 */
public class ClienteDAO implements DAO <Cliente>{
    
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
        String sql = "INSERT INTO cliente (cpf, rg, nome, data_nasc, cep, email) " +
                " VALUES (?, ?, ?, ?,?,?)"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Proprietario com o comando INSERT
        pst.setString(1, obj.getCpf());
        pst.setString(2, obj.getRg());
        pst.setString(3, obj.getNome());
        pst.setString(4, obj.getDataNasc());
        pst.setString(5, obj.getCep());
        pst.setString(6, obj.getEmail());
        
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Cliente obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        if(rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            cliente = new Cliente();
            
            cliente.setId(rs.getInt("id_cliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setRg(rs.getString("rg"));
            cliente.setNome(rs.getString("nome"));
            cliente.setDataNasc(rs.getString("data_nasc"));
            cliente.setCep(rs.getString("cep"));
            cliente.setEmail(rs.getString("email"));
            
        }
        else {
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        if(rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            cliente = new Cliente();
            
            cliente.setId(rs.getInt("id_cliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setRg(rs.getString("rg"));
            cliente.setNome(rs.getString("nome"));
            cliente.setDataNasc(rs.getString("data_nasc"));
            cliente.setCep(rs.getString("cep"));
            cliente.setEmail(rs.getString("email"));
            
        }
        else {
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
