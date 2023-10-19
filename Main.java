import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        SystemClass systemClass;
        ArrayList<String> cities = new ArrayList<>();
        String[] line = in.nextLine().trim().split(", ");
        for (int i = 0; i< line.length;i++) {
            cities.add(line[i]);
        }
        if (line[0].equals("all"))
            systemClass = new SystemClass();
        else
            systemClass = new SystemClass(cities);
        systemClass.printResults();
    }



}
