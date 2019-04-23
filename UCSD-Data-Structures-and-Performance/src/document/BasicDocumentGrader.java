package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

import document.BasicDocument;

public class BasicDocumentGrader {
    public static void main(String[] args) {
        try
        {
            System.out.println("Sentences, words, and syllables:");
            BufferedReader br = new BufferedReader(new FileReader("test_cases/mod1TestCases.txt"));
            String line;
            PrintWriter out = new PrintWriter("grader_output/module1.part1.out", "utf-8");
            while ((line = br.readLine()) != null)
            {
                BasicDocument doc = new BasicDocument(line);
                String result = doc.getNumSentences() + " " + doc.getNumWords() + " " + doc.getNumSyllables() + " ";
                System.out.print(result);
                out.print(result);
            }
            out.print("\n");
            out.close();
            System.out.println("\nFlesch scores:");
            br.close();

            br = new BufferedReader(new FileReader("test_cases/mod1TestCases.txt"));
            out = new PrintWriter("grader_output/module1.part2.out", "utf-8");
            while ((line = br.readLine()) != null)
            {
                BasicDocument doc = new BasicDocument(line);
                String result = doc.getFleschScore() + " ";
                System.out.print(result);
                out.print(result);
            }
            out.print("\n");
            out.close();
            System.out.print('\n');
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
