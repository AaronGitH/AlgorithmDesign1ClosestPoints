
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * @author Rene Anda Nielsen <rann@itu.dk>
 * @author Aaron Gornott <agor@itu.dk>
 * @author Sarah de Voss <satv@itu.dk>
 */
public class ClosestPoints {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(""); 
        Scanner sc = new Scanner(file);
        //sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            System.out.println(line);
        }
    }
    
}
