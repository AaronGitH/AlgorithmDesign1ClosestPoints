
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Rene Anda Nielsen <rann@itu.dk>
 * @author Aaron Gornott <agor@itu.dk>
 * @author Sarah de Voss <satv@itu.dk>
 */
public class ClosestPoints {

    static ArrayList<Point> points = new ArrayList();

    public static void main(String[] args) throws FileNotFoundException{
        File file = new File("d198-tsp.txt");
        Scanner sc = new Scanner(file); // (System.in);
        int id = 1;
        while (sc.hasNextLine()){
            String[] fields = sc.nextLine().trim().replaceAll("\\s+"," ").split(" ");
            if (fields[0].compareTo(id + "") == 0) {
                double x = Double.parseDouble(fields[1]);
                double y = Double.parseDouble(fields[2]);
                Point p = new Point(id, x, y);
                points.add(p);
                id++;
            }
        }
    }
    
    private static class Pair{
        
        private final ArrayList<Point> left;
        private final ArrayList<Point> right;
        
        public Pair(ArrayList<Point> left, ArrayList<Point> right){
            this.left = left;
            this.right = right;
        }
    }

    private static class Point{

        final int id;
        final double x, y;

        public Point(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

}
