package operacao;

import dominio.Funcoes;
import dominio.Individuo;
import dominio.Populacao;
import java.util.Random;

/**
 * Classe que mantém dados do algorítimo, com taxa de crossover e mutação,
 * função e população corrente ...
 *
 * @author pcollares
 */
public class Algoritmo {

    private static double taxaDeCrossover;
    private static double taxaDeMutacao;
    private static Funcoes funcao;
    private static Populacao populacaoAtual;
    public static int N = 20;

    public static Populacao novaGeracao(Populacao populacao, int geracao, boolean elitismo) {
        Random r;
        Populacao novaPopulacao = new Populacao(populacao.getTamPopulacao(), false);

        if (elitismo) {
            novaPopulacao.setIndividuo(populacao.getIndividuo(0));
        }

        while (novaPopulacao.getNumIndividuos() < novaPopulacao.getTamPopulacao()) {
            Individuo[] pais = new Individuo[2];
            pais[0] = selecaoTorneio(populacao);
            pais[1] = selecaoTorneio(populacao);

            Individuo[] filhos;

            r = new Random();
            if (r.nextDouble() <= taxaDeCrossover) {
                filhos = crossover(pais);
                filhos[0].setGeracao(geracao);
                filhos[1].setGeracao(geracao);
                novaPopulacao.setIndividuo(filhos[0]);
                novaPopulacao.setIndividuo(filhos[1]);
            } else {
                novaPopulacao.setIndividuo(pais[0]);
                novaPopulacao.setIndividuo(pais[1]);
            }
        }

        double aptidaoMedia = novaPopulacao.getMediaAptidao();
        for (int i = 0; i < novaPopulacao.getTamPopulacao(); i++) {
            novaPopulacao.getIndividuo(i).aplicaMutacao(aptidaoMedia);
        }

        novaPopulacao.ordenaPopulacao();
        return novaPopulacao;
    }

    public static Individuo selecaoTorneio(Populacao populacao) {
        int n = 2;
        Random r;
        Populacao populacaoIntermediaria = new Populacao(n, false);

        r = new Random();
        for (int i = 0; i < n; i++) {
            int pos = r.nextInt(populacao.getTamPopulacao());
            populacaoIntermediaria.setIndividuo(populacao.getIndividuo(pos));
        }
        populacaoIntermediaria.ordenaPopulacao();

        return populacaoIntermediaria.getIndividuo(0);
    }

    public static Individuo[] crossover(Individuo pais[]) {

        Individuo filhos[] = new Individuo[2];

        double geneFilho1[] = new double[Algoritmo.N];
        double geneFilho2[] = new double[Algoritmo.N];

        for (int i = 0; i < Algoritmo.N; i++) {
            geneFilho1[i] = crossoverAritimetico(pais[0].getGene()[i], pais[1].getGene()[i], 1);
            geneFilho2[i] = crossoverAritimetico(pais[0].getGene()[i], pais[1].getGene()[i], 2);
        }

        filhos[0] = new Individuo(geneFilho1);
        filhos[1] = new Individuo(geneFilho2);

        return filhos;
    }

    public static double crossoverAritimetico(double gene1, double gene2, int parte) {

        double beta = 0.3;
        double geneFilho;

        if (parte == 1) {
            geneFilho = beta * gene1 + (1 - beta) * gene2;
        } else {
            geneFilho = (1 - beta) * gene1 + beta * gene2;
        }

        return geneFilho;
    }

    public static double getTaxaDeCrossover() {
        return taxaDeCrossover;
    }

    public static void setTaxaDeCrossover(double taxaDeCrossover) {
        Algoritmo.taxaDeCrossover = taxaDeCrossover;
    }

    public static double getTaxaDeMutacao() {
        return taxaDeMutacao;
    }

    public static void setTaxaDeMutacao(double taxaDeMutacao) {
        Algoritmo.taxaDeMutacao = taxaDeMutacao;
    }

    public static Funcoes getFuncao() {
        return funcao;
    }

    public static void setFuncao(Funcoes funcao) {
        Algoritmo.funcao = funcao;
    }

    public static Populacao getPopulacaoAtual() {
        return populacaoAtual;
    }

    public static void setPopulacaoAtual(Populacao populacaoAtual) {
        Algoritmo.populacaoAtual = populacaoAtual;
    }
}