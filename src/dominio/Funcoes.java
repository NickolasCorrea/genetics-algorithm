package dominio;

import java.util.Random;

/**
 * Classe que define as funções usadas no AG
 *
 * @author pcollares
 */
public class Funcoes {

    private int numFuncao;
    private int maximoGeracoes;
    private double min;
    private double max;
    private int incremento;
    private double incrementoGrafico;
    private double minimoDaFuncao;

    public Funcoes(int numFuncao, double min, double max, int maximoGeracoes) {
        this.numFuncao = numFuncao;
        this.min = min;
        this.max = max;

        this.setMaximoGeracoes(maximoGeracoes);

        if (numFuncao == 8) {
            minimoDaFuncao = -12569.5;
        } else {
            minimoDaFuncao = 0;
        }

        incrementoGrafico = (Math.abs(min) + max) / 30;

    }

    public double resolve(double x[]) {
        double resultado = 0;
        switch (numFuncao) {
            case 89:
                resultado = f89(x);
                break;
        }
        return resultado;
    }

    private double f89(double x[]) {
        double sum1 = 0.0;
        double sum2 = 0.0;
        double sum3 = 0.0;
        double resultado = 0;

        int D = x.length;

        // Tratando as condições de contorno
        double x0 = x[D - 1]; // x0 = xD
        double xDplus1 = x[0]; // xD+1 = x1

        for (int i = 0; i < D; i++) {
            // Calculando A e B com condições de contorno
            double xi = x[i];
            double xiMinus1 = (i == 0) ? x0 : x[i - 1];
            double xiPlus1 = (i == D - 1) ? xDplus1 : x[i + 1];

            double A = (xiMinus1 * Math.sin(xi) + Math.sin(xiPlus1));
            double B = Math.pow(xiMinus1, 2) - 2 * xi + 3 * xiPlus1 - Math.cos(xi) + 1;

            sum1 += i * Math.pow(xi, 2);
            sum2 += 20 * Math.sin(A) * Math.sin(A);
            sum3 += Math.log10(1 + (i + 1) * Math.pow(B, 2));
        }

        resultado = sum1 + sum2 + sum3;
        return resultado;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public int getIncremento() {
        return incremento;
    }

    public int getMaximoGeracoes() {
        return maximoGeracoes;
    }

    public void setMaximoGeracoes(int maximoGeracoes) {
        this.maximoGeracoes = maximoGeracoes;
        incremento = maximoGeracoes / 20;
    }

    public double getMinimoDaFuncao() {
        return minimoDaFuncao;
    }

    public double getIncrementoGrafico() {
        return incrementoGrafico;
    }

    public int getNumFuncao() {
        return numFuncao;
    }

    @Override
    public String toString() {
        return "F" + numFuncao + "(x) :: [" + min + "," + max + "] :: fmin=" + minimoDaFuncao;
    }
}
