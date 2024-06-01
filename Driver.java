import java.io.File;

public class Driver {
  
    public static void main(String [] args) {
      Polynomial p = new Polynomial();

      double [] c1 = {4, 3, 45.1, 5.5};
      int [] e1 = {5, 6, 4, 3};
      Polynomial p1 = new Polynomial(c1, e1);
      p1.printPolynomial();

      double [] c2 = {1, 1, -1, 0, 49};
      int [] e2 = {1, 2, 5, 4, 9};
      Polynomial p2 = new Polynomial(c2, e2);
      p2.printPolynomial();

      Polynomial p3 = new Polynomial(new File("test.txt"));
      p3.printPolynomial();

      Polynomial p1_p2 = p1.add(p2);
      p1_p2.printPolynomial();
      
      Polynomial p1_p2_p2 = p1_p2.add(p2);
      p1_p2_p2.printPolynomial();

      Polynomial p1p2 = p1.multiply(p2);
      p1p2.printPolynomial();

      Polynomial p1p3 = p1.multiply(p3);
      p1p3.printPolynomial();

      p1p3.saveToFile("product.txt");

      System.out.println(p1p3.evaluate(1));
    }
}