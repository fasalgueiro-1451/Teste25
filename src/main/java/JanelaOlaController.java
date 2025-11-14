import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// Este é o cérebro COMPLETO da nossa janela
public class JanelaOlaController {

    // --- 1. A "COLA MÁGICA" (Injeção de @FXML) ---
    //
    // Estas variáveis "ligam" o código Java aos componentes do FXML.
    // O nome DEVE ser idêntico ao "fx:id" que você digitou no Scene Builder.

    @FXML
    private TextField campoNome; // Conecta-se ao fx:id="campoNome"

    @FXML
    private Label labelResultado; // Conecta-se ao fx:id="labelResultado"


    // --- 2. A "AÇÃO" (O que o botão faz) ---
    //
    // Este método é o "On Action" que você digitou no Scene Builder.

    @FXML
    protected void aoClicarBotao() {

        // 1. Pega o texto que está DENTRO do "campoNome"
        String nomeDigitado = campoNome.getText();

        // 2. Coloca o novo texto DENTRO do "labelResultado"
        labelResultado.setText("Olá, " + nomeDigitado + "! Bem-vindo(a) ao JavaFX!");
    }
}