package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Alex Smirnov
 *Test each chromosome to see how good it is at solving the problem at hand and assign a fitness score accordingly. 
 * The fitness score is a measure of how good that chromosome is at solving the problem to hand.
 * Select two members from the current population. The chance of being selected is proportional to the chromosomes fitness. 
 * Roulette wheel selection is a commonly used method.
 * Dependent on the crossover rate crossover the bits from each chosen chromosome at a randomly chosen point.
 * Step through the chosen chromosomes bits and flip dependent on the mutation rate.
 * Repeat step 2, 3, 4 until a new population of N members has been created.*/

/** 
 * SATISFIABILITY PROBLEM
 * Comment lines start with 'c'
 * Problem line - 1 line, starts with 'p'
 * p FORMAT VARIABLES CLAUSES
 * FORMAT - "cnf"
 * VARIABLES - int n, the number of variables
 * CLAUSES - int m, the number of clauses in this instance
 * 1 to n clauses
 * Positive number i is a non-negated variable
 * Negative number -i is a negated variable
 * 0 is newline character
 */

// TODO: Create 10 randomized States, Parse through lines of cnf files, Implement genetic algorithm
public class GeneticAlgo {
	public int variables;
	public int clauses;
	public int[] bits;
	public static void main(String[] args) {
		
	}
	/**
	 * Instantiates the Genetic Algorithm
	 * @throws FileNotFoundException 
	 */
	public GeneticAlgo() throws FileNotFoundException{
		// File input for testing - FOR DEMO
    	Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");   
        String fileName = sc.next();
        Scanner fileSc = new Scanner(new File(fileName));
        fileParse(fileSc);      
        fileSc.close();
        sc.close();
	}
	
	/**
	 * fileParse - parses through input file, reads in variables, clauses, then parses through the clauses
	 * @param fileScan
	 */
	public void fileParse(Scanner fileScan){
		while(fileScan.hasNext()){
        	if(fileScan.next().charAt(0) == 'c'){ // comment line
        		fileScan.nextLine();
        	} else if(fileScan.next().charAt(0) == 'p'){ // problem line
        		if(fileScan.next() == "cnf"){
        			variables = fileScan.nextInt();
        			clauses = fileScan.nextInt();
        			bits = new int[variables];
        			fileScan.nextLine();
        		} else{
        			System.out.println("Wrong format, not cnf");
        			break;	
        		}
        	} else if(fileScan.next().charAt(0) == '%'){ // end of file
        		
        	} else {
        		// WRITE CODE FOR PARSING CLAUSES HERE
        		// 0 is end of clause
        	}
        }
	}
	
	/**
	 * genStates - generates 10 new randomized states to start off the algorithm
	 * @return
	 */
	public State[] genStates(){
		State[] testStates = new State[10];
		for(int i = 0; i < 10; i++){
			testStates[i].randomizeBits(variables);
		}
		return testStates;
	}
}
