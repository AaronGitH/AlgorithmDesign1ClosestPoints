
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

    static List<Point> pX = new ArrayList();
    static List<Point> pY;
    static boolean sortByY = false;

    public static void main(String[] args) throws FileNotFoundException {
        //File file = new File("linhp318-tsp.txt");
        //Scanner sc = new Scanner(file);
        Scanner sc = new Scanner(System.in);
        int id = 1;
        while (sc.hasNextLine()) {
            String[] fields = sc.nextLine().trim().replaceAll("\\s+", " ").split(" ");
            if (fields[0].compareTo(id + "") == 0 && fields.length==3) {
                double x = Double.parseDouble(fields[1]);
                double y = Double.parseDouble(fields[2]);
                Point p = new Point(id, x, y);
                pX.add(p);
                id++;
            }
        }

        // copy pX to pY
        pY = new ArrayList<>(pX);
        // sort pX by x
        Collections.sort(pX);
        // sort pY by y
        sortByY = true;
        Collections.sort(pY);

        Pair closestPair = closestPairRec(pX, pY);
        System.out.println("Points: " + closestPair.p1.id + " & " + closestPair.p2.id + "\nDistance: " + closestPair.distance());
    }

    // CLOSEST-PAIR (p1, p2, …, pn)
    private static Pair closestPairRec(List<Point> pointsSortedByX, List<Point> pointsSortedByY) {

        if (pointsSortedByX.size() <= 3) {
            return bruteForce(pointsSortedByX);
        }

        int lineL = pointsSortedByX.size() / 2;

        // divide pX into qX and rX
        List<Point> qX = new ArrayList<>(pointsSortedByX.subList(0, lineL));
        List<Point> rX = new ArrayList<>(pointsSortedByX.subList(lineL + 1, pointsSortedByX.size()));

        // create temporary list containing first the points in Q then the points in R
        List<Point> temp = new ArrayList<>(qX);
        // sorts by Y
        Collections.sort(temp);
        // find closest pair in Q
        Pair closestPair = closestPairRec(qX, temp);

        temp.clear();
        temp.addAll(rX);
        // sorts by Y
        Collections.sort(temp);
        // find closest pair in R
        Pair closestPairR = closestPairRec(rX, temp);

        // if distance in R is smaller than the distance in Q then update closestPair
        if (closestPairR.distance() < closestPair.distance()) {
            closestPair = closestPairR;
        }

        // construct sY = points in band
        List<Point> sY = new ArrayList<>();
        double delta = closestPair.distance();
        for (Point point : pointsSortedByY) {
            if (Math.abs(rX.get(0).x - point.x) < delta) {
                sY.add(point);
            }
        }

        for (int i = 0; i < sY.size(); i++) {
            Point point1 = sY.get(i);
            // compare with 11 neighbours
            for (int j = i + 1; j < i + 11; j++) {
                if (j == sY.size()) {
                    break;
                }
                Point point2 = sY.get(j);
                if (Math.abs(point2.y - point1.y) >= delta) {
                    break;
                }
                double distance = point1.distance(point2);
                if (distance < closestPair.distance()) {
                    closestPair = new Pair(point1, point2);
                    delta = distance;
                }
            }
        }
        return closestPair;
    }

    private static Pair bruteForce(List<Point> points) {
        Pair minimumPair = new Pair(new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (points.get(i) != points.get(j)) {
                    Pair currentPair = new Pair(points.get(i), points.get(j));
                    minimumPair = shortestDistance(currentPair, minimumPair);
                }
            }
        }
        return minimumPair;
    }

    private static Pair shortestDistance(Pair pair1, Pair pair2) {
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
            if (sortByY) {
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
