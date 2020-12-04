public class Main {
	
	public static void main(String[] args) {
		
		ReadFiles r1 = new ReadFiles();
		ReadFiles r2 = new ReadFiles();
		Object [][] o1=r1.readfiles("lessons.txt");
		Object[][] o2=r2.readfiles("teachers.txt");
		Genetic g = new Genetic(o1,o2);
		Chromosome x = new Chromosome(o1,o2);		
		x = g.geneticAlgorithm(10, 0.1, 18000, 1000);
		PrintFile p = new PrintFile(x);
		p.print(x);
	}
}