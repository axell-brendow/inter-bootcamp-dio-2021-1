import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// URI 2700 Angariando Fundos: https://www.urionlinejudge.com.br/judge/pt/problems/view/2700
// Fenwick tree (pt-br): https://www.youtube.com/watch?v=d5PBN2hf2Xo

class Pair implements Comparable<Pair>
{
    public int beauty;
    public int fortune;

    public Pair(int beauty, int fortune)
    {
        this.beauty = beauty;
        this.fortune = fortune;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (beauty ^ (beauty >>> 32));
        result = prime * result + (int) (fortune ^ (fortune >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (beauty != other.beauty)
            return false;
        if (fortune != other.fortune)
            return false;
        return true;
    }

    @Override
    public int compareTo(Pair o) {
        return beauty == o.beauty
            ? fortune - o.fortune
            : beauty - o.beauty;
    }

    @Override
    public String toString() {
        return "Pair [beauty=" + beauty + ", fortune=" + fortune + "]";
    }
}

class Triple implements Comparable<Triple>
{
    public int beauty;
    public int fortune;
    public long donation;

    public Triple(int beauty, int fortune, long donation)
    {
        this.beauty = beauty;
        this.fortune = fortune;
        this.donation = donation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (beauty ^ (beauty >>> 32));
        result = prime * result + (int) (donation ^ (donation >>> 32));
        result = prime * result + (int) (fortune ^ (fortune >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Triple other = (Triple) obj;
        if (beauty != other.beauty)
            return false;
        if (donation != other.donation)
            return false;
        if (fortune != other.fortune)
            return false;
        return true;
    }

    @Override
    public int compareTo(Triple o) {
        return beauty == o.beauty && fortune == o.fortune
            ? (int)(donation - o.donation)
            : beauty == o.beauty
                ? fortune - o.fortune
                : beauty - o.beauty;
    }

    @Override
    public String toString() {
        return "Triple [beauty=" + beauty + ", donation=" + donation + ", fortune=" + fortune + "]";
    }
}

public class Main
{
    private static long[] cumulativeDonations; // bit

    public static long donationsFromLessOrEqualRichThan(int fortune)
    {
        long donations = 0;
        for(; fortune > 0; fortune -= fortune & -fortune)
            donations = Math.max(donations, cumulativeDonations[fortune]);
        return donations;
    }

    public static void updateFenwickTree(int fortune, long cumulativeDonation, int numFortunes)
    {
        for (; fortune < numFortunes; fortune += fortune & -fortune)
            cumulativeDonations[fortune] = Math.max(
                cumulativeDonations[fortune], cumulativeDonation
            );
    }

    public static void main(String[] args)
    {
        try (Scanner scan = new Scanner(System.in))
        {
            int numPersons = scan.nextInt();
            HashMap<Pair, Long> beautyFortuneToDonation = new HashMap<>(numPersons); // mapa
            HashMap<Integer, Integer> fortuneToFenwickIndex = new HashMap<>(numPersons); // C
            ArrayList<Integer> fortunes = new ArrayList<>(numPersons); // vv
            ArrayList<Triple> beautyFortuneDonationAux = new ArrayList<>(numPersons); // vaux
            ArrayList<Triple> beautyFortuneDonation = new ArrayList<>(); // v

            for (int i = 0; i < numPersons; i++) {
                int beauty = scan.nextInt(), fortune = scan.nextInt();
                long donation = scan.nextLong();
                beautyFortuneDonationAux.add(new Triple(beauty, fortune, donation));
                fortunes.add(fortune);
            }

            int[] fenwickIndex = new int[] { 1 };
            fortunes.sort(Integer::compareTo);
            fortunes.forEach((fortune) -> {
                if (!fortuneToFenwickIndex.containsKey(fortune))
                    fortuneToFenwickIndex.put(fortune, fenwickIndex[0]++);
            });
            int numFortunes = fenwickIndex[0];

            for (int i = 0; i < numPersons; i++) {
                Triple triple = beautyFortuneDonationAux.get(i);
                int fortuneFenwickIndex = fortuneToFenwickIndex.get(triple.fortune);
                Pair pair = new Pair(triple.beauty, fortuneFenwickIndex);
                Long currentCumulativeDonation = beautyFortuneToDonation.get(pair);
                beautyFortuneToDonation.put(
                    pair,
                    currentCumulativeDonation == null
                        ? triple.donation
                        : currentCumulativeDonation + triple.donation
                );
            }

            beautyFortuneToDonation.forEach((key, value) -> {
                beautyFortuneDonation.add(new Triple(key.beauty, key.fortune, value));
            });

            beautyFortuneDonation.sort(Triple::compareTo);

            beautyFortuneDonationAux.clear();
            int lastBeauty = -1;
            int cumulativeDonationsLength = numFortunes + 2;
            cumulativeDonations = new long[cumulativeDonationsLength];

            for (int i = 0; i < beautyFortuneDonation.size();) {
                Triple triple = beautyFortuneDonation.get(i);

                do {
                    lastBeauty = triple.beauty;
                    long donationsFromLessBeautifulAndLessRich =
                        donationsFromLessOrEqualRichThan(triple.fortune - 1);
    
                    beautyFortuneDonationAux.add(
                        new Triple(
                            triple.beauty,
                            triple.fortune,
                            triple.donation + donationsFromLessBeautifulAndLessRich
                        )
                    );
                    i++;

                    if (i == beautyFortuneDonation.size()) break;
                    triple = beautyFortuneDonation.get(i);
                } while (lastBeauty == triple.beauty);

                for (int j = 0; j < beautyFortuneDonationAux.size(); j++) {
                    triple = beautyFortuneDonationAux.get(j);
                    updateFenwickTree(
                        triple.fortune, triple.donation, cumulativeDonationsLength
                    );
                }

                beautyFortuneDonationAux.clear();
            }
            
            System.out.println(donationsFromLessOrEqualRichThan(cumulativeDonationsLength - 1));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
