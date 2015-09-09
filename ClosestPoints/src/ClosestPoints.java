
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Rene Anda Nielsen <rann@itu.dk>
 * @author Aaron Gornott <agor@itu.dk>
 * @author Sarah de Voss <satv@itu.dk>
 */
public class ClosestPoints {

    static ArrayList<Point> points = new ArrayList();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("a280-tsp.txt");
        Scanner sc = new Scanner(file); // (System.in);
        int id = 1;
        while (sc.hasNextLine()) {
            String[] fields = sc.nextLine().trim().replaceAll("\\s+", " ").split(" ");
            if (fields[0].compareTo(id + "") == 0) {
                double x = Double.parseDouble(fields[1]);
                double y = Double.parseDouble(fields[2]);
                Point p = new Point(id, x, y);
                points.add(p);
                id++;
            }
        }
        Collections.sort(points);
        closestPair(points);
    }
    
    // CLOSEST-PAIR (p1, p2, …, pn)
    public static Pair closestPair(ArrayList<Point> list){
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
        return new Pair(list.get(0), list.get(0));
    }

    
    private static class Point implements Comparable {

        int id;
        double x;
        double y;

        public Point(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public Double distance(Point p) {
            Double distance = Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
            return distance;
        }

        @Override
        public int compareTo(Object o) {
            if (this.x < ((Point) o).x) {
                return -1;
            }
            if (this.x > ((Point) o).y) {
                return 1;
            }
            return 0;
        }
    }
    
    private static class Pair {
        Point p1;
        Point p2;
        
        public Pair(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
        
        public Double distance() {
            return p1.distance(p2);
        }
    }
}
