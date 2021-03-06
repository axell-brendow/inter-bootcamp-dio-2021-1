import java.util.Scanner;

// URI: https://www.urionlinejudge.com.br/judge/en/problems/view/1519

public class Main
{
    public static boolean newWordIsBetter(String current, String newWord, String line)
    {
        return (
            line.replaceAll("\\b" + newWord + "\\b", "-.").length()
            < line.replaceAll("\\b" + current + "\\b", "-.").length()
        );
    }

    public static void main(String[] args)
    {
        try (var scan = new Scanner(System.in))
        {
            var line = scan.nextLine()
                .trim()
                .toLowerCase()
                .replaceAll("\n", " ")
                .replaceAll("\t", " ");

            while (!line.equals("."))
            {
                var wordsToReplace = new String[26];
                var words = line.split(" ");
                for (var word : words)
                {
                    if (word.length() <= 2) continue;

                    int charIndex = word.charAt(0) - 'a';

                    if (
                        wordsToReplace[charIndex] == null
                        || newWordIsBetter(wordsToReplace[charIndex], word, line)
                    )
                        wordsToReplace[charIndex] = word;
                }
                int N = 0;
                for (int i = 0; i < wordsToReplace.length; i++)
                {
                    if (wordsToReplace[i] == null) continue;
                    N++;
                    line = line.replaceAll("\\b" + wordsToReplace[i] + "\\b", (char)('a' + i) + ".");
                }
                System.out.println(line);
                System.out.println(N);
                for (int i = 0; i < wordsToReplace.length; i++)
                {
                    if (wordsToReplace[i] == null) continue;

                    System.out.println(String.format("%c. = %s", 'a' + i, wordsToReplace[i]));
                }
                line = scan.nextLine()
                    .trim()
                    .toLowerCase()
                    .replaceAll("\n", " ")
                    .replaceAll("\t", " ");
            }
        }
    }
}
