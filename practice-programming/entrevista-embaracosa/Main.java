import java.util.Scanner;
import java.util.regex.Pattern;

// URI: https://www.urionlinejudge.com.br/judge/pt/problems/view/2153

public class Main
{
    public static Scanner scan;

    public static void getPossiblePrefixes(String line, String doubleStr)
    {
        var fixedLine = line.replaceFirst(doubleStr + "$", "");
        System.out.println(fixedLine);
        var size = doubleStr.length();

        for (int i = 1; i < size; i++)
        {
            doubleStr = doubleStr.substring(1);
            if (!line.matches(".*" + doubleStr + doubleStr + "$")) continue;

            fixedLine = line.replaceFirst(doubleStr + "$", "");
            System.out.println(fixedLine);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            scan = new Scanner(System.in);
            while (true)
            {
                var line = scan.nextLine();
                var m = Pattern.compile("(.*)\\1$").matcher(line);
    
                String doubleStr = null;
                while (m.find())
                    if (!m.group().isEmpty())
                        doubleStr = m.group();

                if (doubleStr == null) System.out.println(line);
                else
                {
                    doubleStr = doubleStr.substring(doubleStr.length() / 2);
                    getPossiblePrefixes(line, doubleStr);
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            scan.close();
        }
    }
}
