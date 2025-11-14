// Importa tudo de SQL e as nossas classes de "molde"
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) - Especialista em Banco de Dados.
 * Esta classe é a ÚNICA que sabe "falar" SQL com o banco de dados dos Alunos.
 */
public class AlunoDAO {

    // O endereço do banco de dados agora vive aqui.
    private static final String URL_BANCO = "jdbc:sqlite:C:\\Users\\Usuario\\IdeaProjects\\MeusEstudosJava\\minhaescola.db";

    // --- CREATE (Salvar) ---

    public void salvarAlunoComum(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO ALUNO(nome, nota1, nota2) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setDouble(2, aluno.getNota1());
            pstmt.setDouble(3, aluno.getNota2());
            pstmt.executeUpdate();
        }
    }

    public void salvarAlunoBolsista(AlunoBolsista bolsista) throws SQLException {
        String sql = "INSERT INTO ALUNO_BOLSISTA(nome, nota1, nota2, valor_bolsa) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bolsista.getNome());
            pstmt.setDouble(2, bolsista.getNota1());
            pstmt.setDouble(3, bolsista.getNota2());
            pstmt.setDouble(4, bolsista.getValorBolsa());
            pstmt.executeUpdate();
        }
    }

    // --- READ (Listar) ---

    public List<Aluno> listarAlunosComuns() throws SQLException {
        List<Aluno> lista = new ArrayList<>();
        String sqlAlunos = "SELECT * FROM ALUNO";

        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlAlunos)) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getDouble("nota1"),
                        rs.getDouble("nota2")
                );
                lista.add(aluno);
            }
        }
        return lista;
    }

    public List<AlunoBolsista> listarAlunosBolsistas() throws SQLException {
        List<AlunoBolsista> lista = new ArrayList<>();
        String sqlBolsistas = "SELECT * FROM ALUNO_BOLSISTA";

        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBolsistas)) {

            while (rs.next()) {
                AlunoBolsista bolsista = new AlunoBolsista(
                        rs.getString("nome"),
                        rs.getDouble("nota1"),
                        rs.getDouble("nota2"),
                        rs.getDouble("valor_bolsa")
                );
                lista.add(bolsista);
            }
        }
        return lista;
    }

    // --- DELETE (Deletar) ---

    public int deletarAluno(String nome) throws SQLException {
        int totalAfetado = 0;
        String sqlAluno = "DELETE FROM ALUNO WHERE nome = ?";
        String sqlBolsista = "DELETE FROM ALUNO_BOLSISTA WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection(URL_BANCO)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlAluno)) {
                pstmt.setString(1, nome);
                totalAfetado += pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sqlBolsista)) {
                pstmt.setString(1, nome);
                totalAfetado += pstmt.executeUpdate();
            }
        }
        return totalAfetado;
    }

    // --- READ (Buscar) ---

    public Aluno buscarAlunoComum(String nome) throws SQLException {
        String sqlAluno = "SELECT * FROM ALUNO WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             PreparedStatement pstmt = conn.prepareStatement(sqlAluno)) {

            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getString("nome"),
                            rs.getDouble("nota1"),
                            rs.getDouble("nota2")
                    );
                }
            }
        }
        return null; // Não achou
    }

    public AlunoBolsista buscarAlunoBolsista(String nome) throws SQLException {
        String sqlBolsista = "SELECT * FROM ALUNO_BOLSISTA WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             PreparedStatement pstmt = conn.prepareStatement(sqlBolsista)) {

            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new AlunoBolsista(
                            rs.getString("nome"),
                            rs.getDouble("nota1"),
                            rs.getDouble("nota2"),
                            rs.getDouble("valor_bolsa")
                    );
                }
            }
        }
        return null; // Não achou
    }

    // --- UPDATE (Atualizar) ---

    public void atualizarAlunoComum(String nomeAntigo, Aluno aluno) throws SQLException {
        String sql = "UPDATE ALUNO SET nome = ?, nota1 = ?, nota2 = ? WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setDouble(2, aluno.getNota1());
            pstmt.setDouble(3, aluno.getNota2());
            pstmt.setString(4, nomeAntigo); // O nome antigo vai no WHERE
            pstmt.executeUpdate();
        }
    }

    public void atualizarAlunoBolsista(String nomeAntigo, AlunoBolsista bolsista) throws SQLException {
        String sql = "UPDATE ALUNO_BOLSISTA SET nome = ?, nota1 = ?, nota2 = ?, valor_bolsa = ? WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection(URL_BANCO);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bolsista.getNome());
            pstmt.setDouble(2, bolsista.getNota1());
            pstmt.setDouble(3, bolsista.getNota2());
            pstmt.setDouble(4, bolsista.getValorBolsa());
            pstmt.setString(5, nomeAntigo); // O nome antigo vai no WHERE
            pstmt.executeUpdate();
        }
    }
}
