<h1 align="center">✅ Requisito 06 - Cadastro de Warehouse e Seção ✅</h1>
<p align="center">Branch criada para hospedar o código do Requisito Individual número 06</p>

<p align="center">
  <img align="" alt="meliantes" src="https://media2.giphy.com/media/vzO0Vc8b2VBLi/giphy.gif?cid=ecf05e4746bhqetxc9j608r2ln5slim4yf678v73t8zsr0u4&rid=giphy.gif&ct=g" height="70%" width="70%" />
</p>

<p align="center">
 <a href="#Objetivo">Sobre o Requisito 06</a> •
 <a href="#Requisitos">Definição Formal</a> • 
 <a href="#Tecnologias">Tecnologias</a> • 
 <a href="#Pré-requisitos">Pré-requisitos</a> • 
 <a href="#Rodando o Servidor">Rodando o Servidor</a> • 
 <a href="#Dependências">Dependências</a> • 
 <a href="#Coleção-de-Requisições">Coleção de Requisições</a> •
 <a href="#Documentação-Complementar">Documentação Complementar</a> •
 <a href="#Autores">Autores</a>
</p>

<a name="Objetivo"></a>
## 🖊 Sobre o Requisito 06

<p> 
O objetivo do requisito de número seis é acrescentar, individualmente, valor à API Rest desenvolvida durante o Projeto Integrador, escrita na linguagem Java. Esta API está inserida no contexto de venda de produtos frescos, 
refrigerados e congelados pelo Mercado Livre. Neste sentido, o programa viabiliza o cadastro, consulta e edição de lotes de produtos setorizados, bem como a gerência de carrinho de compras e de diferentes anúncios.
Para o requisito individual, decidiu-se implementar métodos de cadastro e consulta de armazéns de distribuição e de seções de armazenamento.
</p>

___

<a name="Requisitos"></a>

## 📄 Definição Formal

<p> Obrigatórios (RO) </p>

<p>

- RO-01 Inserir o lote no armazém de atendimento;
- RO-02 Adicionar produto ao carrinho de compras;
- RO-03 Verificar a localização de um produto no armazém;
- RO-04 Consultar o estoque de um produto em todos os armazéns;
- RO-05 Consultar a data de validade por lote
- RO-06 Testes de unidade & testes de integração de todos os requisitos anteriores.

</p>

<p> Requisito Individual (RI) </p>

<p>

- RI-01 Inserir um novo armazém;
- RI-02 Consultar um armazém específico;
- RI-03 Consultar todos os armazéns;
- RI-04 Inserir uma nova seção;
- RI-05 Consultar uma seção específica;
- RI-06 Consultar todas as seções;

</p>

<p> Cobertura de testes Unitários (TU) implementados (%)</p>
<p>

- TU-01 BatchController (100% methods, 100% lines);
- TU-02 CartController (100% methods, 100% lines);
- TU-03 OrderController (100% methods, 100% lines);
- TU-04 ProductController (100% methods, 100% lines);
- TU-05 SectionController (100% methods, 100% lines);
- TU-06 WarehouseController (100% methods, 100% lines);
- TU-07 AgentService (100% methods, 100% lines);
- TU-08 BatchService (80% methods, 86% lines);
- TU-09 CartService (100% methods, 100% lines);
- TU-10 OrderService (100% methods, 92% lines);
- TU-11 ProductService (87% methods, 90% lines);
- TU-12 SectionService (100% methods, 100% lines);
- TU-13 WarehouseService (100% methods, 100% lines);

</p>

___
<a name="Tecnologias"></a>

## 🛠 Tecnologias
A API foi construída utilizando a linguagem Java, com o framework Spring Boot. Como banco de dados, utilizou-se o MySQL, por meio da biblioteca JPA.

___
<a name="Pré-requisitos"></a>

## ✅ Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [Java](https://www.java.com/pt-BR/) e um editor de código a sua escolha. Sugere-se o uso do Intelij Idea, disponível [aqui](https://www.jetbrains.com/pt-br/idea/).

___

<a name="Rodando o Servidor"></a>

## 🎲 Rodando o Servidor


- Clone este repositório na sua máquina:

      git clone https://github.com/diovanavalim/projeto-integrador.git

- Acesse a pasta do projeto no terminal:

      cd projeto_integrador

- Instale as dependências do projeto:

      mvn clean install

- Execute o servidor:

      mvn spring-boot:run

O servidor estará sendo escutado na porta indicada na IDE. Geralmente, trata-se da porta 8080.

___
<a name="Dependências"></a>
## 🏁 Dependências

Foram utilizadas no projeto as seguintes dependências:

- 🍃 Spring Boot Dev Tools;

- 🍃 Spring Boot Web;

- 🍃 Spring Boot Stater Validations;

- 🌶️ Lombok;

- 🍃 Spring Data JPA

- 🐬 MySQL Driver
___

<a name="Coleção-de-Requisições"></a>

## 🌙 Coleção de Requisições

A coleção de requisições utilizadas para testar os endpoints estão na pasta `projeto_integrador/docs`. O arquivo `Projeto Integrador - Mercado Frescos.postman_collection.json` pode ser importado em algum cliente HTTP, como o Postman ou o Insomnia.

Já a coleção de requisições utilizadas para testar os endpoints do **Requisito Individual** estão na pasta `projeto_integrador/req6-docs`. O arquivo `Requisito 06 - Projeto Integrador.postman_collection.json` pode ser importado em algum cliente HTTP, como o Postman e o Insomnia.

---
<a name="Documentação-Complementar"></a>

## 📚 Documentação Complementar

Na pasta `projeto_integrador/req6-docs` estão o Diagrama de Entidade e Relacionamento `Projeto Integrador DER.svg` e a definição formal das histórias de usuário para o requisito 06 `Requisito 06 - UserStory.pdf`.

___

## <img alt="coffee_cup" src="https://user-images.githubusercontent.com/80721339/173413428-56d4f208-6f5f-437d-ad91-cb878a90a01a.png" width="30px" /> Java Docs

Criou-se também uma documentação da aplicação, utilizando a ferramenta Java Docs, que pode ser consultada seguindo os passos a seguir:

1 - À partir da IDE de sua preferência, basta rodar o comando: `mvn javadoc:javadoc`. Esse comando fará o build da documentação e criará uma pasta chamada **target**.

2 - Agora basta localizar o arquivo `index-all.html` -> clicar com o botão direito em "Open" -> escolher o browser de sua preferência. Ao clicar, será aberta uma página contendo a documentação.

___

<a name="Autores"></a>

## 📝 Autores ##

Desenvolvido com 💛 por Diovana.

