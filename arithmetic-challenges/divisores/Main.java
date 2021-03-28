import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

// URI: https://www.urionlinejudge.com.br/judge/pt/problems/view/2238

public class Main {
    public static void main(String[] args) {
        try (var scan = new Scanner(System.in)) {
            SortedSet<Long> possiveisN = new TreeSet<Long>();

            long divisorDeNA = scan.nextLong();
            long naoDivisorDeNB = scan.nextLong();
            long multiploDeNC = scan.nextLong();
            long naoMultiploDeND = scan.nextLong();
            
            final var raizDoMultiplo = Math.sqrt(multiploDeNC);

            for (int i = 1; i < raizDoMultiplo; i++) {
                if (multiploDeNC % i != 0) continue;

                final var fatorDoMultiplo = multiploDeNC / i;

                if (
                    i % divisorDeNA == 0
                    && i % naoDivisorDeNB != 0
                    && naoMultiploDeND % i != 0
                )
                    possiveisN.add((long) i);

                if (
                    fatorDoMultiplo % divisorDeNA == 0
                    && fatorDoMultiplo % naoDivisorDeNB != 0
                    && naoMultiploDeND % fatorDoMultiplo != 0
                )
                    possiveisN.add(fatorDoMultiplo);
            }

            System.out.println(possiveisN.size() == 0 ? "-1" : "" + possiveisN.first());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
