package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * ApplicationController control data flow between UI elements and Applicationmanager
 *
 * @author Bartosz Gardziejewski
 * @version 1.0.0
 */
public class ApplicationController {
	
	private ApplicationManager manager;
	
	public BorderPane WelcomePane;
	public AnchorPane BazeAnchor;
	
	public Button ContinueButton;
	public Button exitButton;
	public Button backButton;
	
	public Button addItemButton;
	public Button removeItemButton;
	
	public MenuButton LanguageManu;
	public FlowPane dataFlow;
	
	public ImageView IconImage;
	public Label nameLabel;
	public Label quantityLabel;
	public Label priceLabel;
	public Label preQuantityLabel;
	public Label prePriceLabel;
	public Label preModItem;
	public TextField modAmount;
	
	/**
	 * This method sets controller manager
	 * 
	 * @param manager that will communicate with controller
	 */
	public void setManager(ApplicationManager manager) {
		this.manager = manager;
	}
	
	/**
	 * This method sets language to Polish
	 */
	public void setLanguagePolish(ActionEvent event) {
		 System.out.println("Language: Polish ");
		 manager.setLanguage(LanguageType.Polish);
	}
	
	/**
	 * This method sets language to English
	 */
	public void setLanguageEnglish(ActionEvent event) {
		 System.out.println("Language: English, US");
		 manager.setLanguage(LanguageType.English);
		 
	}
	
	/**
	 * This method exits application 
	 */
	public void exitApplication(ActionEvent event) {
		 System.out.println("exited");
		 try {
			 Platform.exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	/**
	 * This method returns to main window
	 */
	public void bactToItemList(ActionEvent event) {
		manager.loadMainWindow();
	}
			
}



