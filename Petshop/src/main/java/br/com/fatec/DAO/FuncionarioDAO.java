/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Funcionario;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author isaac
 */
public class FuncionarioDAO {

    public static ArrayList<Funcionario> funcionarios = new ArrayList<>();
    public static Integer idCount = 1;

    public boolean insere(Funcionario obj) throws Exception {
        try {
            funcionarios.add(obj);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean remove(Funcionario obj) throws Exception {
        try {
            FuncionarioDAO.funcionarios.remove(obj);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean altera(Funcionario obj) throws Exception {
        for (Funcionario funcionario : FuncionarioDAO.funcionarios) {
            if (funcionario.getId_funcionario() == obj.getId_funcionario()) {
                funcionario.setCpf(obj.getCpf());
                funcionario.setRg(obj.getRg());
                funcionario.setNome(obj.getNome());
                funcionario.setData_nasc(obj.getData_nasc());
                funcionario.setCep(obj.getCep());
                funcionario.setCargo(obj.getCargo());
                funcionario.setEmail(obj.getEmail());
                funcionario.setTelefone(obj.getTelefone());
                funcionario.setEndereco(obj.getEndereco());
                funcionario.setBairro(obj.getBairro());
                funcionario.setCidade(obj.getCidade());
                funcionario.setUf(obj.getUf());
                funcionario.setNumero(obj.getNumero());

                return true;
            }
        }
        return false;
    }

    public Funcionario buscaID(Funcionario obj) throws Exception {
        for (Funcionario funcionario : FuncionarioDAO.funcionarios) {
            if (funcionario.getId_funcionario() == obj.getId_funcionario()) {
                return funcionario;
            }
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Collection<Funcionario> lista(String tipo, String criterio) throws Exception {
        ArrayList<Funcionario> lista = new ArrayList<>();      

        if ("id_funcionario".equals(tipo)) {
            for (Funcionario funcionario : FuncionarioDAO.funcionarios) {
                if (funcionario.getId_funcionario() == Integer.parseInt(criterio)) {
                    lista.add(funcionario);
                }
            }
        } else if ("nome".equals(tipo)) {
            for (Funcionario funcionario : FuncionarioDAO.funcionarios) {
                if (funcionario.getNome().equals(criterio)) {
                    lista.add(funcionario);
                }
            }
        } else if ("cpf".equals(tipo)) {
            for (Funcionario funcionario : FuncionarioDAO.funcionarios) {
                if (funcionario.getCpf().equals(criterio)) {
                    lista.add(funcionario);
                }
            }
        } else if ("cargo".equals(tipo)) {
            for (Funcionario funcionario : FuncionarioDAO.funcionarios) {
                if (funcionario.getCargo().equals(criterio)) {
                    lista.add(funcionario);
                }
            }
        } else if ("email".equals(tipo)) {
            for (Funcionario funcionario : FuncionarioDAO.funcionarios) {
                if (funcionario.getEmail().equals(criterio)) {
                    lista.add(funcionario);
                }
            }

        } else {
            return FuncionarioDAO.funcionarios;
        }

        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void inserirFuncionariosPadroes() {
        FuncionarioDAO dao = new FuncionarioDAO();
        Funcionario f1 = new Funcionario(FuncionarioDAO.idCount++, "Mario", "Recepcionista", "(11)99888-8888", "Mario@gmail", "123.111.222-33", "23.111.222-3", "25-10-2000", "03203-010","R INDUSTRIAL","VILA BELA","SAO PAULO","SP","123");
        Funcionario f2 = new Funcionario(FuncionarioDAO.idCount++, "Maria", "Medica Veterinaria", "(11)99888-8888", "Maria@gmail", "123.111.222-33", "23.111.222-3", "21-05-1988", "03203-010","R INDUSTRIAL","VILA BELA","SAO PAULO","SP","123");
        Funcionario f3 = new Funcionario(FuncionarioDAO.idCount++, "Marcelo", "Tosadora", "(11)99888-8888", "Marcelo@gmail", "123.111.222-33", "23.111.222-3", "25-02-1990", "03203-010","R INDUSTRIAL","VILA BELA","SAO PAULO","SP","123");
        Funcionario f4 = new Funcionario(FuncionarioDAO.idCount++, "Marilene", "Assistente", "(11)99888-8888", "Marilene@gmail", "123.111.222-33", "23.111.222-3", "12-01-1983", "03203-010","R INDUSTRIAL","VILA BELA","SAO PAULO","SP","123");

        try {
            dao.insere(f1);
            dao.insere(f2);
            dao.insere(f3);
            dao.insere(f4);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
