import java.io.*;
import java.util.*;

public class Gene {
	
	public int teacher;
	public int lesson;
	
	public Gene(int lesson, int teacher) {
		this.teacher=teacher;
		this.lesson=lesson;
	}

	public Gene(){
		this.teacher=-1;
		this.lesson=-1;
	}
	
	public int getTeacher() {
		return this.teacher;
	}
	
	public int getLesson() {
		return this.lesson;
	}
	
	public int setTeacher(int t) {
		return this.teacher=t;
	}
	
	public int setLesson(int l) {
		return this.lesson=l;
	}
}