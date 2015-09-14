
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rene Anda Nielsen <rann@itu.dk>
 * @author Aaron Gornott <agor@itu.dk>
 * @author Sarah de Voss <satv@itu.dk>
 */
public class ClosestPoints {

    static List<Point> points = new ArrayList();
    static List<Point> pointsSortedByY = new ArrayList<>();
    static boolean sortByX = true;

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
        
        
        // sort by x
        Collections.sort(points);
        closestPair(points);
    }

    // CLOSEST-PAIR (p1, p2, …, pn)
    public static Pair closestPair(List<Point> list) {
        // Compute separation line L such that half the points are on each side of the line. 
        //L = rightmost x in left half
        Double lineL = list.get((list.size() / 2) + (list.size() % 2)).x;
        
        if(list.size() < 4){
            Pair minimumPair = bruteForce(list);
            return minimumPair;
        }
        // δ1 ← CLOSEST-PAIR (points in left half).
        Pair delta1 = closestPair(list.subList(0, (list.size() / 2)));
        // δ2 ← CLOSEST-PAIR (points in right half).
        Pair delta2 = closestPair(list.subList((list.size() / 2) + 1, list.size()));
        // δ ← min { δ1 , δ2 }.
        Pair delta = delta1.distance() < delta2.distance() ? delta1 : delta2;
        // Delete all points further than δ from line L.
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).compareTo(new Point(lineL, list.get(i).y)) > delta.distance()) {
                list.remove(i);
            }

        }
        // Sort remaining points by y-coordinate.
        sortByX = false;
        
        // Scan points in y-order and compare distance between
        // each point and next 11 neighbors. If any of these
        // distances is less than δ, update δ.
        // RETURN δ.
        return new Pair(list.get(0), list.get(0));
    }
    private static Pair bruteForce (List<Point> list){
        Pair minimumPair = new Pair(new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        for (int i=0; i< list.size(); i++) {
            for (int j=0; j < list.size(); j++) {
                if (list.get(i) != list.get(j)) {
                    Pair currentPair = new Pair(list.get(i), list.get(j));
                    minimumPair = smallestPair(currentPair, minimumPair);
                }
            }
        }
        return minimumPair;
    }
    
    private static Pair smallestPair (Pair pair1, Pair pair2) {
        if (pair1.distance() < pair2.distance()) {
            return pair1;
        } else {
            return pair2;
        }
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

        public Point(double x, double y) {
            this.id = -1;
            this.x = x;
            this.y = y;
        }

        // Euclidean distance 
        public Double distance(Point p) {
            Double distance = Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
            return distance;
        }

        // Sort on x
        @Override
        public int compareTo(Object o) {
            if (sortByX) {
                if (this.x < ((Point) o).x) {
                    return -1;
                }
                if (this.x > ((Point) o).x) {
                    return 1;
                }
                return 0;
            } else {
                if (this.y < ((Point) o).y) {
                    return -1;
                }
                if (this.y > ((Point) o).y) {
                    return 1;
                }
                return 0;
            }
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
