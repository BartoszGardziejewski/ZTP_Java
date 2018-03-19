package Data;

import java.util.ArrayList;

/**
 * Question class represents set of question with list of answers
 * and specified which one of them is right 
 * 
 * 
 * @author Bartosz Gardziejewski
 * @version 1.0
 */
public class Question {
	
	private ArrayList<String> answers;
	private String rightAnswer;
	private String content;
	
	// Constructor 
	Question(){
		answers = new ArrayList<String>();
		rightAnswer = "";
		content = "";
	}
	
	// Getters
	public String getContent() {
		return content;
	}
	
	// Setters
	public void setRightAnswer(String answer) {
		rightAnswer = answer;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void  addAnswer(String answer) {
		answers.add(answer);
	}
	
	/**
	 * This method checks if given answer is correct
	 * 
	 * @param answer is a string that represents the answer,
	 * but the code letter of the answer is enough for this method 
	 * 
	 * @return boolean that answers question "is given answer correct ?"
	 */
	public boolean isAnswerCorrect(String answer) {
		return ( (answer.substring(0, 1)).compareTo(rightAnswer) == 0 );
	}
	
	
	@Override
	public String toString() {
		String autString = content + "\n";
		
		for(String answer : answers ) {
			
		autString += answer + "\n";
			
		}
		
		return autString;
	}

}
