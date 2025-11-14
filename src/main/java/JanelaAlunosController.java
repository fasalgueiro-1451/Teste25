import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

// Importamos o novo DAO
import java.util.List;
import java.sql.SQLException; // Só precisamos disto para o "catch"

/**
 * Controller (Cérebro da Tela) - VERSÃO REATORADA COM DAO
 * Esta classe agora só controla a TELA.
 * Ela não sabe SQL. Ela "chama" o AlunoDAO para fazer o trabalho sujo.
 */
public class JanelaAlunosController {

    // --- 1. "COLANDO" OS COMPONENTES DO FXML (Não muda) ---
    @FXML private TextField campoNome;
    @FXML private TextField campoNota1;
    @FXML private TextField campoNota2;
    @FXML private TextField campoBolsa;
    @FXML private RadioButton radioComum;
    @FXML private RadioButton radioBolsista;
    @FXML private TextArea areaRelatorio;
    @FXML private TextField campoDeletarNome;
    private ToggleGroup tipoAlunoGrupo;
    private String nomeDoAlunoEmEdicao = null;

    // --- NOVIDADE: O Controller agora "tem" um DAO ---
    private AlunoDAO alunoDAO = new AlunoDAO();


    // --- 2. MÉTODO DE INICIALIZAÇÃO (Não muda) ---
    @FXML
    public void initialize() {
        tipoAlunoGrupo = new ToggleGroup();
        radioComum.setToggleGroup(tipoAlunoGrupo);
        radioBolsista.setToggleGroup(tipoAlunoGrupo);
        radioComum.setSelected(true);
        campoBolsa.setDisable(true);
        radioComum.setOnAction(e -> campoBolsa.setDisable(true));
        radioBolsista.setOnAction(e -> campoBolsa.setDisable(false));
    }

    // --- 3. AÇÃO "Salvar" (AGORA MUITO MAIS LIMPO) ---
    @FXML
    protected void aoClicarSalvar() {
        try {
            // 1. Pegamos os dados da tela
            String nome = campoNome.getText();
            double n1 = Double.parseDouble(campoNota1.getText());
            double n2 = Double.parseDouble(campoNota2.getText());

            // --- LÓGICA DE DECISÃO (UPDATE vs INSERT) ---
            if (this.nomeDoAlunoEmEdicao != null) {
                // MODO UPDATE
                System.out.println("MODO: ATUALIZAÇÃO (UPDATE) para " + this.nomeDoAlunoEmEdicao);
                if (radioBolsista.isSelected()) {
                    double bolsa = Double.parseDouble(campoBolsa.getText());
                    AlunoBolsista bolsista = new AlunoBolsista(nome, n1, n2, bolsa);
                    alunoDAO.atualizarAlunoBolsista(this.nomeDoAlunoEmEdicao, bolsista); // Chama o DAO
                } else {
                    Aluno aluno = new Aluno(nome, n1, n2);
                    alunoDAO.atualizarAlunoComum(this.nomeDoAlunoEmEdicao, aluno); // Chama o DAO
                }
                areaRelatorio.setText("...Aluno " + this.nomeDoAlunoEmEdicao + " atualizado para " + nome + "!");
                this.nomeDoAlunoEmEdicao = null;

            } else {
                // MODO INSERT
                System.out.println("MODO: CRIAÇÃO (INSERT)");
                if (radioBolsista.isSelected()) {
                    double bolsa = Double.parseDouble(campoBolsa.getText());
                    AlunoBolsista bolsista = new AlunoBolsista(nome, n1, n2, bolsa);
                    alunoDAO.salvarAlunoBolsista(bolsista); // Chama o DAO
                    areaRelatorio.setText("...Aluno Bolsista " + nome + " salvo com SUCESSO!");
                } else {
                    Aluno aluno = new Aluno(nome, n1, n2);
                    alunoDAO.salvarAlunoComum(aluno); // Chama o DAO
                    areaRelatorio.setText("...Aluno Comum " + nome + " salvo com SUCESSO!");
                }
            }
        } catch (NumberFormatException e) {
            areaRelatorio.setText("[ERRO] As notas e a bolsa devem ser NÚMEROS (ex: 8.5)");
        } catch (SQLException e) {
            // O "catch" agora pega os erros do DAO
            areaRelatorio.setText("[ERRO DE BANCO] " + e.getMessage());
        }

        limparCampos();
        aoClicarListar(); // Atualiza a lista
    }

