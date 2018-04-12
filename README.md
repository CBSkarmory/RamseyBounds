# RamseyTh

### Building: 
`mvn package`

### What it does
`Ramsey3ColBounder.boundR()`

This will obtain bounds on R(a, b, c) for 1 <= a <= b <= c <= 10

R(a, b, c) is least n such that for all 3-colorings of an n-clique, there exists either

1. RED a-clique or 
2. BLUE b-clique or
3. GREEN c-clique

`HomogSetChecker.hasHomogSetSizeK()`

Given a coloring of a complete graph and number k, tells you whether or not the graph has a homogenous set size k.

A homogenous set ![H](https://latex.codecogs.com/svg.latex?%5Clarge%20H) is a subclique such that 
![(\forall a, b, c, d \in H)\[COL(a, b) = COL(c, d)\]](https://latex.codecogs.com/svg.latex?%5Clarge%20%28%5Cforall%20a%2C%20b%2C%20c%2C%20d%20%5Cin%20H%29%5C%5BCOL%28a%2C%20b%29%20%3D%20COL%28c%2C%20d%29%5C%5D)

`HomogSetChecker.findMaxHomogSetSize()`

Given a coloring of a clique and number k, tells you the size of a maximum homogenous set.
