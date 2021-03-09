import java.util.Scanner;
import java.util.regex.Pattern;

// URI: https://www.urionlinejudge.com.br/judge/en/problems/view/2087

public class Main
{
    public static Scanner scan;

    public static boolean conjuntoBom(int size)
    {
        var words = new String[size];
        var line = scan.nextLine();
        words[0] = line;
        for (int i = 1; i < size; i++)
        {
            var nextLine = scan.nextLine();
            words[i] = nextLine;
            line += " " + nextLine;
        }
        for (var word : words)
        {
            var matcher = Pattern.compile("\\b" + word).matcher(line);
            var count = matcher.results().count();
            if (count > 1) return false;
        }
        return true;
    }

    public static void main(String[] args)
    {
        scan = new Scanner(System.in);
        var size = Integer.parseInt(scan.nextLine());
        while (size != 0)
        {
            System.out.println(conjuntoBom(size) ? "Conjunto Bom" : "Conjunto Ruim");
            size = Integer.parseInt(scan.nextLine());
        }
        scan.close();
    }
}
