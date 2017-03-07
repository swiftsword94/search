package search;
/**
 * @author  Alex Smirnov
 * 
 * State class - For use with GeneticAlgo.java
 * Defines a state of the genetic algorithm
 * State can be crossed over with another state to make a child state
 * State string can be mutated 
 */

public class State {
	public int[] chromosome; // holds the bits of the assignment
	public boolean elite; // determines if state is one of the best two in the population
	public int fitness; // fitness of the state for solving the SAT problem
	public boolean willReproduce; // determines if state will reproduce through crossover
	
	/**
	 * State - generate default blank state
	 */
	public State(){
		chromosome = new int[100];
		elite = false;
		willReproduce = false;
		fitness = 0;
	}
	
	/**
	 * randomizeBits - takes in variables from GeneticAlgo and randomizes that amount of bits for the chromosome
	 * @param variables
	 */
	public void randomizeBits(int variables){
		chromosome = new int[variables];
		for(int i = 0; i < variables; i++){
			chromosome[i] = random(0, 1);
		}
	}
	
	/**
	 * random - generates random number between min and max, inclusive
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max)
    {
       int range = (max - min) + 1;    
       return (int)(Math.random() * range) + min;
    }
}
