package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Alex Smirnov
 * SATISFIABILITY PROBLEM - Genetic Algorithm
 */

// TODO: Finish testing of files
public class GeneticAlgo {
	public int variables; // n
	public int clauses; // m
	public int clauseLength = 3;
	public int[][] clauseArr; // holds m clauses 
	public State[] chromosomes; // test states
	public int numOfStates = 10; // number of test states
	public boolean problemSolved = false;
	public boolean noSolution = false;
	public int currFlips;
	public static void main(String[] args) throws FileNotFoundException {
		int numTests = 5;
        long[] times = new long[numTests];
        int[] bitFlips = new int[numTests];
        for(int s = 0; s < numTests; s++){
            Scanner sc = new Scanner(System.in);
            String fileName = ("uf50-010" + (s+1) + ".cnf");
            Scanner fileSc = new Scanner(new File(fileName));
            GeneticAlgo algorithm = new GeneticAlgo();	
            int bitFlip = 0;
            long startTime = System.currentTimeMillis();
    		bitFlip = algorithm.run(bitFlip, fileSc);
            long endTime   = System.currentTimeMillis();
            fileSc.close();
            long totalTime = endTime - startTime;
            times[s] = totalTime;
            bitFlips[s] = bitFlip;
            sc.close();
        }
        long avgTimes = 0;
        int avgBitFlips = 0;
        for(int k = 0; k < numTests; k++){
            avgTimes += times[k];
            avgBitFlips += bitFlips[k];
        }
        System.out.println("\nAverage time: " + (avgTimes / numTests));
        System.out.println("Average bit flips: " + (avgBitFlips / numTests));

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
          
	}
	
	/**
	 * run - runs the Genetic Algorithm until either the problem is solved, or no solution is detected
	 * noSolution is true when the best possible fitness is found and cannot be increased any more
	 * @throws FileNotFoundException 
	 */
	public int run(int bitFlip, Scanner fileScan) throws FileNotFoundException{
		fileParse(fileScan); 
        genStates();
		int failCount = 0;
		int topFitness = 0;
		while(problemSolved == false && noSolution == false){
			for(int i = 0; i < numOfStates; i++){
				setFitness(chromosomes[i], i, clauseArr);
			}	    
		    findElite(chromosomes);
		    findParents(chromosomes);
		    crossover(chromosomes);
		    mutation(chromosomes);
		    flipHeuristic(chromosomes);
		    bitFlip++;
		    findElite(chromosomes);
		    findParents(chromosomes);
		    for(int i = 0; i < numOfStates; i++){
		    	if(chromosomes[i].elite && topFitness < chromosomes[i].fitness){
		    		topFitness = chromosomes[i].fitness;
		    		if(chromosomes[i].fitness == clauses - 2){
		    			topFitness++;
						problemSolved = true;
						break;
					}
		    	}
		    }
		    failCount++;
		    System.out.println(failCount + " Current fitness: " + topFitness);
		    if(failCount >= 50000){
				noSolution = true;
		    }
		}
		if(problemSolved){
	    	System.out.println("Solved problem, Best Fitness = " + topFitness);
	    }else if(noSolution){
	    	System.out.println("No solution, Best fitness = " + topFitness);
	    }
		return bitFlip;
	}
	
