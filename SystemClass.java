import java.util.ArrayList;

public class SystemClass {
    private DistanceMatrix filteredMatrix;
    public ArrayList<String> path;
    public ArrayList<ArrayList<String>> neighbourhood;
    public int distance;

    public SystemClass(ArrayList<String> inputedCities) {
        DistanceMatrix matrix = new DistanceMatrix("matrix.txt");
        filteredMatrix = new DistanceMatrix(matrix, inputedCities);
        path = new ArrayList<>();
        neighbourhood = new ArrayList<>();
        distance = 0;
        getStartingPath();
    }

    public ArrayList<String> getStartingPath() {
        ArrayList<String> cities = filteredMatrix.getCities();
        ArrayList<String> firstSolution = new ArrayList<>();
        int size = cities.size();
        for (int i = 0; i < size; i++) {
            int randomIndex = (int)Math.floor(Math.random() * cities.size());
            String city = cities.get(randomIndex);
            firstSolution.add(city);
            cities.remove(city);
        }
        distance = getDistance(firstSolution);
        System.out.println(firstSolution);
        return cities;
    }

    public void simulatedAnnealing() {
        ArrayList<String> current = getStartingPath();
        ArrayList<String> next = new ArrayList<>();
        ArrayList<String> best = current;
        int temperature = 100;

        while (temperature>0) {



            temperature--;
        }
    }

    public int getDistance(ArrayList<String> cities) {
        int matrixDist = 0;
        for (int i = 0; i< cities.size() - 1; i++) {
            String currentCity = cities.get(i);
            matrixDist += filteredMatrix.distance(currentCity, cities.get(i+1));
        }
        matrixDist += filteredMatrix.distance(cities.get(cities.size() - 1), cities.get(0));

        return matrixDist;
    }
}