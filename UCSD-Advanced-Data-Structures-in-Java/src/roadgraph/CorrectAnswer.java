package roadgraph;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import geography.*;

public class CorrectAnswer {
    public int vertices;
    public int edges;
    public List<GeographicPoint> path;
    public CorrectAnswer(String file, boolean hasEdges) {
        try {
            Scanner s = new Scanner(new File(file));
            s.useLocale(Locale.ENGLISH);
            if (hasEdges) {
                vertices = s.nextInt();
                edges = s.nextInt();
            }
            path = null;
            if (s.hasNextDouble()) {
                path = new ArrayList<GeographicPoint>();
            }
            while (s.hasNextDouble()) {
                double x = s.nextDouble();
                double y = s.nextDouble();
                path.add(new GeographicPoint(x, y));
            }
        } catch (Exception e) {
            System.err.println("Error reading correct answer!");
            e.printStackTrace();
        }
    }
}
