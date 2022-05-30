CREATE DATABASE PLUTONIO;

USE PLUTONIO;


CREATE table IF NOT EXISTS cliente (
    id_cliente INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL unique,
    rg VARCHAR(9) NOT NULL unique,
    nome VARCHAR(50) NOT NULL,
    data_nasc DATE NOT NULL,
    cep VARCHAR(9) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE  IF NOT EXISTS pet (
    id_pet INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    raca VARCHAR(50) NOT NULL,
    genero CHAR(1) CHECK (genero='f' OR genero='m'),
    restricao VARCHAR(100),
    id_dono INT NOT NULL,
    FOREIGN KEY(id_dono) REFERENCES cliente(id_cliente)
);

CREATE table IF NOT EXISTS funcionario(
    id_func INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    rg VARCHAR(9) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    data_nasc DATE NOT NULL,
    cep VARCHAR(9) NOT NULL,
    funcao VARCHAR(20) NOT NULL,
    email VARCHAR(100)
);

CREATE table IF NOT EXISTS servico(
   id_serv INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
   nome VARCHAR(50) NOT NULL,
   preco NUMERIC(5,2) NOT NULL
);

CREATE table IF NOT EXISTS agenda(
  id_agend INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  data DATE NOT NULL,
  hora TIME NOT NULL,
  observacao VARCHAR(50),
  id_pet INT NOT NULL,
  id_func INT NOT NULL,
  FOREIGN KEY(id_pet) REFERENCES pet(id_pet),
  FOREIGN KEY(id_func) REFERENCES funcionario(id_func)
);
