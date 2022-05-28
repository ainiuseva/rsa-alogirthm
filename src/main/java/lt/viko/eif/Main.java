package lt.viko.eif;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        GcdAlgorithm gcd = new GcdAlgorithm();
        int p = 3;
        int q = 5;

        gcd.encrypt(p, q, 8);
        gcd.decrypt(new File("src/main/resources/encrypted.txt"), p * q);
    }

}
