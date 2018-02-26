package Data;

import java.util.List;

/**
 * StudentAnswer class represents set of answers given by student
 * 
 * 
 * @author Bartosz Gardziejewski
 * @version 1.0
 */
public class StudentAnswer {

	public String studentName;
	public String[] studentAnswers;
	public double mark = 0;
	
	// Constructor
	StudentAnswer(String name,String[] answers){
		studentName = name;
		studentAnswers = answers;
	}
	
	// Getters
	public String getStudentName()
	{
		return studentName;
	}
	
	public String getStudentAnswers(int number)
	{
		return studentAnswers[number];
	}
	
	public String[] getStudentAnswers()
	{
		return studentAnswers;
	}
	
	public double getMark()
	{
		return mark;
	}
	
	/**
	 * This method calculates which mark will the student get
	 * based on list of questions
	 * 
	 *  @param questions list of questions
	 * 
	 */
	public void calculateMark(List<Question> questions) {
		
 	   double tmpMark = (((float)Statistics.calculateRightAnswers(questions, this))/(float)questions.size())*100;
 	   
 	   if(tmpMark<50) {
 		   mark = 2.0;      	   
 	   }else if(tmpMark<60) {
 		   mark = 3.0;
 	   }else if(tmpMark<70) {
 		   mark = 3.5;
 	   }else if(tmpMark<80) {
 		   mark = 4.0;
 	   }else if(tmpMark<85) {
 		   mark = 4.5;
 	   }else {
 		   mark = 5.0;
 	   }
	}
	
	@Override
	public String toString() {
		
		String autString = studentName + " \t";
		
		autString += studentAnswers[0];
		autString += studentAnswers[1];
		autString += studentAnswers[2];
		
		return autString;
	}
}
