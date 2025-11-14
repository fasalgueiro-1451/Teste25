# 🚀 Sistema de Gestão de Alunos (CRUD Full-Stack)

Este é o meu primeiro projeto "full-stack" em Java, construído como parte da minha maratona de estudos em Engenharia de Software.

É um aplicativo **Desktop (GUI)** completo para gerenciar alunos (Comuns e Bolsistas) que implementa todo o ciclo **CRUD** (Create, Read, Update, Delete) conectado diretamente a um banco de dados SQL.

---

## 📋 Funcionalidades (O que ele faz)

O sistema permite ao usuário:
* **Create (Inserir):** Cadastrar novos Alunos Comuns e Alunos Bolsistas em um banco de dados.
* **Read (Ler):** Listar todos os alunos do banco em uma área de relatório, mostrando a média e o status de "APROVADO!" / "REPROVADO!".
* **Update (Atualizar):** Buscar um aluno existente pelo nome, carregar seus dados na tela, permitir a edição e salvar as mudanças no banco.
* **Delete (Deletar):** Deletar um aluno do banco de dados usando o nome.

---

## 🛠️ Tecnologias Utilizadas (O "Stack")

Este projeto foi construído do zero, conectando várias tecnologias centrais do ecossistema Java:

* **Linguagem:** Java (JDK 25)
* **Frontend (GUI):** JavaFX
* **Design da Interface:** Scene Builder (para os arquivos `.fxml`)
* **Gerenciamento de Dependências:** Apache Maven (para o `pom.xml`)
* **Banco de Dados:** SQLite
* **Conexão (Ponte):** JDBC (Java Database Connectivity)

### 🧠 Conceitos de Programação Aplicados
* **Orientação a Objetos (OOP):** Os 4 Pilares foram usados na prática:
    1.  **Classes/Objetos:** `Aluno.java`, `AlunoBolsista.java`
    2.  **Herança:** `AlunoBolsista` herda de `Aluno`.
    3.  **Encapsulamento:** Atributos `private` com `getters` e `setters` para validação.
    4.  **Polimorfismo:** O `@Override` do método `calcularMedia()` para o bolsista (que ganha bônus).
* **Padrão de Projeto (Design Pattern):**
    * **DAO (Data Access Object):** A lógica de SQL (`INSERT`, `SELECT`, etc.) foi separada da lógica da tela, sendo isolada na classe `AlunoDAO.java`.
* **Tratamento de Exceções:** Uso de `try-catch` para lidar com erros de banco (`SQLException`) e erros de digitação do usuário (`NumberFormatException`).

---

## 🏃‍♂️ Como Executar o Projeto

1.  Clone este repositório (`git clone ...`).
2.  Abra o projeto no IntelliJ IDEA (certifique-se de que é um projeto Maven).
3.  O IntelliJ (via Maven) irá baixar automaticamente todas as dependências (`pom.xml`).
4.  **Importante:** O projeto usa um caminho absoluto para o banco de dados. Para funcionar na sua máquina, vá nos arquivos `AlunoDAO.java` e `Main.java` e altere a `String` da URL do banco para o caminho onde você salvou o arquivo `minhaescola.db`.
5.  Execute o projeto usando a aba **Maven** $\rightarrow$ **Plugins** $\rightarrow$ **`javafx:run`**.
