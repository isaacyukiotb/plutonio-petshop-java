/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Cliente;
import br.com.fatec.model.Pet;
import br.com.fatec.model.Proprietario;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author isaac
 */
public class PetDAO implements DAO<Pet> {

    //variaveis auxiliares
    //permite o uso de comandos DML (select, insert, delete e update) para
    //acessar nosso SGBD
    private java.sql.PreparedStatement pst;

    //permite armazenar um conjunto de dados vindo do SGBD para ser
    //manipulado
    private java.sql.ResultSet rs;

    //representar os dados do  meu negócio
    private Pet pet; //meu MODEL   

    @Override
    public boolean insere(Pet obj) throws SQLException {
        String sql = "INSERT INTO Pet (nome, categoria, raca, genero, restricao, id_dono) "
                + " VALUES (?, ?, ?, ?, ?, ?)"; //a ? indica parametros

        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);

        //associar os dados do objeto Proprietario com o comando INSERT
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getCategoria());
        pst.setString(3, obj.getRaca());
        pst.setString(4, obj.getGenero());
        pst.setString(5, obj.getRestricao());
        pst.setInt(6, obj.getId_dono());

        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update

        //fecha a conexao
        Banco.desconectar();

        //devolve se funcionou ou nao
        return res != 0;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(Pet obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Pet obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pet buscaID(Pet obj) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Pet> lista(String criterio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ObservableList<Pet> buscaDono(Cliente obj) throws SQLException {

        ObservableList<Pet> pets = FXCollections.observableArrayList();

        String sql = "SELECT * FROM pet "
                + "WHERE id_dono = ?"; //a ? indica parametros

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
        while (rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário

            Pet pet = new Pet();

            pet.setId_pet(rs.getInt("id_pet"));
            pet.setNome(rs.getString("nome"));
            pet.setCategoria(rs.getString("categoria"));
            pet.setRaca(rs.getString("raca"));
            pet.setGenero(rs.getString("genero"));
            pet.setRestricao(rs.getString("restricao"));
            pet.setId_dono(rs.getInt("id_dono"));

            pets.add(pet);
        }

        //fecha a conexao
        Banco.desconectar();

        //devolve o objeto proprietario
        return pets;

    }

}
