import java.io.*;
import java.util.*;

public class Chromosome implements Comparable<Chromosome> {
	
	//Table that holds the chromosome itself
	private Gene[][] genes;
    //Integer that holds the fitness score of the chromosome
	private int fitness;
	//table for teachers data
	public Object[][] teachers;
	//table for lessons data
	public Object[][] lessons;

    //Constructs a random chromosome
	public Chromosome(Object[][] lessons, Object[][] teachers) {
		
		this.lessons=lessons;
		this.teachers=teachers;
		
		Random r = new Random();
		
		//9 are the classes and 35 are the total hours of the week for each class
		this.genes = new Gene[9][35];

		for(int i=0;i<getGenesXLength();i++){
			for(int j=0;j<getGenesYLength();j++){
				this.genes[i][j] = new Gene(r.nextInt(getLessonsXLength()-1)+1,r.nextInt(getTeachersXLength()-1)+1);;
			}
		}
	
		//insert required blanks
		
		int hcounter = 0;
		for(int k=0;k<getLessonsXLength();k++){
			for(int l=0;l<3*getLessonHours(k);l++){
				hcounter++;
			}
		}

		for(int y=0;y<getGenesXLength()*getGenesYLength()-hcounter;y++){
			this.genes[r.nextInt(getGenesXLength()-1)][r.nextInt(getGenesYLength()-1)] = new Gene();
		}
		
		this.calculateFitness();
	}

    //Constructs a copy of a chromosome
	public Chromosome(Gene [][] genes, Object[][] lessons, Object[][] teachers) {
		this.lessons=lessons;
		this.teachers=teachers;
		
		this.genes = new Gene[9][35];
		for(int i=0; i<getGenesXLength(); i++) {
			for(int j=0; j<getGenesYLength(); j++) {
				this.genes[i][j] = genes[i][j];
			}
		}
		
		this.calculateFitness();
	}
	
	public Gene[][] getGenes() {
		return this.genes;
	}
	
	public int getFitness() {
		return this.fitness;
	}
	
	public void setGenes(Gene[][] genes) {
		for(int i=0; i<getGenesXLength(); i++) {
			for(int j=0; j<getGenesYLength() ; j++) {
				this.genes[i][j] = genes[i][j];
			}
		}
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

    //Calculates the fitness score of the chromosome
	public void calculateFitness() {
		//int non_violations counts how many rules are not violated
		int non_violations = 0;

		//LESSON SHOULD MATCH CLASS
		for(int i=0; i<getGenesXLength(); i++) {
			for(int j=0; j<getGenesYLength(); j++) {
				//if it is A class
				if(i>=0 && i<=2) {
					for(int x=0; x<getLessonsXLength(); x++) {
						if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1) {
							//search lessons[][] to find a lesson for A class
							if ((getLessonClass(x)).equals("A")) {
								//if lesson code in lessons[][] equals lesson code in genes[][] then lesson in chromosome is matching class in chromosome
								if (getGenes(i, j).getLesson() == getLessonCode(x)) {
									non_violations += 20;
									break;
								}

							}
						}
					}
				}
				//if it is B class
				if(i>=3 && i<=5) {
					for(int x=0; x<getLessonsXLength(); x++) {
						//search lessons[][] to find a lesson for B class
						if((getLessonClass(x)).equals("B")) {
							//if lesson code in lessons[][] equals lesson code in genes[][] then lesson in chromosome is matching class in chromosome
							if(getGenes(i,j).getLesson() !=  -1 && getGenes(i,j).getTeacher() != -1) {
								if (getGenes(i, j).getLesson() == getLessonCode(x)) {
									non_violations += 20;
									break;
								}
							}
						}
					}
				}
				//if it is C class
				if(i>=6 && i<=8) {
					for(int x=0; x<getLessonsXLength(); x++) { 
						//search lessons[][] to find a lesson for C class
						if((getLessonClass(x)).equals("C")) {
							//if lesson code in lessons[][] equals lesson code in genes[][] then lesson in chromosome is matching class in chromosome
							if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
								if (getGenes(i, j).getLesson() == getLessonCode(x)) {
									non_violations += 20;
									break;
								}

							}
						}
					}
				}
			}
		}
		
		//TEACHER SHOULD MATCH LESSON
		
