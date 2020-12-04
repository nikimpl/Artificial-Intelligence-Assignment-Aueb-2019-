import java.io.*;
import java.util.*;

public class PrintFile { 

	public Chromosome chromosome;
	//tables for each day
	private String[] monday = new String[7];
	private String[] tuesday = new String[7];
	private String[] wednesday = new String[7];
	private String[] thursday = new String[7];
	private String[] friday = new String[7];
	//table for hours
	private String[] hours = new String[7];
	
	public PrintFile(Chromosome chromosome) {
		this.chromosome=chromosome;
	}

	public void print(Chromosome chromosome) {
		
		File f = null;
		PrintWriter writer = null;

		try	{
			f = new File("schedule.txt");
		}
		
		catch (NullPointerException e) {
			System.out.println ("Cannot create file.");
		}

		try	{
			writer = new PrintWriter(new FileWriter(f));
		}
		catch (IOException e){
			System.out.println("Cannot write to file.");
		}
		
			writer.format("%-20s%-33s%-60s%-60s%-60s%-60s%n","   Hour", "Class", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY");
			writer.write("\n");
			
			for(int h=8; h<15; h++) {
				this.hours[h-8] = "" + h + ":00 - " + (h+1) + ":00";
			}
			
			//for the 9 classes
			for(int i=0; i<9; i++) {
				//for Monday
				for(int j=0; j<7; j++) {
					if (this.chromosome.getGenes(i, j).getLesson() != -1 && this.chromosome.getGenes(i, j).getTeacher() != -1) {

						this.monday[j] = "" + this.chromosome.getLessonl(this.chromosome.getGenes(i, j).getLesson()) + " (" + this.chromosome.getTeachert(this.chromosome.getGenes(i, j).getTeacher()) + ")";
					}else{
						this.monday[j] = "-------";
					}
				}
				
				//for Tuesday
				for(int j=7; j<14; j++) {
					if (this.chromosome.getGenes(i, j).getLesson() != -1 && this.chromosome.getGenes(i, j).getTeacher() != -1) {

						this.tuesday[j - 7] = "" + this.chromosome.getLessonl(this.chromosome.getGenes(i, j).getLesson()) + " (" + this.chromosome.getTeachert(this.chromosome.getGenes(i, j).getTeacher()) + ")";
					}else{
						this.tuesday[j-7] = "-------";
					}
				}
				
				//for Wednesday
				for(int j=14; j<21; j++) {
					if (this.chromosome.getGenes(i, j).getLesson() != -1 && this.chromosome.getGenes(i, j).getTeacher() != -1) {

						this.wednesday[j - 14] = "" + this.chromosome.getLessonl(this.chromosome.getGenes(i, j).getLesson()-1) + " (" + this.chromosome.getTeachert(this.chromosome.getGenes(i, j).getTeacher()) + ")";
					}else {
						this.wednesday[j-14] = "-------";
					}
				}
				
				//for Thursday
				for(int j=21; j<28; j++) {
					if (this.chromosome.getGenes(i, j).getLesson() !=-1 && this.chromosome.getGenes(i, j).getTeacher() != -1) {

						this.thursday[j - 21] = "" + this.chromosome.getLessonl(this.chromosome.getGenes(i, j).getLesson()-1) + " (" + this.chromosome.getTeachert(this.chromosome.getGenes(i, j).getTeacher()) + ")";
					}else {
						this.thursday[j-21] = "-------";
					}
				}
				
				//for Friday
				for(int j=28; j<35; j++) {
					if (this.chromosome.getGenes(i, j).getLesson() != -1 && this.chromosome.getGenes(i, j).getTeacher() != -1) {

						this.friday[j - 28] = "" + this.chromosome.getLessonl(this.chromosome.getGenes(i, j).getLesson()-1) + " (" + this.chromosome.getTeachert(this.chromosome.getGenes(i, j).getTeacher()) + ")";
					}else {
						this.friday[j-28] = "-------";
					}

				}
				
				switch (i) {
					case 0: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "A1", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 1: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "A2", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 2: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "A3", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 3: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "B1", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 4: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "B2", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 5: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "B3", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 6: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "C1", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 7: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "C2", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					case 8: 
						for(int d=0; d<7; d++) {
							writer.format("%-22s%-20s%-60s%-60s%-60s%-60s%n", this.hours[d], "C3", this.monday[d], this.tuesday[d], this.wednesday[d], this.thursday[d], this.friday[d]);
						}
						break;
					default: break;
						
				}
				writer.write("\n");
			}

		writer.close();
	}
}