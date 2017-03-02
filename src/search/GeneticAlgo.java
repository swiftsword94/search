package search;

/**
 * Your task is to code a genetic algorithm variance so as to solve SAT problems. This means that
you will code a local search algorithm that generate off-spring states through genetic operators
and improves them subsequently by means of local search. Each individual state corresponds to
an assignment of true or false values to the variables. Here, a variation to the vanilla genetic
algorithms is described for your implementation. Employ a population size of 10
 * @author Alex Smirnov
 *
 */
public class GeneticAlgo {
	/**Test each chromosome to see how good it is at solving the problem at hand and assign a fitness score accordingly. 
	 * The fitness score is a measure of how good that chromosome is at solving the problem to hand.
	Select two members from the current population. The chance of being selected is proportional to the chromosomes fitness. 
	Roulette wheel selection is a commonly used method.
	Dependent on the crossover rate crossover the bits from each chosen chromosome at a randomly chosen point.
	Step through the chosen chromosomes bits and flip dependent on the mutation rate.
	Repeat step 2, 3, 4 until a new population of N members has been created.*/
	
	public GeneticAlgo(){
		
	}
	public static void main(String[] args) {
		

	}

}