		//for each lesson in genes[][]
		for(int i=0; i<getGenesXLength(); i++) {
			for(int j=0; j<getGenesYLength(); j++) {
				//search teachers[][] to find same teacher code with genes[][]
				for(int y=0; y<getTeachersYLength(); y++) {
					if(getTeacherCode(i)==getGenes(i,j).getTeacher()) {
						for (int x=0; x<getTeachableLessons(x).length; x++) {
							//if lesson code in output[] matches lesson code in genes
							if(getGenes(i,j).getLesson()==getTeachableLessons(x)[x]) {
								non_violations += 20;
							}
						}
					}
				}
			}
		}
		
		//TOTAL HOURS FOR EACH LESSON SHOULD BE EXACTLY AS MANY AS LESSONS.TXT SAYS

		for(int x=0; x<getLessonsXLength(); x++) {
			for(int i=0; i<getGenesXLength(); i++) {
				//total weekly hours of a specific lesson for class i
				int total_hours = 0;
				for(int j=0; j<getGenesYLength(); j++) {
					
					//find same lesson code in genes[][] and lessons[][]
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i,j).getLesson() == getLessonCode(x)) {
							total_hours++;
						}
					}
				}
				//compare total_hours to lessons.txt lesson total hours
				if(getLessonHours(x)==total_hours) {
					non_violations+=20;
				}
			}			
		}
		
		//THERE SHOULD BE NO EMPTY SLOTS IN A CLASS SCHEDULE
		for(int i=0; i<getGenesXLength(); i++) {
			//holds the previous spot that is empty
			int previous_spot = -1;
			//day 1
			for(int j=0; j<(getGenesYLength()/5)-1; j++) {
				//if this gene is not empty and there is no previous gene that is empty
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1 && previous_spot!=-1) {
					previous_spot = getGenes(i,j).getLesson();
				}	
			}
			
			if(previous_spot!=-1) {
				non_violations+=20;
			}
			
			previous_spot = -1;
			
			//day 2
			for(int j=getGenesYLength()/5; j<(2*getGenesYLength()/5)-1; j++) {
				//if this gene is not empty and there is no previous gene that is empty
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1 && previous_spot!=-1) {
					previous_spot = getGenes(i,j).getLesson();
				}	
			}
			
			if(previous_spot!=-1) {
				non_violations+=20;
			}
			
			previous_spot = -1;
			
			//day 3
			for(int j=2*getGenesYLength()/5; j<(3*getGenesYLength()/5)-1; j++) {
				//if this gene is not empty and there is no previous gene that is empty
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1 && previous_spot!=-1) {
					previous_spot = getGenes(i,j).getLesson();
				}	
			}
			
			if(previous_spot!=-1) {
				non_violations+=20;
			}
			
			previous_spot = -1;
			
			//day 4
			for(int j=3*getGenesYLength()/5; j<(4*getGenesYLength()/5)-1; j++) {
				//if this gene is not empty and there is no previous gene that is empty
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1 && previous_spot!=-1) {
					previous_spot = getGenes(i,j).getLesson();
				}	
			}
			
			if(previous_spot!=-1) {
				non_violations+=20;
			}
			
			previous_spot = -1;
			
			//day 5
			for(int j=4*getGenesYLength()/5; j<getGenesYLength()-1; j++) {
				//if this gene is not empty and there is no previous gene that is empty
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1 && previous_spot!=-1) {
					previous_spot = getGenes(i,j).getLesson();
				}	
			}
			
			if(previous_spot!=-1) {
				non_violations+=20;
			}
			
		}
		
		//NO TEACHER SHOULD HAVE MORE THAN 2 CONTINUOUS TEACHING HOURS
		for(int i=0; i<getGenesXLength(); i++) {
			for(int j=0; j<getGenesYLength()-2; j++) {
				//if the teacher in this hour is not the same to the teacher 1 hour and 2 hours after then that teachers doesn't have more than 2 continuous teaching hours 
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1 && getGenes(i,j+1).getLesson()!= -1 && getGenes(i,j+1).getTeacher() != -1 && getGenes(i,j+2).getLesson()!= -1 && getGenes(i,j+2).getTeacher() != -1)  {
					if(getGenes(i,j).getTeacher()!=getGenes(i,j+1).getTeacher() && getGenes(i,j).getTeacher()!=getGenes(i,j+2).getTeacher()) {
						non_violations += 10;
					}
				}
			}
		}
		
		//AN EVENLY DISTRIBUTED SCHEDULE WITHOUT BIG DIFFERENCES EACH DAY FOR EACH CLASS (SIMILAR CUMULATIVE DAILY HOURS)
		for(int i=0; i<getGenesXLength(); i++) {
			//daily hours for one class
			int hours1 = 0;
			int hours2 = 0;
			int hours3 = 0;
			int hours4 = 0;
			int hours5 = 0;
			
			//day 1
			for(int j=0; j<(getGenesYLength()/5)-1; j++) {
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1) {
					hours1+=1;
				}
			}
			//day 2
			for(int j=getGenesYLength()/5; j<(2*getGenesYLength()/5)-1; j++) {
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1) {
					hours2+=1;
				}
			}
			//day 3
			for(int j=2*getGenesYLength()/5; j<(3*getGenesYLength()/5)-1; j++) {
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1) {
					hours3+=1;
				}
			}
			//day 4
			for(int j=3*getGenesYLength()/5; j<(4*getGenesYLength()/5)-1; j++) {
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1) {
					hours4+=1;
				}
			}
			//day 5
			for(int j=4*getGenesYLength()/5; j<getGenesYLength()-1; j++) {
				if(getGenes(i,j).getLesson()!=-1 && getGenes(i,j).getTeacher() != -1) {
					hours5+=1;
				}
			}
			
			int avg = (hours1+hours2+hours3+hours4+hours5)/5;
			if((hours1==avg+1 || hours1==avg-1) && (hours2==avg+1 || hours2==avg-1) && (hours3==avg+1 || hours3==avg-1) && (hours4==avg+1 || hours4==avg-1) &&(hours5==avg+1 || hours5==avg-1)) {
				non_violations += 4;
			}
		}
		
		
		//SUBJECTS SHOULD BE DISTRIBUTED EVENLY ACROSS DAYS
		
		for(int l=0; l<getLessonsXLength(); l++) {
			//int count counts the hours a lesson is taught in a day
			int count = 0;
			
			for(int i=0; i<getGenesXLength(); i++) {

				int temp = getLessonCode(i);
				
				//day 1
				for(int j=0; j<(getGenesYLength()/5)-1; j++) {
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i, j).getLesson() == temp) {
							count++;
						}
					}
				}
				
				count++;
				if(count<getLessonHours(l) || count !=0) {
					non_violations += 4;
				}
				
				//day 2
				for(int j=getGenesYLength()/5; j<(2*getGenesYLength()/5)-1; j++) {
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i, j).getLesson() == temp) {
							count++;

						}
					}
				}
				if (count < getLessonHours(l) || count !=0){
					non_violations += 4;
				}
				
				//day 3
				for(int j=2*getGenesYLength()/5; j<(3*getGenesYLength()/5)-1; j++) {
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i, j).getLesson() == temp) {
							count++;
						}
					}
				}
				if (count < getLessonHours(l) || count !=0) {
					non_violations += 4;
				}
				
				//day 4
				for(int j=3*getGenesYLength()/5; j<(4*getGenesYLength()/5)-1; j++) {
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i, j).getLesson() == temp) {
							count++;
						}
					}
				}
				if (count < getLessonHours(l) || count !=0){
					non_violations += 4;
				}
				
				//day 5
				for(int j=4*getGenesYLength()/5; j<getGenesYLength()-1; j++) {
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i, j).getLesson() == temp) {
							count++;
						}
					}
				}
				if (count < getLessonHours(l) || count !=0){
					non_violations += 4;
				}
			}
		
		}
		
		//TEACHERS SHOULD HAVE SIMILAR WORKLOADS
		//array to store weekly hours of every teacher in genes
		int[] weeklyhours = new int[getTeachersXLength()];
		
		int summ = 0;
		for (int k=0;k<getTeachersXLength();k++){//every teacher
			int weekly_hours =0;
			for(int i=0; i<getGenesXLength();i++){//in every class
				for (int j=0;j<getGenesYLength();j++){//every hour of the week
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i, j).getLesson() == getLessonCode(k)) {
							weekly_hours++;

						}
					}
				}
			}
			weeklyhours[k] = weekly_hours;
			summ +=weekly_hours;

		}
		int average_weekly_hours = summ/getTeachersYLength();
		for (int i=0;i<getTeachersXLength();i++){
			if (weeklyhours[i] >= average_weekly_hours-2 || weeklyhours[i]<= average_weekly_hours+2){
				non_violations +=3;
			}

		}
		
		//TEACHERS SHOULD NOT TEACH MORE HOURS IN A WEEK THAN TEACHERS.TXT SAYS
		
		for(int x=0; x<getTeachersXLength(); x++) {
			for(int i=0; i<getGenesXLength(); i++) {
				//total weekly hours teachers teaches
				int total_hours = 0;
				for(int j=0; j<getGenesYLength(); j++) {
					
					//find same lesson code in genes[][] and lessons[][]
					if(getGenes(i,j).getLesson()!= -1 && getGenes(i,j).getTeacher() != -1) {
						if (getGenes(i,j).getTeacher() == getTeacherCode(x)) {
							total_hours++;
						}
					}
				}
				//compare total_hours to lessons.txt lesson total hours
				if(getTeacherTotalWeeklyHours(x)<=total_hours) {
					non_violations+=20;
				}
			}			
		}
		
		this.fitness = non_violations;
	}

   	//Mutate by randomly changing the position of a lesson
	public void mutate() {
		
		Random r = new Random();
		int x = r.nextInt(getGenesXLength()-1);
		int y = r.nextInt(getGenesYLength()-1);
		int l = r.nextInt(getLessonsXLength());
		
		if(l==0) {
			genes[x][y].setLesson(-1);
			genes[x][y].setTeacher(-1);
		}
		else {
			genes[x][y].setLesson(l);
			genes[x][y].setTeacher(r.nextInt(getTeachersXLength()-1)+1);
		}
		
		this.calculateFitness();
	}
	
	public Gene getGenes(int i, int j) {
		return this.genes[i][j];
	}
	
	private int getGenesXLength() {
		return this.genes.length;
	}
	
	private int getGenesYLength() {
		return this.genes[0].length;
	}
	
	private int getLessonsXLength() {
		return this.lessons.length;
	}
	
	private int getLessonsYLength() {
		return this.lessons[0].length;
	}
	
	private int getLessonCode(int i) {
		return (Integer)this.lessons[i][0];
	}
	
	public String getLessonl(int i) {
		return (String)this.lessons[i][1];
	}
	
	private String getLessonClass(int i) {
		return (String)this.lessons[i][2];
	}
	
	private int getLessonHours(int i) {
		return (Integer)this.lessons[i][3];
	}
	
	private int getTeachersXLength() {
		return this.teachers.length;
	}
	
	private int getTeachersYLength() {
		return this.teachers[0].length;
	}
	
	private int getTeacherCode(int i) {
		return (Integer)this.teachers[i][0];
	}
	
	public String getTeachert(int i) {
		return (String)this.teachers[i][1];
	}
	
	private Integer[] getTeachableLessons(int i) {
		//split 3rd cell's data in teachers[][] (teachers teachable lessons) and put them in an array 
		String[] x = ((String) (this.teachers[i][2])).split("\\,");
		Integer[] output = new Integer[x.length];
		for(int a=0; a<x.length; a++) {
			output[a] = Integer.parseInt(x[a]);
		}

		return output;
	}
	
	private int getTeacherTotalDailyHours(int i) {
		return (Integer)this.teachers[i][3];
	}
	
	private int getTeacherTotalWeeklyHours(int i) {
		return (Integer)this.teachers[i][4];
	}
	
	@Override
	public boolean equals(Object obj) {
		for(int i=0; i<this.genes.length; i++) {
			for(int j=0; j<this.genes[0].length; j++) {
				if(this.genes[i][j] != ((Chromosome)obj).genes[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashcode = 0;
		for(int i=0; i<this.genes.length; i++) {
			for(int j=0; j<this.genes[0].length; j++) {
				hashcode += (this.genes[i][j]).getLesson();
			}
		}
		return hashcode;
	}
	
	@Override
    //The compareTo function has been overridden so sorting can be done according to fitness scores
	public int compareTo(Chromosome x) {
		return this.fitness - x.fitness;
	}

}
