
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rene Anda Nielsen <rann@itu.dk>
 * @author Aaron Gornott <agor@itu.dk>
 * @author Sarah de Voss <satv@itu.dk>
 */
public class CP {

    static List<Point> pX = new ArrayList();
    static boolean sortByY = false;
    static boolean readPoints = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String[] fields = sc.nextLine().trim().replaceAll("\\s+", " ").split(" ");

            if (fields[0].equalsIgnoreCase("NODE_COORD_SECTION")) {
                readPoints = true;
            }

            if (readPoints && fields.length == 3) {
                double x = Double.parseDouble(fields[1]);
                double y = Double.parseDouble(fields[2]);
                pX.add(new Point(x, y));
            }
        }
        sc.close();

        // sort pX by x
        Collections.sort(pX);
        // from now on sort by Y
        sortByY = true;

        double d = closestPairRec(pX);
        System.out.println(d);

    }

    private static double closestPairRec(List<Point> pointsSortedByX) {

        if (pointsSortedByX.size() <= 3) {
            return bruteForce(pointsSortedByX);
        }
        // calculate separation line L
        int lineL = pointsSortedByX.size() / 2;

        // divide pX into qX and rX
        List<Point> qX = pointsSortedByX.subList(0, lineL);
        List<Point> rX = pointsSortedByX.subList(lineL, pointsSortedByX.size());

        // find closest pair in Q
        double d1 = closestPairRec(qX);

        // find closest pair in R
        double d2 = closestPairRec(rX);

        double d = Math.min(d1,d2);

        // construct sY = points in band
        List<Point> sY = new ArrayList<>();

        for (Point point : pointsSortedByX) {
            if (Math.abs(point.x - qX.get(lineL - 1).x) < d) {
                sY.add(point);
            }
        }
        
        Collections.sort(sY);

        for (int i = 0; i < sY.size(); i++) {
            Point point1 = sY.get(i);
            // compare with 11 neighbours
            for (int j = i + 1; j <= i + 11; j++) {
                if (j == sY.size()) {
                    break;
                }
                Point point2 = sY.get(j);

                if (point1.distance(point2) < d) {
                    d = point1.distance(point2);
                }
            }
        }
        return d;
    }

    private static double bruteForce(List<Point> points) {
        Pair minimumPair = new Pair(new Point(0, 0), new Point(0, Double.POSITIVE_INFINITY));
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (points.get(i) != points.get(j)) {
                    Pair currentPair = new Pair(points.get(i), points.get(j));
                    minimumPair = shortestDistance(currentPair, minimumPair);
                }
            }
        }
        return minimumPair.distance();
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
            this.x = x;
            this.y = y;
        }

        // Euclidean distance 
        public Double distance(Point other) {
            Double distance = Math.hypot(this.x - other.x, this.y - other.y);
            return distance;
        }

        // Sort on x
        @Override
        public int compareTo(Object o) {
            if (sortByY) {
                if (this.y < ((Point) o).y) {
                    return -1;
                }
                if (this.y > ((Point) o).y) {
                    return 1;
                }
                return 0;
            } else {
                if (this.x < ((Point) o).x) {
                    return -1;
                }
                if (this.x > ((Point) o).x) {
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
