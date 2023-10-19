import java.util.ArrayList;

public class SystemClass {
    private DistanceMatrix filteredMatrix;
    public ArrayList<String> path;
    public ArrayList<ArrayList<String>> neighbourhood;
    private double temperature;
    private double nIter;
    private int distance;
    private static final double alpha = 0.8;
    private static final double beta = 1.1;

    public SystemClass(ArrayList<String> inputCities) {
        DistanceMatrix matrix = new DistanceMatrix("matrix.txt");
        filteredMatrix = new DistanceMatrix(matrix, inputCities);
        distance = 0;
        nIter = 1.0;
    }
    public SystemClass() {
        filteredMatrix = new DistanceMatrix("matrix.txt");
        distance = 0;
        nIter = 1.0;
    }

    //function decaimento                                                     DONE
    //function starting temperature                                           DONE
    //function stop criteria, returns true when the criteria is achieved      DONE
    //function iteration number varying                                       DONE
    //function starting solution                                              DONE
    //function neighbour                                                      DONE
    //alg function

    public static long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private void decreaseTemp() {
        temperature *= alpha;
    }

    private void varNrIter() {
        nIter *= beta;
    }

    private boolean isOver() {
        if(temperature <= 0.1) {
            //System.out.println("Ending because of temperature");
            return true;
        }
        else if(factorial(filteredMatrix.getCities().size()-1) < nIter) {
            //System.out.println("Ending because of end of iterations");
            return true;
        }
        return false;
    }

    private double getInitialTemperature() {
        ArrayList<ArrayList<Integer>> matrix = filteredMatrix.getDistances();

        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;

        int rows = matrix.size();
        for (int i = 0; i < rows; i++) {
            int cols = matrix.get(i).size();
            for (int j = 0; j < cols; j++) {
                int current = matrix.get(i).get(j);
                if (current < min1) {
                    min2 = min1;
                    min1 = current;
                } else if (current < min2) {
                    min2 = current;
                }

                if (current > max1) {
                    max2 = max1;
                    max1 = current;
                } else if (current > max2) {
                    max2 = current;
                }

            }
        }
        int ans = (max1+max2)-(min1+min2);
        return ans;
    }

    /*
    Atroeira, Douro, Pinhal, Teixoso, Ulgueira, Vilar
    */

    private ArrayList<String> getStartingPath() {
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

    private ArrayList<String> simulatedAnnealing() {
        long startTime = System.nanoTime();
        first = getStartingPath();
        ArrayList<String> current = first;
        ArrayList<String> next = new ArrayList<>();
        ArrayList<String> best = current;
        temperature = getInitialTemperature();

        while (true) {
            for (int i = 1;i <= nIter; i++) {
                next = getNeighbour(new ArrayList<>(current));
                int d = getDistance(next) - getDistance(current);
                if (d < 0) {
                    current = next;
                    int currentD = getDistance(current);
                    if (currentD < distance) {
                        best = current;
                        distance = currentD;

                    }
                }
                else
                    if(Math.random() < Math.exp(-d/temperature)) {
                        current = next;
                    }
            }
            if (isOver()) {
                last = next;
                executeTime = System.nanoTime() - startTime;
                return best;
            }
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

    public void printResults() {
        System.out.println("Best Path: " + simulatedAnnealing());
        System.out.println("Distance: " + distance);
        System.out.println("Final temperature: " + temperature);
        System.out.println("Number of iterations: " + (int)Math.floor(nIter));
        System.out.println("Execution time: " + executeTime/1000000.0 + "ms");
        System.out.println();
        System.out.println("Worst: " + worst + ", distance: " + getDistance(worst) );
        System.out.println("First: " + first + ", distance: " + getDistance(first) );
        System.out.println("Last: " + last + ", distance: " + getDistance(last) );
    }
}