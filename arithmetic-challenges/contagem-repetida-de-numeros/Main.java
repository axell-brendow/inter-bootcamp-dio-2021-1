import java.util.Scanner;
import java.util.TreeMap;

// URI: https://www.urionlinejudge.com.br/judge/en/problems/view/1171

public class Main {
	public static void main(String[] args) {
        try (final var sc = new Scanner(System.in)) {
            final var n = sc.nextInt();
    
            var numAndQuantity = new TreeMap<Integer, Integer>();
    
            for (int i = 0; i < n; i++)
                numAndQuantity.compute(sc.nextInt(), (k, v) -> v == null ? 1 : v + 1);
    
            numAndQuantity.entrySet().forEach(entry->{
                System.out.printf("%d aparece %d vez(es)\n", entry.getKey(), entry.getValue());  
            });
        }
	}
}
