import java.io.*;
import java.util.*;

public class ReadFiles {
	
	//variable that takes the length of the table
	static int l;
	//table for data
	public Object[][] table;

	public Object[][] readfiles(String data) {
		
		File f = null;
		BufferedReader reader = null;
		String line;
		int counter = 0;
			
		try {
			f = new File(data);
		}
		catch (NullPointerException e){
			System.err.println("File not found.");
		}

		try {
			reader = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e ){
			System.err.println("Error opening file!!!");
		}
			
		try {
			line=reader.readLine();
			while(line!=null) {
				counter++;
				line=reader.readLine();
			}
		}
		catch (IOException e){
			System.err.println("Error reading line " + counter + ".");
		}
			
		if(data.equals("teachers.txt")) {
			//teachers table
			this.table = new Object[counter][5];
		}
		else {
			//lessons table
			this.table = new Object[counter][4];
		}
		this.l = counter;
			
		try {
			reader.close();
		}
		catch (IOException e){
			System.err.println("Error closing file.");
		}
			
		try {
			f = new File(data);
		}
		catch (NullPointerException e){
			System.err.println ("File not found.");
		}

		try {
			reader = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e ){
			System.err.println("Error opening file!");
		}

		try	{
			//int i is number of row
			int i = 0;
			counter = 0;
				
			line=reader.readLine();
			while(line!=null) {
					
				counter++;
				StringTokenizer st = new StringTokenizer(line,"#");
		
				//int j is number of column
				int j = 0;
					
				if(data.equals("teachers.txt")) {
					//teacher code
					this.table[i][j]=Integer.parseInt(st.nextToken());
					j++;
					//teacher name
					this.table[i][j]=st.nextToken();
					j++;
					//teacher subject code
					this.table[i][j]=st.nextToken();
					j++;
					//teacher daily hours
					this.table[i][j]=Integer.parseInt(st.nextToken());
					j++;
					//teacher weekly hours
					this.table[i][j]=Integer.parseInt(st.nextToken());
					//move to next row of the table
					i++;
					
					line=reader.readLine();
				}
				else {
					//lesson code
					this.table[i][j]=Integer.parseInt(st.nextToken());
					j++;
					//lesson name
					this.table[i][j]=st.nextToken();
					j++;
					//lesson class
					this.table[i][j]=st.nextToken();
					j++;
					//lesson weekly hours per class
					this.table[i][j]=Integer.parseInt(st.nextToken());
					i++;
					
					line=reader.readLine();
				}
			}
		}
		catch (IOException e){
			System.err.println("Error reading line " + counter + ".");
		}

		try {
			reader.close();
		}
		catch (IOException e){
			System.err.println("Error closing file.");
		}
		return table;
	}
}