    // --- 4. AÇÃO "Listar" (AGORA MUITO MAIS LIMPO) ---
    @FXML
    protected void aoClicarListar() {
        StringBuilder relatorio = new StringBuilder();
        try {
            // 1. Pede a lista de comuns ao DAO
            relatorio.append("--- Alunos Comuns ---\n");
            List<Aluno> comuns = alunoDAO.listarAlunosComuns();
            for (Aluno aluno : comuns) {
                String situacao = aluno.verificarSituacao();
                relatorio.append(String.format("Nome: %s -> Média: %.1f (%s)%n",
                        aluno.getNome(), aluno.calcularMedia(), situacao));
                relatorio.append("--------------------\n");
            }

            // 2. Pede a lista de bolsistas ao DAO
            relatorio.append("\n--- Alunos Bolsistas ---\n");
            List<AlunoBolsista> bolsistas = alunoDAO.listarAlunosBolsistas();
            for (AlunoBolsista bolsista : bolsistas) {
                String situacao = bolsista.verificarSituacao();
                relatorio.append(String.format("Nome: %s -> Média: %.1f (%s)%n",
                        bolsista.getNome(), bolsista.calcularMedia(), situacao));
                relatorio.append(String.format("   Bolsa: R$ %.2f%n",
                        bolsista.getValorBolsa()));
                relatorio.append("--------------------\n");
            }
        } catch (SQLException e) {
            areaRelatorio.setText("[ERRO DE BANCO] " + e.getMessage());
        }
        areaRelatorio.setText(relatorio.toString());
    }

    // --- 5. AÇÃO "Deletar" (AGORA MUITO MAIS LIMPO) ---
    @FXML
    protected void aoClicarDeletar() {
        String nomeParaDeletar = campoDeletarNome.getText();
        if (nomeParaDeletar == null || nomeParaDeletar.trim().isEmpty()) {
            areaRelatorio.setText("[ERRO] Por favor, digite um nome para deletar.");
            return;
        }

        try {
            int totalAfetado = alunoDAO.deletarAluno(nomeParaDeletar); // Chama o DAO
            if (totalAfetado > 0) {
                areaRelatorio.setText("...Aluno(s) com nome '" + nomeParaDeletar + "' foram deletados!");
                aoClicarListar();
            } else {
                areaRelatorio.setText("Nenhum aluno com o nome '" + nomeParaDeletar + "' foi encontrado.");
            }
            campoDeletarNome.setText("");
        } catch (SQLException e) {
            areaRelatorio.setText("[ERRO DE BANCO] " + e.getMessage());
        }
    }

    // --- 6. AÇÃO "Buscar" (AGORA MUITO MAIS LIMPO) ---
    @FXML
    protected void aoClicarBuscar() {
        String nomeParaBuscar = campoDeletarNome.getText();
        if (nomeParaBuscar == null || nomeParaBuscar.trim().isEmpty()) {
            areaRelatorio.setText("[ERRO] Por favor, digite um nome para buscar.");
            return;
        }

        try {
            // 1. Procura na tabela ALUNO
            Aluno aluno = alunoDAO.buscarAlunoComum(nomeParaBuscar); // Chama o DAO

            if (aluno != null) {
                // Achou um Aluno Comum
                campoNome.setText(aluno.getNome());
                campoNota1.setText(String.valueOf(aluno.getNota1()));
                campoNota2.setText(String.valueOf(aluno.getNota2()));
                radioComum.setSelected(true);
                campoBolsa.setText("");
                campoBolsa.setDisable(true);

                this.nomeDoAlunoEmEdicao = nomeParaBuscar;
                areaRelatorio.setText("Aluno '" + nomeParaBuscar + "' carregado. Edite os campos e clique em 'Salvar Aluno'.");
            } else {
                // 2. Se não achou, procura na tabela BOLSISTA
                AlunoBolsista bolsista = alunoDAO.buscarAlunoBolsista(nomeParaBuscar); // Chama o DAO

                if (bolsista != null) {
                    // Achou um Aluno Bolsista
                    campoNome.setText(bolsista.getNome());
                    campoNota1.setText(String.valueOf(bolsista.getNota1()));
                    campoNota2.setText(String.valueOf(bolsista.getNota2()));
                    radioBolsista.setSelected(true);
                    campoBolsa.setDisable(false);
                    campoBolsa.setText(String.valueOf(bolsista.getValorBolsa()));

                    this.nomeDoAlunoEmEdicao = nomeParaBuscar;
                    areaRelatorio.setText("Aluno '" + nomeParaBuscar + "' carregado. Edite os campos e clique em 'Salvar Aluno'.");
                } else {
                    // Não achou em nenhuma
                    areaRelatorio.setText("Aluno '" + nomeParaBuscar + "' não encontrado.");
                    this.nomeDoAlunoEmEdicao = null;
                }
            }
        } catch (SQLException e) {
            areaRelatorio.setText("[ERRO DE BANCO] " + e.getMessage());
        }
    }

    // --- 7. Método de Ajuda (Não muda) ---
    private void limparCampos() {
        campoNome.setText("");
        campoNota1.setText("");
        campoNota2.setText("");
        campoBolsa.setText("");
        radioComum.setSelected(true);
        campoBolsa.setDisable(true);
        this.nomeDoAlunoEmEdicao = null;
        campoDeletarNome.setText("");
    }
}