public class Polynomial {
    // Question: should I be using public?

    // Fields
    double[] coefficients;

    // Methods
    
    public Polynomial() {
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
    }
    
    public Polynomial(double[] coefficients) {
        this.coefficients = new double[coefficients.length];
        this.coefficients = coefficients; 
    }

    public Polynomial add(Polynomial polynomial) {
        int p1Length = this.coefficients.length;
        int p2Length = polynomial.coefficients.length;
        int maxLength = Math.max(p1Length, p2Length);

        double[] newCoefficients = new double[maxLength];

        for (int i=0; i<maxLength; i++) {
            
            if (i < p1Length && i < p2Length) {
                newCoefficients[i] = this.coefficients[i] + polynomial.coefficients[i];
            }
            else if (p1Length > p2Length) {
                newCoefficients[i] = this.coefficients[i];
            }
            else {
                newCoefficients[i] = polynomial.coefficients[i];
            }
        }
        return new Polynomial(newCoefficients);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i=0; i<coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
