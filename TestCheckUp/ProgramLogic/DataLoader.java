package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Data.Question;
import Data.StudentAnswer;

/**
 * DataLoader class is use to load specified data
 * 
 * 
 * @author Bartosz Gardziejewski
 * @version 1.0
 */
public class DataLoader {
	
	
	/**
	 * This method loads students answers from .CSV file. 
	 * It also performs {@link StudentAnswer#calculateMark(List)} 
	 * to calculate mark for every loaded student
	 * 
	 * @param path represents path to file
	 * @param form represents list of question in this case use as test template
	 * 
	 * @return list of student answers loaded from file
	 * 
	 * @see StudentAnswer
	 */
	public List<StudentAnswer> loadCSV(String path,List<Question> form){
	
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	
	String name;
	
	System.out.println( form.size() );
	
	List<StudentAnswer> studentsAnswer = new ArrayList<>();
	
    try {

        br = new BufferedReader(new FileReader(path));

        
        
        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] studentData = line.split(cvsSplitBy);
            String[] answers = new String[ form.size() ];
            
            name = studentData[0];
                           
            for(int i=0 ; i < form.size() ; i++) {

            	answers[i] = studentData[i+1];               		
            }
            
            StudentAnswer tmpAnswer = new StudentAnswer(name,answers);
            tmpAnswer.calculateMark(form);
            
            studentsAnswer.add(tmpAnswer);
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
        		return studentsAnswer;
        		
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    return null;	        
}

	/**
	 * This method loads questions from .XML file.
	 * 
	 * @param path represents path to file
	 * 
	 * @return list of questions loaded from file
	 * 
	 * @see Question
	 */
	public List<Question> loadXML(String path) {
	
      try {
    	  List<Question> questionsList = new ArrayList<Question>() ;
    	  
    	 // String toPrint="";
    	  
    	  
          File inputFile = new File(path);
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(inputFile);
          doc.getDocumentElement().normalize();
          
          NodeList nList = doc.getElementsByTagName("question");
          
          for (int temp = 0; temp < nList.getLength(); temp++) {
             Node nNode = nList.item(temp);
             
             Question question = new Question();  
             
             if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                
                question.setContent(eElement.getAttribute("content") + "?");
                question.setRightAnswer(eElement.getAttribute("answerCode")+"");
                
                //toPrint += eElement.getAttribute("content") + "?" + "\n";
                //System.out.println(eElement.getAttribute("content") + "?");
                
                NodeList eList = eElement.getElementsByTagName("answer");
                
                for (int temp2 = 0; temp2 < eList.getLength(); temp2++) {
                	 Node eNode = eList.item(temp2);
                	 Element aElement = (Element) eNode;
 	                
                	 question.addAnswer(aElement.getTextContent());
                	 //toPrint += "\t" + aElement.getTextContent()+ "\n";
                	//System.out.println("\u001B31;1m"+aElement.getTextContent());
                }
                
                
             }
             questionsList.add(question);
          }
          
          return questionsList;
          
       } catch (Exception e) {
          e.printStackTrace();
          return null;
       }
}


}
