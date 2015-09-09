
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
    
    // CLOSEST-PAIR (p1, p2, …, pn)
    public static void closestPair(){
    // Compute separation line L such that half the points
    // are on each side of the line.
    // δ1 ← CLOSEST-PAIR (points in left half).
    // δ2 ← CLOSEST-PAIR (points in right half).
    // δ ← min { δ1 , δ2 }.
    // Delete all points further than δ from line L.
    // Sort remaining points by y-coordinate.
    // Scan points in y-order and compare distance between
    // each point and next 11 neighbors. If any of these
    // distances is less than δ, update δ.
    // RETURN δ.
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
