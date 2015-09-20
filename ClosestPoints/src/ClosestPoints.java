
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
    static List<Point> pY = new ArrayList();
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

        // copy pX to pY
        pY.addAll(pX);
        // sort pX by x
        Collections.sort(pX);
        // sort pY by y
        sortByY = true;
        Collections.sort(pY);

        Pair closestPair = closestPairRec(pX, pY);
        System.out.println(closestPair.distance());

    }

    private static Pair closestPairRec(List<Point> pointsSortedByX, List<Point> pointsSortedByY) {

        if (pointsSortedByX.size() <= 3) {
            return bruteForce(pointsSortedByX);
        }
        // calculate separation line L
        int lineL = pointsSortedByX.size() / 2;

        // divide pX into qX and rX
        List<Point> qX = new ArrayList<>(pointsSortedByX.subList(0, lineL));
        List<Point> rX = new ArrayList<>(pointsSortedByX.subList(lineL, pointsSortedByX.size()));

        // create temporary list containing first the points in Q then the points in R
        List<Point> qY = new ArrayList<>(qX);
        // sorts by Y
        Collections.sort(qY);
        // find closest pair in Q
        Pair closestPair = closestPairRec(qX, qY);

        List<Point> rY = new ArrayList<>(rX);
        // sorts by Y
        Collections.sort(rY);
        // find closest pair in R
        Pair closestPairR = closestPairRec(rX, rY);

        // if distance in R is smaller than the distance in Q then update closestPair
        if (closestPairR.distance() < closestPair.distance()) {
            closestPair = closestPairR;
        }

        // construct sY = points in band
        List<Point> sY = new ArrayList<>();

        for (Point point : pointsSortedByY) {
            if (Math.abs(point.x - qX.get(lineL - 1).x) < closestPair.distance()) {
                sY.add(point);
            }
        }

        for (int i = 0; i < sY.size(); i++) {
            Point point1 = sY.get(i);
            // compare with 11 neighbours
            for (int j = i + 1; j <= i + 15; j++) {
                if (j == sY.size()) {
                    break;
                }
                Point point2 = sY.get(j);

                if (Math.abs(point1.y - point2.y) >= closestPair.distance()) {
                    break;
                }
                if (point1.distance(point2) < closestPair.distance()) {
                    closestPair = new Pair(point1, point2);
                }
            }
        }
        return closestPair;
    }

    private static Pair bruteForce(List<Point> points) {
        Pair minimumPair = new Pair(new Point(0, 0), new Point(0, Double.POSITIVE_INFINITY));
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
            this.x = x;
            this.y = y;
        }

        // Euclidean distance 
        public Double distance(Point other) {
            //Double distance = Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
            Double distance = Math.hypot(this.x - other.x, this.y - other.y);
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
