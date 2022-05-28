package lt.viko.eif;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

public class GcdAlgorithm {
    public void encrypt(int p, int q, int text) {
        int n;
        int f;
        int d = 0;
        int e;
        int i;
        double c; // number to be encrypted and decrypted

        n = p * q;
        f = (p - 1) * (q - 1);
        System.out.println("the value of f = " + f);

        for (e = 2; e < f; e++) { //public key
            if (gcd(e, f) == 1) {
                AtomicInteger x = new AtomicInteger();
                AtomicInteger y = new AtomicInteger();

                System.out.printf("gcd(%d, %d) = " + extendedGcd(e, f, x, y) + '\n', e, f);
                System.out.printf("Extended GCD: x = %d, y = %d\n", x.get(), y.get());
                break;
            }
        }
        System.out.println("the value of e (public key) = " + e);
        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * f);
            if (x % e == 0) { //private key
                d = x / e;
                break;
            }
        }

        System.out.println("the value of d (private key) = " + d);
        c = (Math.pow(text, e)) % n;

        File encFile = new File("src/main/resources/encrypted.txt");
        try {
            Files.write(Path.of(encFile.getPath()), ((int) c + " " + e + " " + d).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Encrypted message is : " + (int) c);
    }

    public void decrypt(File file, int n) {
        String inf;
        try {
             inf = Files.readString(Path.of(file.getPath()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String[] values = inf.split(" ");

        int c = Integer.parseInt(values[0]);
        int e = Integer.parseInt(values[1]);
        int d = Integer.parseInt(values[2]);

        BigInteger N = BigInteger.valueOf(n);
        BigInteger C = BigDecimal.valueOf(c).toBigInteger();

        BigInteger msgBack = (C.pow(d)).mod(N);

        try {
            Files.write(Path.of("src/main/resources/decrypted.txt"), String.valueOf(msgBack).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Decrypted message is : " + msgBack);
    }


    private int gcd(int e, int z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }

    private int extendedGcd(int a, int b, AtomicInteger x, AtomicInteger y) {
        if (a == 0) {
            x.set(0);
            y.set(1);
            return b;
        }

        AtomicInteger _x = new AtomicInteger();
        AtomicInteger _y = new AtomicInteger();
        int gcd = extendedGcd(b % a, a, _x, _y);

        x.set(_y.get() - (b / a) * _x.get());
        y.set(_x.get());

        return gcd;
    }
}
