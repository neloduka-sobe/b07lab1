import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Polynomial {

    // Fields
    double[] coefficients;
    int[] exponents;

    // Methods
    public Polynomial() {
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
        this.exponents = new int[1];
        this.exponents[0] = 0;
    }

    public Polynomial(File file) {
        String input = "";

        // Opening file
        try {
            Scanner sc = new Scanner(file);
            input = (String)sc.nextLine();
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Splitting
        input = input.replaceAll("-", "+-");

        // https://stackoverflow.com/questions/16311651/java-string-split-by
        String[] splitted = input.split("\\+");
        
        this.coefficients = new double[splitted.length];
        this.exponents = new int[splitted.length];
        for (int i=0; i<splitted.length; i++) {
            // Parsing
            String[] split = splitted[i].split("x");
            this.coefficients[i] = Double.parseDouble(split[0]);
            if (split.length > 1) {
                this.exponents[i] = Integer.parseInt(split[1]);
            }
            else {
                this.exponents[i] = 0;
            }
        }
    }
    
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = new double[coefficients.length];
        this.coefficients = coefficients;
        this.exponents = new int[exponents.length];
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial polynomial) {
        // Validity check
        if (this.exponents == null && polynomial.exponents == null) {
            return new Polynomial();
        }

        // Find maximum exponent
        int maxExponent = -1;
        for (int i = 0; i < this.exponents.length; i++) {
            if (this.exponents[i] > maxExponent) {
                maxExponent = this.exponents[i];
            }
        }
        for (int i = 0; i < polynomial.exponents.length; i++) {
            if (polynomial.exponents[i] > maxExponent) {
                maxExponent = polynomial.exponents[i];
            }
        }

        double[] tempCoefficients = new double[maxExponent + 1];
        Arrays.fill(tempCoefficients, 0);

        if (this.exponents != null) {
            for (int i = 0; i < this.exponents.length; i++) {
                tempCoefficients[this.exponents[i]] += this.coefficients[i];
            }
        }

        if (polynomial.exponents != null) {
            for (int i = 0; i < polynomial.exponents.length; i++) {
                tempCoefficients[polynomial.exponents[i]] += polynomial.coefficients[i];
            }
        }

        // Counting zero coefficients 
        int numberOfNonZeros = 0;
        for (int i = 0; i < tempCoefficients.length; i++) {
            if (tempCoefficients[i] != 0) {
                numberOfNonZeros++;
            }
        }

        double[] coefficientsRes = new double[numberOfNonZeros];
        int[] exponentsRes = new int[numberOfNonZeros];

        int index = 0;
        for (int i = 0; i < tempCoefficients.length; i++) {
            if (tempCoefficients[i] != 0) {
                coefficientsRes[index] = tempCoefficients[i];
                exponentsRes[index] = i;
                index++;
            }
        }

        return new Polynomial(coefficientsRes, exponentsRes);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i=0; i<this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial polynomial) {
        // Validity check
        if (this.exponents == null && polynomial.exponents == null) {
            return new Polynomial();
        }
        int len1 = this.coefficients.length;
        int len2 = polynomial.coefficients.length;

        Polynomial[] resultPolynomials = new Polynomial[len1];

        for (int index1 = 0; index1 < len1; index1++) {
            double[] newCoefficients = new double[len2];
            int[] newExponents = new int[len2];

            for (int index2 = 0; index2 < len2; index2++) {
                newCoefficients[index2] = this.coefficients[index1] * polynomial.coefficients[index2];
                newExponents[index2] = this.exponents[index1] + polynomial.exponents[index2];
            }

            resultPolynomials[index1] = new Polynomial(newCoefficients, newExponents);
        }

        Polynomial finalSum = resultPolynomials[0];

        for (int k = 1; k < len1; k++) {
            finalSum = finalSum.add(resultPolynomials[k]);
        }

        return finalSum;
    }
    
    public void saveToFile(String filename) {
        // Make it to string
        String text = "";
        for (int i=0; i<coefficients.length ;i++) {
            if (coefficients[i] > 0) {
                if (exponents[i] != 0) {
                    text = String.format("%s+%fx%d", text, coefficients[i], exponents[i]);
                }
                else {
                    text = String.format("%s+%f", text, coefficients[i], exponents[i]);
                }
            }
            else {
                if (exponents[i] != 0) {
                    text = String.format("%s%fx%d", text, coefficients[i], exponents[i]);
                }
                else {
                    text = String.format("%s%f", text, coefficients[i], exponents[i]);
                }
            }
        }

        // Save sting to file
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(text);
            writer.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void printPolynomial() {
        for (int i = 0; i < coefficients.length; i++)
            System.out.print("" + coefficients[i] + "x" + exponents[i] + " ");
            System.out.println();
    }
}