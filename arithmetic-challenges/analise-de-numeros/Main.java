import java.util.Scanner;

// URI: https://www.urionlinejudge.com.br/judge/en/problems/view/1066

public class Main {
	public static void main(String[] args) {
        try (final var sc = new Scanner(System.in)) {
            int even = 0, odd = 0, positive = 0, negative = 0;

            for (int i = 0; i < 5; i++) {
                final var value = sc.nextInt();
                if (value % 2 == 0) even++; else odd++;
                if (value > 0) positive++;
                else if (value < 0) negative++;
            }

            System.out.println(even + " valor(es) par(es)");
            System.out.println(odd + " valor(es) impar(es)");
            System.out.println(positive + " valor(es) positivo(s)");
            System.out.println(negative + " valor(es) negativo(s)");
        }
	}
}
