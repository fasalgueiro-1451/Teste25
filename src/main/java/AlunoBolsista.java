// Este é o código COMPLETO do AlunoBolsista.java
public class AlunoBolsista extends Aluno {

    private double valorBolsa;

    // Construtor
    public AlunoBolsista(String nome, double n1, double n2, double bolsa) {
        // "super" chama o construtor do PAI (Aluno)
        super(nome, n1, n2);
        this.setValorBolsa(bolsa);
    }

    // Getters e Setters da Bolsa
    public double getValorBolsa() {
        return this.valorBolsa;
    }
    public void setValorBolsa(double bolsa) {
        if (bolsa >= 0) {
            this.valorBolsa = bolsa;
        } else {
            this.valorBolsa = 0;
            System.out.println("[ERRO] Valor de bolsa inválido (" + bolsa + "). Foi definido 0.");
        }
    }

    // Método próprio
    public void mostrarValorBolsa() {
        System.out.println("Este aluno tem uma bolsa de: R$" + this.valorBolsa);
    }

    // Polimorfismo (Sobrescrita)
    @Override
    public double calcularMedia() {
        System.out.println("(Cálculo especial do bolsista " + this.getNome() + "...)");

        double mediaBase = (this.getNota1() + this.getNota2()) / 2;
        double mediaFinal = mediaBase + 1.0;

        if (mediaFinal > 10.0) {
            mediaFinal = 10.0;
        }

        return mediaFinal;
    }
}
