import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> cities = new ArrayList<>();
        String[] line = in.nextLine().trim().split(", ");
        for (int i = 0; i< line.length;i++) {
            cities.add(line[i]);
        }
        SystemClass systemClass = new SystemClass(cities);
    }


}
