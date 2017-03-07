package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Alex Smirnov
 * 
 * SATISFIABILITY PROBLEM - Genetic Algorithm
 */

// TODO: Complete crossover, mutation, and flipHeuristic. Complete main.
public class GeneticAlgo {
	public int variables; // n
	public int clauses; // m
	public int clauseLength = 3;
	public int[][] clauseArr; // holds m clauses 
	public State[] chromosomes; // test states
	public int numOfStates = 10; // number of test states
	public boolean problemSolved = false;
	public boolean noSolution = false;
	
	public static void main(String[] args) throws FileNotFoundException {
		GeneticAlgo algorithm = new GeneticAlgo();
		algorithm.run();
		/**
		 * Plot the success rate of algorithm as a function of n variables.
		 * Success rate is percentage of benchmark problems for which solutions are found by solver
		 * Report running time and average number of flips in finding a solution.
		 * Plot median running time and average bit flips as a function of n variables. 
		 */
	}
	/**
	 * GeneticAlgo - instantiates the Genetic Algorithm
	 * @throws FileNotFoundException 
	 */
	public GeneticAlgo() throws FileNotFoundException{	
        fileParse(); 
        genStates();  
	}
	
	/**
	 * run - runs the Genetic Algorithm until either the problem is solved, or no solution is detected
	 * noSolution is true when the best possible fitness is found and cannot be increased any more
	 */
	public void run(){
		while(problemSolved == false || noSolution == false){
			for(int i = 0; i < numOfStates; i++){
				setFitness(chromosomes[i], clauseArr);
			}	    
		    findElite(chromosomes);
		    findParents(chromosomes);
		    crossover(chromosomes);
		    mutation(chromosomes);
		    flipHeuristic(chromosomes);
        }
	}
	
	/**
	 * fileParse - parses through input file, reads in variables, clauses, then parses through the clauses
	 * @throws FileNotFoundException 
	 */
	public void fileParse() throws FileNotFoundException{
		Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");   
        String fileName = sc.next();
        sc.close();
        Scanner fileScan = new Scanner(new File(fileName));
		while(fileScan.hasNext()){
        	if(fileScan.next().charAt(0) == 'c'){ // comment line
        		fileScan.nextLine();
        	} else if(fileScan.next().charAt(0) == 'p'){ // problem line
        		if(fileScan.next() == "cnf"){
        			variables = fileScan.nextInt();
        			clauses = fileScan.nextInt();
        			clauses--; // for some reason there's always 1 less clause than it says
        			clauseArr = new int[clauses][clauseLength];
        			fileScan.nextLine();
        		} else{
        			System.out.println("Wrong format, not cnf");
        			break;	
        		}
        	} else if(fileScan.next().charAt(0) == '%'){ // end of file
        		break;
        	} else { // fill clauses into string array
        		int number;
        		for(int i = 0; i < clauses; i++){
        			for(int j = 0; j < clauseLength; j++){
        				number = fileScan.nextInt();	
        				clauseArr[i][j] = number;
        			}
        			fileScan.nextLine();
        		}
        	}
        }
		fileScan.close();
	}
	
	/**
	 * genStates - generates 10 new randomized states to start off the algorithm
	 * @return
	 */
	public void genStates(){
		chromosomes = new State[numOfStates];
		for(int i = 0; i < numOfStates; i++){
			chromosomes[i].randomizeBits(variables);
		}
	}
	
	/**
	 * setFitness - traverses clauses and sets fitness of current state
	 * @param curr
	 */
	public void setFitness(State chromosome, int[][] clauseArr){
		boolean satisfyClause = true;
		int curr;
		for(int i = 0; i < clauses; i++){
			for(int j = 0; j < clauseLength; j++){
				curr = clauseArr[i][j];
				if(curr > 0 && chromosome.bitstring[curr] != 1){
					satisfyClause = false;
				} else if(curr < 0 && chromosome.bitstring[curr] != 0){
					satisfyClause = false;
				}
			}
			if(satisfyClause == true){
				chromosome.fitness++;
			}
			satisfyClause = true;
		}
	}
	
	/**
	 * findElite - finds the best two individuals in the population according to fitness
	 * @param chromosomes
	 */
	public void findElite(State[] chromosomes){
		int elite1 = 0;
		int elite2 = 0;
		int eliteCount = 0;
		for(int i = 0; i < numOfStates; i++){
			if(chromosomes[i].fitness > elite1){
				elite2 = elite1;
				elite1 = chromosomes[i].fitness;
			} else if(chromosomes[i].fitness > elite2){
				elite2 = chromosomes[i].fitness;
			}
		}
		for(int j = 0; j < numOfStates; j++){
			if(chromosomes[j].fitness == elite1 || chromosomes[j].fitness == elite2){
				chromosomes[j].elite = true;
				eliteCount++;
				if(eliteCount >= 2){
					break;
				}
			}
		}
	}
	
	/**
	 * findParents - finds which members of the population will reproduce
	 * @param chromosomes
	 */
	public void findParents(State[] chromosomes){
		int notParent1 = 999;
		int notParent2 = 999;
		int notParentCount = 0;
		for(int i = 0; i < numOfStates; i++){
			if(chromosomes[i].fitness < notParent1){
				notParent2 = notParent1;
				notParent1 = chromosomes[i].fitness;
			} else if(chromosomes[i].fitness < notParent2){
				notParent2 = chromosomes[i].fitness;
			}
		}
		for(int j = 0; j < numOfStates; j++){
			if(chromosomes[j].fitness != notParent1 && chromosomes[j].fitness != notParent2){
				chromosomes[j].willReproduce = true;
			} else {
				chromosomes[j].willReproduce = false;
				notParentCount++;
				if(notParentCount >= 2){
					break;
				}
			}
		}
	}
	
	/**
	 * crossover - given parents x and y, the i-th bit of the offspring is from x with 0.5 probability
	 * and from y with 0.5 probability. Only apply crossover to 8 states from probabilistic selection,
	 * and propagate the elite states forward intact.
	 * @param x
	 * @param y
	 */
	public void crossover(State[] chromosomes){
		// TODO: FINISH THIS METHOD
	}
	
	/**
	 * mutation - mutates chromosome with probability 0.9. If a chromosome is to be mutated, then the mutation
	 * flips each bit with probability 0.5. Apply mutation only to 8 states that are result of crossover.
	 * @param curr
	 */
	public void mutation(State[] chromosomes){
		// TODO: FINISH THIS METHOD
	}
	
	/**
	 * flipHeuristic - after crossover and mutation, the algorithm scans the bits of the assignment in random order.
	 * Each bit is flipped, and the flip is accepted if the gain is >= 0. When all the bits have been considered, 
	 * if the process improves the assignment's fitness, the flipping process is repeated until no additional improvement
	 * can be achieved. Do not apply flip on the elite states.
	 * @param curr
	 */
	public void flipHeuristic(State[] chromosomes){
		// TODO: FINISH THIS METHOD
	}
}
