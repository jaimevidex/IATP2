# IATP2 — Simulated Annealing for the Travelling Salesman Problem

A from-scratch implementation of **simulated annealing** applied to the Travelling Salesman Problem, written in plain Java with no dependencies. Given a city distance matrix, it searches for a short round trip and reports how the result compares to the first random tour and the worst tour seen.

Built for the *Inteligência Artificial e Tecnologias* course at Instituto Superior Técnico.

## How it works

- **Initial solution** — a uniformly random permutation of the cities.
- **Neighbourhood** — swap two random cities in the current tour.
- **Initial temperature** — derived from the two largest and two smallest distances in the matrix: `(max₁ + max₂) − (min₁ + min₂)`.
- **Cooling** — geometric schedule, `T ← 0.8 · T`.
- **Iterations per temperature** — grows geometrically, `n ← 1.1 · n`.
- **Acceptance** — better neighbours are always accepted; worse ones with probability `exp(−Δ/T)`.
- **Stop** — when `T ≤ 0.1` or when the iteration budget exceeds `(n−1)!`.

The search keeps track of the best, first, worst and last tours found, so the effect of the annealing schedule is visible directly in the output.

## Input

`matrix.txt` holds the distance matrix in a triangular layout:

```
Distancias  Atroeira
Belmar      383 Belmar
Cerdeira    129 504 Cerdeira
```

The first token on each line is a city name; the numbers are its distances to the cities above it.

At the prompt, enter the cities to visit, comma-separated, or `all` to use the whole matrix:

```
Lisboa, Porto, Coimbra, Faro
```

## Run

```bash
javac *.java
java Main
```

`matrix.txt` must be in the working directory.

## Output

```
Best Path: [Faro, Coimbra, Lisboa, Porto]
Distance: 612
Final temperature: 0.098
Number of iterations: 1464
Execution time: 12.4ms

Worst: [...], distance: 981
First: [...], distance: 903
Last: [...], distance: 640
```

## Notes

- Pure Java, standard library only (Java 8+).
- Because the initial tour and neighbour moves are randomised, results vary between runs.
