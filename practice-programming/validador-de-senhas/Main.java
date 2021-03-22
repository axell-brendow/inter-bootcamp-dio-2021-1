import java.util.Scanner;

// URI: https://www.urionlinejudge.com.br/judge/pt/problems/view/2253

public class Main
{
    public static Scanner scan;

    public static void main(String[] args)
    {
        try
        {
            scan = new Scanner(System.in);
            while (true)
            {
                var line = scan.nextLine();

                var valid = line.length() >= 6 && line.length() <= 32
                    && line.matches(".*[A-Z].*") && line.matches(".*[a-z].*")
                    && line.matches(".*[0-9].*") && line.matches("^[a-zA-Z0-9]+$");

                System.out.println(valid ? "Senha valida." : "Senha invalida.");
            }
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            scan.close();
        }
    }
}
