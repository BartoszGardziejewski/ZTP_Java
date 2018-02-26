package application;

import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Controller class is created to handle connection between UI and Main 
 * And callback methods of UI elements 
 * 
 * @author Bartosz Gardziejewski
 * @version 1.0
 */
public class Controller {

	private Main main;
	
	/**
	 * This parameter represents table of students answers and marks.
	 */
	public Pane dataTable;
	
	/**
	 * This parameter represents test template.
	 */
	public VBox testTemplate;
	
	/**
	 * This parameter represents bar chart of student answers.
	 */
	public BarChart<String,Integer> statsBarChart;
	
	/**
	 * This parameter represents button showed in welcome window.
	 */
	public Button continueButton;
	
	
	
	/**
	 * This method allows Main class to set Controller main parameter.
	 * 
	 * @param main
	 */
	public void setMainClass(Main main) {
		this.main= main;
	}
	
	/**
	 * This method is {@link Controller#continueButton} callback, that calls {@link Main#loadMainWindow()}
	 */
	public void loadMainWidnow(){
		main.loadMainWindow();
	}
}
