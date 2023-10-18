import java.util.ArrayList;

public class SystemClass {
    private DistanceMatrix filteredMatrix;
    public ArrayList<String> path;
    public ArrayList<ArrayList<String>> neighbourhood;
    private int temperature;
    private int nIter;
    private int distance;

    public SystemClass(ArrayList<String> inputedCities) {
        DistanceMatrix matrix = new DistanceMatrix("matrix.txt");
        filteredMatrix = new DistanceMatrix(matrix, inputedCities);
        path = new ArrayList<>();
        neighbourhood = new ArrayList<>();
        distance = 0;
        System.out.println("Precurso: " + simulatedAnnealing());
        System.out.println("Distância: " + distance);
    }

    //function decaimento                                                     TODO
    //function starting temperature                                           TODO
    //function stop criteria, returns true when the criteria is achieved      TODO
    //function iteration number varying                                       TODO

    //function starting solution                                              DONE
    //function neighbour                                                      DONE
    //alg function

    private void decreaseTemp() {
        temperature--;
    }

    private void varNrIter() {
        nIter++;
    }

    private boolean isOver() {
        return temperature == 1;
    }

    public ArrayList<String> getStartingPath() {
        ArrayList<String> cities = new ArrayList<>(filteredMatrix.getCities()) ;
        ArrayList<String> firstSolution = new ArrayList<>();
        int size = cities.size();
        for (int i = 0; i < size; i++) {
            int randomIndex = (int)Math.floor(Math.random() * cities.size());
            String city = cities.get(randomIndex);
            firstSolution.add(city);
            cities.remove(city);
        }

        distance = getDistance(firstSolution);
        return firstSolution;
    }

    public ArrayList<String> simulatedAnnealing() {
        ArrayList<String> current = getStartingPath();
        ArrayList<String> next;
        ArrayList<String> best = current;
        temperature = 100;
        nIter = 3;

        while (true) {
            for (int i = 1;i < nIter; i++) {
                next = getNeighbour(current);
                int d = getDistance(next) - getDistance(current);
                if (d < 0) {
                    current = next;
                    if (getDistance(current) < getDistance(best))
                        best = current;
                }
                else
                    if(Math.random() > 1/temperature)
                        current = next; //apenas com probabilidade exp (-d / temperature)
            }
            if (isOver())
                return best;
            varNrIter();
            decreaseTemp();
        }
    }

    private int getDistance(ArrayList<String> cities) {
        int matrixDist = 0;
        for (int i = 0; i< cities.size() - 1; i++) {
            String currentCity = cities.get(i);
            matrixDist += filteredMatrix.distance(currentCity, cities.get(i+1));
        }
        matrixDist += filteredMatrix.distance(cities.get(cities.size() - 1), cities.get(0));

        return matrixDist;
    }


    private ArrayList<String> getNeighbour(ArrayList<String> solution) {
        int city1Position = (int)Math.floor(Math.random() * solution.size());
        int city2Position = (int)Math.floor(Math.random() * solution.size());
        while (city2Position == city1Position) {
            city2Position = (int)Math.floor(Math.random() * solution.size());
        }
        String city1 = solution.get(city1Position);
        solution.set(city1Position, solution.get(city2Position));
        solution.set(city2Position, city1);
        return solution;
    }



}