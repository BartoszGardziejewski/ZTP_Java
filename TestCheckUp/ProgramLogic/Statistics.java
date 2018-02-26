package Data;

import java.util.List;


/**
 * Statistics class performs statistic calculation
 * 
 * 
 * @author Bartosz Gardziejewski
 * @version 1.0
 */
public class Statistics {

	/**
	 * This method calculates how many of the answers where correct 
	 * for each question 
	 * 
	 * @param questions represents list of questions 
	 * @param answers represents list of students answers 
	 * 
	 * @return array of integers where index represents number of question 
	 * and integer under that index represents how many of the answers where correct 
	 * 
	 */
	public static int[] calculateRightAnswers(List<Question> questions, List<StudentAnswer> answers) {
		
		int questionNumber = 0;
		int[] rightAnswers = new int[questions.size()];
		
		for(Question question:questions) {

			int calculateRigthAnswers = 0;
			
			for(StudentAnswer answer:answers) {
				
				if(question.isAnswerCorrect(answer.studentAnswers[questionNumber])) {
					calculateRigthAnswers++;
				}
			}
			
			rightAnswers[questionNumber] = calculateRigthAnswers;				
			questionNumber++;
		}
		
		return rightAnswers;
	}
	
	/**
	 * This method calculates how many of the answers where correct 
	 * for given student 
	 * 
	 * @param questions represents list of questions 
	 * @param answer represents student answers 
	 * 
	 * @return integer that represent how many answers where correct
	 * 
	 */
	public static int calculateRightAnswers(List<Question> questions, StudentAnswer answer) {
		
		int questionNumber = 0;
		int rigthAnswers = 0;
		for(Question question:questions) {			
				
			if(question.isAnswerCorrect(answer.studentAnswers[questionNumber])) {
				rigthAnswers++;
			}
			
			questionNumber++;
		}
		
		return rigthAnswers;
	}
}
