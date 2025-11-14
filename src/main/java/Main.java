import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

// IMPORTAÇÕES NOVAS para o Banco de Dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    // --- 1. O "Lançador" da Janela (não muda) ---
    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource("/janela_alunos.fxml");

            if (fxmlUrl == null) {
                throw new IOException("Erro: Não foi possível encontrar o arquivo 'janela_alunos.fxml'.");
            }

            AnchorPane root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root, 700, 500);
            primaryStage.setTitle("Sistema de Gestão de Alunos (v1.0)");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- 2. O NOVO MÉTODO DE AJUDA ---
    // Este método vai criar nossas tabelas se elas não existirem
    private static void criarTabelas() {
        // O "endereço" completo do seu banco de dados antigo
        String url = "jdbc:sqlite:C:\\Users\\Usuario\\IdeaProjects\\MeusEstudosJava\\minhaescola.db";

        // O SQL que usamos no projeto antigo
        String sqlAluno = "CREATE TABLE IF NOT EXISTS ALUNO ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nome TEXT NOT NULL,"
                + " nota1 REAL NOT NULL,"
                + " nota2 REAL NOT NULL"
                + ");";

        String sqlBolsista = "CREATE TABLE IF NOT EXISTS ALUNO_BOLSISTA ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nome TEXT NOT NULL,"
                + " nota1 REAL NOT NULL,"
                + " nota2 REAL NOT NULL,"
                + " valor_bolsa REAL NOT NULL"
                + ");";

        // "try-with-resources" para conectar e fechar
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Executa os dois comandos de criação
            stmt.execute(sqlAluno);
            stmt.execute(sqlBolsista);
            System.out.println("Tabelas 'ALUNO' e 'ALUNO_BOLSISTA' verificadas com sucesso.");

        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- 3. O MÉTODO MAIN (A MUDANÇA ESTÁ AQUI) ---
    public static void main(String[] args) {

        // ANTES de "lançar" a janela...
        criarTabelas(); // ...nós mandamos ele criar/verificar as tabelas!

        // E DEPOIS, nós lançamos a janela.
        launch(args);
    }
}