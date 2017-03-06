package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Alex Smirnov
 * SATISFIABILITY PROBLEM - Genetic Algorithm
 */

// TODO: Create 10 randomized States, Parse through lines of cnf files, Implement genetic algorithm
public class GeneticAlgo {
	public int variables;
	public int clauses; // n
	public int clauseLength = 3;
	public int[][] clauseArr; // holds n clauses 
	public State[] chromosomes; // test states
	public static void main(String[] args) {
		
	}
	/**
	 * Instantiates the Genetic Algorithm
	 * @throws FileNotFoundException 
	 */
	public GeneticAlgo() throws FileNotFoundException{
		// File input for testing - FOR DEMO	
        fileParse(); 
        genStates();
	}
	
	/**
	 * fileParse - parses through input file, reads in variables, clauses, then parses through the clauses
	 */
	public void fileParse(){
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
		chromosomes = new State[10];
		for(int i = 0; i < 10; i++){
			chromosomes[i].randomizeBits(variables);
		}
	}
	
	 /**Test each chromosome to see how good it is at solving the problem at hand and assign a fitness score accordingly. 
	 * The fitness score is a measure of how good that chromosome is at solving the problem to hand.
	 * Select two members from the current population. The chance of being selected is proportional to the chromosomes fitness. 
	 * Roulette wheel selection is a commonly used method.
	 * Dependent on the crossover rate crossover the bits from each chosen chromosome at a randomly chosen point.
	 * Step through the chosen chromosomes bits and flip dependent on the mutation rate.
	 * Repeat step 2, 3, 4 until a new population of N members has been created.*/
	
	/**
	 * setFitness - traverses clauses and sets fitness of current state
	 * @param curr
	 */
	public void setFitness(State[] chromosomes, int[][] clauseArr){
		
	}
}
