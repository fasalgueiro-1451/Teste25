// Este é um programa novo e separado, na pasta src/main/java
// chamado AtividadeMetodos.java

public class AtividadeMetodos {

    // --- REQUISITO 1: O MÉTODO "void" (Procedimento) ---
    //
    // "void" significa que este método FAZ uma ação,
    // mas NÃO DEVOLVE (return) nenhum valor.

    public static void exibirMensagem(String nome) {
        System.out.println("Olá, " + nome + "! Seja bem-vindo ao sistema!");
    }

    // --- REQUISITO 2: O MÉTODO COM RETORNO (Função) ---
    //
    // "int" significa que este método DEVOLVE (return)
    // um valor do tipo inteiro.

    public static int calcularQuadrado(int numero) {
        int resultado = numero * numero;
        return resultado; // Devolve o valor calculado
    }

    // --- O "PLAY" (Método Principal) ---
    // O 'main' vai chamar os dois métodos que criamos.

    public static void main(String[] args) {

        System.out.println("--- Executando a Atividade Prática ---");

        // 1. Chamando o método 'void' (Procedimento)
        // Nós passamos o seu nome como parâmetro
        exibirMensagem("Fabiola Salgueiro");


        // 2. Chamando a 'int' (Função)
        int numeroParaTestar = 7;

        // Precisamos de uma variável para "pegar" o valor que a função retornou
        int quadradoDoNumero = calcularQuadrado(numeroParaTestar);

        // Imprimindo o resultado
        System.out.println("O quadrado de " + numeroParaTestar + " é: " + quadradoDoNumero);

        // Você também pode chamar a função diretamente dentro do println:
        System.out.println("O quadrado de 10 é: " + calcularQuadrado(10));

        System.out.println("--- Fim da Atividade ---");
    }
}
