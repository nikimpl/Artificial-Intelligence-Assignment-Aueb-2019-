import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

    //ArrayList that contains the current population of chromosomes
	private ArrayList<Chromosome> population;
    /*
     * ArrayList that contains indexes of the chromosomes in the population ArrayList
     * Each chromosome index exists in the ArrayList as many times as its fitness score
     * By creating this ArrayList so, and choosing a random index from it,
     * the greater the fitness score of a chromosome the greater chance it will be chosen.
    */
	private ArrayList<Integer> fitnessBounds;
	
	//table for teachers data
	public Object[][] teachers;
	//table for lessons data
	public Object[][] lessons;
	
	public Genetic(Object[][] lessons, Object[][] teachers) {
		this.population = null;
		this.fitnessBounds = null;
		this.lessons=lessons;
		this.teachers=teachers;
	}

    /* 
     * populationSize: The size of the population in every step
     * mutationPropability: The probability a mutation might occur in a chromosome
     * minimumFitness: The minimum fitness value of the solution we wish to find
     * maximumSteps: The maximum number of steps we will search for a solution
     */
	public Chromosome geneticAlgorithm(int populationSize, double mutationProbability, int minimumFitness, int maximumSteps) {
		
        //We initialize the population
		initializePopulation(populationSize);
		Random r = new Random();
		for(int step=0; step < maximumSteps; step++) {
			
            //Initialize the new generated population
			ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>();
			for(int i=0; i < populationSize; i++) {
				
				int xIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
				Chromosome x = this.population.get(xIndex);
				int yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
				while(yIndex == xIndex) {
					yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
				}
				Chromosome y = this.population.get(yIndex);
                //We generate the "child" of the two chromosomes
				Chromosome child = this.reproduce(x, y, lessons, teachers);
                //We might then mutate the child
				if(r.nextDouble() < mutationProbability) {
					child.mutate();
				}
                //...and finally add it to the new population
				newPopulation.add(child);
			}
			this.population = new ArrayList<Chromosome>(newPopulation);

            //We sort the population so the one with the greater fitness is first
			Collections.sort(this.population, Collections.reverseOrder());
            //If the chromosome with the best fitness is acceptable we return it
			//System.out.println("step: " + step);
			if(this.population.get(0).getFitness() >= minimumFitness) {
				System.out.println("Fitness " + this.population.get(0).getFitness());
                System.out.println("Finished after " + step + " steps...!");
				return this.population.get(0);
			}
            //We update the fitnessBounds arrayList
			this.updateFitnessBounds();
		}

		System.out.println("Fitness " + this.population.get(0).getFitness());
        System.out.println("Finished after " + maximumSteps + " steps...");
		return this.population.get(0);
	}

    //We initialize the population by creating random chromosomes
	public void initializePopulation(int populationSize) {
		
		this.population = new ArrayList<Chromosome>();
		for(int i=0; i<populationSize; i++) {
			this.population.add(new Chromosome(lessons, teachers));
		}
		this.updateFitnessBounds();
	}

    //Updates the ArrayList that contains indexes of the chromosomes in the population ArrayList
	public void updateFitnessBounds() {
		
		this.fitnessBounds = new ArrayList<Integer>();
		for (int i=0; i<this.population.size(); i++) {
			for(int j=0; j<this.population.get(i).getFitness(); j++) {
                //Each chromosome index exists in the ArrayList as many times as its fitness score
                //By creating this ArrayList so, and choosing a random index from it,
                //the greater the fitness score of a chromosome the greater chance it will be chosen.
				fitnessBounds.add(i);
			}
		}
	}

    //"Reproduces" two chromosomes and generated their "child"
	public Chromosome reproduce(Chromosome x, Chromosome y, Object[][] lessons, Object[][] teachers) {
		Random r = new Random();
        //Randomly choose the intersection point
		int intersectionPoint = r.nextInt(35);
		Gene [][] childGenes = new Gene[9][35];
        //The child has the left side of the x chromosome up to the intersection point...
		for(int i=0; i<9; i++) {
			for(int j=0; j<intersectionPoint; j++) {
				childGenes[i][j] = x.getGenes()[i][j];
			}
		}
        //...and the right side of the y chromosome after the intersection point
		for(int i=0; i<9;i++) {	
			for(int j=intersectionPoint; j<childGenes[0].length; j++) {
				childGenes[i][j] = y.getGenes()[i][j];
			}
		}
		return new Chromosome(childGenes, lessons, teachers);
	}
}
