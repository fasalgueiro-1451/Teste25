// Este é o código COMPLETO do Aluno.java
public class Aluno {

    // Atributos "private" (Encapsulamento)
    private double nota1;
    private double nota2;
    private String nome;

    // Construtor
    public Aluno(String nome, double n1, double n2) {
        this.nome = nome;
        this.setNota1(n1);
        this.setNota2(n2);
    }

    // Getters e Setters (Encapsulamento)
    public String getNome() {
        return this.nome;
    }

    public void setNota1(double nota) {
        if (nota >= 0 && nota <= 10) {
            this.nota1 = nota;
        } else {
            this.nota1 = 0;
            System.out.println("[ERRO] Nota 1 inválida (" + nota + "). Foi definido 0.");
        }
    }
    public void setNota2(double nota) {
        if (nota >= 0 && nota <= 10) {
            this.nota2 = nota;
        } else {
            this.nota2 = 0;
            System.out.println("[ERRO] Nota 2 inválida (" + nota + "). Foi definido 0.");
        }
    }
    public double getNota1() {
        return this.nota1;
    }
    public double getNota2() {
        return this.nota2;
    }

    // Métodos de Ação
    public double calcularMedia() {
        double media = (this.nota1 + this.nota2) / 2;
        return media;
    }

    public String verificarSituacao() {
        double media = this.calcularMedia();
        if (media >= 7.0) {
            return "APROVADO!";
        } else {
            return "REPROVADO.";
        }
    }
}