	/**
	 * fileParse - parses through input file, reads in variables, clauses, then parses through the clauses
	 * @throws FileNotFoundException 
	 */
	public void fileParse(Scanner fileScan) throws FileNotFoundException{
		/*Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name: ");   
        String fileName = sc.next();
        sc.close();
        Scanner fileScan = new Scanner(new File(fileName));*/
		while(fileScan.hasNext()){
			char curr = fileScan.next().charAt(0);
        	if(curr == 'c'){ // comment line
        		fileScan.nextLine();
        	} else if(curr == 'p'){ // problem line
        		String currS = fileScan.next();
        		if(currS.equals("cnf")){
        			variables = fileScan.nextInt();
        			clauses = fileScan.nextInt();
        			clauseArr = new int[clauses][clauseLength];
        			fileScan.nextLine();
        			int number;
            		for(int i = 0; i < clauses; i++){
            			for(int j = 0; j < clauseLength; j++){
            				number = fileScan.nextInt();
            				clauseArr[i][j] = number;
            			}
            			fileScan.nextLine();
            		}
        		} else{
        			System.out.println("Wrong format, not cnf");
        			break;	
        		}
        	} else if(curr == '%'){ // end of file
        		break;
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
			chromosomes[i] = new State();
		}
		for(int i = 0; i < numOfStates; i++){
			chromosomes[i].randomizeBits(variables);
		}
	}
	
	/**
	 * setFitness - traverses clauses and sets fitness of current state
	 * @param curr
	 */
	public void setFitness(State chromosome, int index, int[][] clauseArr){
		boolean satisfyClause = false;
		int curr;
		chromosome.fitness = 0;
		for(int i = 0; i < clauses; i++){
			for(int j = 0; j < clauseLength; j++){
				curr = clauseArr[i][j];
				if(curr > 0 && chromosome.bitstring[curr-1] == 1){
					satisfyClause = true;
					break;
				} else if(curr < 0 && chromosome.bitstring[-curr-1] == 0){
					satisfyClause = true;
					break;
				}
			}
			if(satisfyClause == true){
				chromosome.fitness++;
			}
			satisfyClause = false;
		}
		chromosomes[index].fitness = chromosome.fitness;
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
			chromosomes[i].elite = false;
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
		State[] nextGeneration = new State[numOfStates];
		int j = 0;
		for(int i = 0; i < numOfStates; i++){ // propagate elite states
			if(chromosomes[i].elite == true){
				nextGeneration[j] = chromosomes[i];
				j++;
			} 	
		}
		for(int i = 2; i < numOfStates; i++){
			nextGeneration[i] = new State();
		}
		State parent1;
		State parent2;
		int index1 = 0;
		int index2 = 1;
		for(int k = 2; k < numOfStates; k++){
			while(!chromosomes[index1].willReproduce){
				if(index1 == 9){
					index1 = 0;
				} else{
					index1++;
				}	
			}
			parent1 = chromosomes[index1];
			while(!chromosomes[index2].willReproduce){
				if(index2 == 9){
					index2 = 0;
				} else{
					index2++;
				}
			}
			parent2 = chromosomes[index2];
			// Crossover
			for(int i = 0; i < variables; i++){
				if(random(1, 10) > 5){
					nextGeneration[k].bitstring[i] = parent1.bitstring[i];
				}else{
					nextGeneration[k].bitstring[i] = parent2.bitstring[i];
				}
			}	
		}
		for(int i = 0; i < numOfStates; i++){
			chromosomes[i] = nextGeneration[i];
		}	
	}
	
	/**
	 * mutation - mutates chromosome with probability 0.9. If a chromosome is to be mutated, then the mutation
	 * flips each bit with probability 0.5. Apply mutation only to 8 states that are result of crossover.
	 * @param curr
	 */
	public void mutation(State[] chromosomes){
		for(int i = 0; i < numOfStates; i++){
			if(!chromosomes[i].elite){
				if(random(1, 10) < 10){
					for(int j = 0; j < variables; j++){
						if(random(1, 10) > 5){
							if(chromosomes[i].bitstring[j] == 0){
								chromosomes[i].bitstring[j] = 1;
							}else if(chromosomes[i].bitstring[j] == 1){
								chromosomes[i].bitstring[j] = 0;
							}		
						}
					}
				}
			}
		}
	}
	
	/**
	 * flipHeuristic - after crossover and mutation, the algorithm scans the bits of the assignment in random order.
	 * Each bit is flipped, and the flip is accepted if the gain is >= 0. When all the bits have been considered, 
	 * if the process improves the assignment's fitness, the flipping process is repeated until no additional improvement
	 * can be achieved. Do not apply flip on the elite states.
	 * @param curr
	 */
	public void flipHeuristic(State[] chromosomes){
		State test;
		for(int i = 0; i < numOfStates; i++){
			if(chromosomes[i].elite){
				continue;
			}
			test = chromosomes[i];
			setFitness(test, i, clauseArr);
			boolean[] consideredBits = new boolean[variables];
			for(int j = 0; j < variables; j++){ // initialize array of false booleans
				consideredBits[j] = false;
			}
			int randomBit = 0;
			int topFitnessAchieved = 0;
			while(topFitnessAchieved < test.fitness){
				for(int j = 0; j < variables; j++){
					while(true){
						randomBit = random(0, (variables - 1));
						if(!consideredBits[randomBit]){
							consideredBits[randomBit] = true;
							break;
						}
					}
					// found the random bit to flip
					if(test.bitstring[randomBit] == 0){
						test.bitstring[randomBit] = 1;
					} else if(test.bitstring[randomBit] == 1){
						test.bitstring[randomBit] = 0;
					}
					int testFitness = test.fitness;
					setFitness(test, i, clauseArr);
					if(test.fitness >= testFitness){
						chromosomes[i].bitstring = test.bitstring;
						topFitnessAchieved = test.fitness;
					} else{ // keep state
						
					}
				}	
			}
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
