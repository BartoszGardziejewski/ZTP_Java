package application;
	
import java.util.ArrayList;
import java.util.List;

import Data.*;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.TextFlow;

/**
 * Main class is extending javafx.application 
 * 
 * @author Bartosz Gardziejewski
 * @version 1.0
 */
public class Main extends Application {
	
	private Controller controller = null;
	private TableView<StudentAnswer> dataTable;
	private Stage currentStage;
	
	/**
	 * This method creates and shows welcomeWindow.
	 */
	public void loadWelcomeWindow() {
		try {
			FXMLLoader welcomeloader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
			
			Parent root = welcomeloader.load();
			Scene scene = new Scene(root,790,590);
			controller = welcomeloader.getController();
			controller.setMainClass(this);
			
			currentStage.setResizable(false);
			currentStage.setScene(scene);

			scene.getStylesheets().add("application/application.css");
			
			currentStage.getIcons().add(new Image("file:./resources/icon.png")); 
		
			currentStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates and shows mainWindow.
	 * <p>
	 * It is also calling data loader methods:
	 * <p>	Loads StudentAnswer
	 * 		{@link DataLoader#loadCSV(String, List)}
	 * <p>	Loads Question
	 * 		{@link DataLoader#loadXML(String)}
	 * <p>
	 * And populates UI with methods:
	 * <p>	Populate Table
	 * 		{@link Main#loadDatatoTable(List, List)}
	 * <p>	Loads Test Template
	 * 		{@link Main#loadTestTemplate(List)}
	 * <p>	Loads Bars Chars and Statistics
	 * 		{@link Main#loadBarChar(List, List)}
	 * 
	 */
	public void loadMainWindow() {
		try {
			FXMLLoader mainloader = new FXMLLoader(getClass().getResource("Main.fxml"));
			
			Parent root = mainloader.load();
			Scene scene = new Scene(root,800,600);
			controller = mainloader.getController();
			controller.setMainClass(this);
			
			DataLoader dataLoader = new DataLoader();
			
			List<Question> questions = new ArrayList<>();
			List<StudentAnswer> students = new ArrayList<>();
			
			scene.getStylesheets().add("application/application.css");
			
			questions = dataLoader.loadXML("./resources/Szablon.xml");		
			students = dataLoader.loadCSV("./resources/Test.csv",questions);
			
			loadTestTemplate(questions);
			loadDatatoTable(questions,students);
			loadBarChar(questions,students);
		
			currentStage.setResizable(false);
			currentStage.setScene(scene);
			
			currentStage.getIcons().add(new Image("file:./resources/icon.png")); 
		
			currentStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		currentStage = primaryStage;
		loadWelcomeWindow();	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method populates data table in UI
	 * <p>
	 * @param questions represents collection of questions that define test template
	 * <p>
	 * @param students represents collection of students answers
	 */
	private void loadDatatoTable(List<Question> questions,List<StudentAnswer> students){
	     		
	    ObservableList<StudentAnswer> tableData = FXCollections.observableArrayList();
	    tableData.addAll(students);
	    
	    List< TableColumn<StudentAnswer, String> > answerList = new ArrayList<>();	     	     
	    
	    TableColumn<StudentAnswer, String> tmpCol= new TableColumn<>("Student");
	    tmpCol.setMinWidth(150);
	    tmpCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));

	    answerList.add(tmpCol);

	   for(int i=0 ; i<questions.size() ; i++) {
	    	
	    	tmpCol = new TableColumn<>("pytanie "+(i+1));
		    tmpCol.setMinWidth(50);
		    final int number = i;
		    tmpCol.setCellValueFactory(new Callback<CellDataFeatures<StudentAnswer, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<StudentAnswer, String> p) {
                    return new SimpleStringProperty((p.getValue().getStudentAnswers(number)));
                }
		    });
		    
	 	    answerList.add(tmpCol);    	 
	    }	        
	   	
   		tmpCol = new TableColumn<>("Zdobyte punkty");
	    tmpCol.setMinWidth(50);
	    
	    tmpCol.setCellValueFactory(new Callback<CellDataFeatures<StudentAnswer, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(CellDataFeatures<StudentAnswer, String> p) {
               return new SimpleStringProperty( (Statistics.calculateRightAnswers(questions, p.getValue()))+"/"+questions.size() );
           }
	    });
	    
	    answerList.add(tmpCol);    	
	    
   		tmpCol = new TableColumn<>("Ocena");
	    tmpCol.setMinWidth(50);
	    
	    
	    
	    tmpCol.setCellValueFactory(new Callback<CellDataFeatures<StudentAnswer, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(CellDataFeatures<StudentAnswer, String> p) {
               return new SimpleStringProperty( p.getValue().getMark()+"" );
           }
           
           
	    });
	    
	    answerList.add(tmpCol);  
	    
		dataTable = new TableView<>();
		dataTable.setId("table");
		dataTable.setPrefHeight(550);
		
	    dataTable.setItems(tableData);
	    dataTable.getColumns().addAll(answerList);

		controller.dataTable.getChildren().add(dataTable);
	}
	
	/**
	 * This method populates test template in UI
	 * <p>
	 * @param questions represents collection of questions that define test template
	 * @see {@link Controller#testTemplate}
	 */
	private void loadTestTemplate(List<Question> questions){
		
		//List<TextArea> questionsFields = new ArrayList<>();
		
		List<TextFlow> questionsFields = new ArrayList<>();
		List<TextField> questionsLine = new ArrayList<>();
		
		for(Question question:questions) {		

			String[] questionData = question.toString().split("\n");
			
						
			TextField questionLabel = new TextField(questionData[0]);
			questionLabel.getStyleClass().set(0, "questionLabel");
			questionLabel.setPrefWidth(800);
			questionLabel.setEditable(false);
			
			questionsLine.add(questionLabel);
			
			for(int i=1; questionData.length > i; i++) {
				TextField answerLabel = new TextField("         "+questionData[i]);
				
				if( question.isAnswerCorrect( questionData[i] ) ){
					answerLabel.getStyleClass().set(0, "rightAnswerLabel");					
				} else {
					answerLabel.getStyleClass().set(0, "answerLabel");
				}
				
				
				answerLabel.setPrefWidth(800);
				answerLabel.setEditable(false);
				
				questionsLine.add(answerLabel);
			}
			
			TextField answerLabel = new TextField();
			answerLabel.getStyleClass().set(0, "answerLabel");
			answerLabel.setPrefWidth(800);
			answerLabel.setEditable(false);
			
			questionsLine.add(answerLabel);
			
			TextFlow textBox = new TextFlow();
			textBox.setPrefWidth(800);
			textBox.setLineSpacing(2);
			textBox.getChildren().addAll(questionsLine);			
			
			
			questionsFields.add(textBox);
		}
		
		controller.testTemplate.getChildren().addAll(questionsFields);
	}
	
	/**
	 * This method populates bar chart in UI.
	 * <p>
	 * It is using static method from Statistics class
	 * <p>
	 * {@link  Statistics#calculateRightAnswers(List, List)}
	 * <p>
	 * 
	 * @param questions represents collection of questions that define test template
	 * <p>
	 * @param students represents collection of students answers
	 * 
	 * @see Statistics
	 * @see Controller#statsBarChart
	 */
	private void loadBarChar(List<Question> questions,List<StudentAnswer> students) {

	        
		XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
		series1.setName("poprawne odpowiedzi");
		
		
		XYChart.Series<String,Integer> series2 = new XYChart.Series<>();
		series2.setName("b³êdne odpowiedzi");       
		
		int i=0;
		for(Question question:questions) {
			int rightAnswers = Statistics.calculateRightAnswers(questions, students)[i];
					
			series1.getData().add(new XYChart.Data<String,Integer>(question.getContent(), rightAnswers));
		    series2.getData().add(new XYChart.Data<String,Integer>(question.getContent(), students.size()-rightAnswers));  
		    i++;
		}
		
		//series1.getNode().setStyle("-fx-bar-fill : black");
  
	    controller.statsBarChart.getData().addAll(series1,series2);
	}

}
