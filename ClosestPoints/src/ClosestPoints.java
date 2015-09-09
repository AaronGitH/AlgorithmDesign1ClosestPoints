
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

    public static void closestPair(ArrayList<Point> list) {
        
 
        

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
