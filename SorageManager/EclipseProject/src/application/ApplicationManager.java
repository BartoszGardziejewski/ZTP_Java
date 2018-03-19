package application;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import data.DataManager;
import data.Item;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * LanguageType is an enum that represents available languages
 */
enum LanguageType{
	Polish,
	English
}

/**
 * ApplicationManager manages and connects data with UI
 *
 * @author Bartosz Gardziejewski
 * @version 1.0.0
 */
public class ApplicationManager extends Application
{
	private ApplicationController controller;	
	private Locale currentLocale = new Locale("pl","PL");	
	
	private DataManager dataManager;
	private Stage currentStage;
	
	private double windowWidth = 800,windowHeight=600 ;
	
    public static void main( String[] args )
    {
    	launch(args);
    }

    
    @Override
	public void start(Stage stage) throws Exception {

    	dataManager = new DataManager();
    	dataManager.loadData("./resources/Items.xml");
    	
    	currentStage = stage;
		currentStage.setHeight(windowHeight);
		currentStage.setWidth(windowWidth);

		currentStage.getIcons().add(new Image("file:./resources/appIcon.png")); 
  	
    	loadMainWindow();
    	
		try {
			currentStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
    @Override
    public void stop() {
    	
    	dataManager.saveData("./resources/Items.xml");
    	
    	System.out.println("Application Stoped");
    }

    /**
     * This method sets current UI language
     * 
     * @param language that will by loaded to UI
     */
    public void setLanguage(LanguageType language){
		switch(language) {
		
		case Polish:
			currentLocale = new Locale("pl","PL");
			break;
			
		case English:
			currentLocale = new Locale("en","US");
			break;
			
		default:
			break;
		}
		
		loadLanguale();
	}

   /**
    * This method loads current language to UI elements
    */
	private void loadLanguale() {

	    ResourceBundle  messages;
		messages = ResourceBundle.getBundle("application.Language",currentLocale);
		
		if(controller.ContinueButton!=null) {
			controller.ContinueButton.setText( messages.getString("continue") );
			}	
		if(controller.LanguageManu!=null) {
			controller.LanguageManu.setText( messages.getString("language") );
			}
		if(controller.exitButton!=null) {
			controller.exitButton.setText( messages.getString("exit") );
			}
		if(controller.backButton!=null) {
			controller.backButton.setText( messages.getString("back") );
			}
		if(controller.preQuantityLabel!=null) {
			controller.preQuantityLabel.setText( messages.getString("preQuantity") );
			}
		if(controller.prePriceLabel!=null) {
			controller.prePriceLabel.setText( messages.getString("prePrice") );
			}

		if(controller.addItemButton!=null) {
			controller.addItemButton.setText( messages.getString("addItem") );
			}
		if(controller.removeItemButton!=null) {
			controller.removeItemButton.setText( messages.getString("removeItem") );
			}
		if(controller.preModItem!=null) {
			controller.preModItem.setText( messages.getString("preModItem") );
			}
		
		
		if(controller.dataFlow!=null) {
			loadItemList();
			}
		
		
	}
	
   /**
    * This method loads and shows main window
    */
	public void loadMainWindow() {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemList.fxml"));
		
		windowHeight = currentStage.getHeight();
    	windowWidth = currentStage.getWidth();
		
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		scene.getStylesheets().add("application/ApplicationStyle.css");
		
		controller = loader.getController();
		controller.setManager(this);
		
		loadLanguale();
		loadItemList();
		
		currentStage.setScene(scene);
		currentStage.setHeight(windowHeight);
		currentStage.setWidth(windowWidth);
		
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
  /**
    * This method loads and shows item data window
    * 
    * @param item whose data will by displayed 
    */
	public void loadItemDataWindow(Item item) {
	    ResourceBundle  messages;
		messages = ResourceBundle.getBundle("data.Item",currentLocale);
		
		windowHeight = currentStage.getHeight();
    	windowWidth = currentStage.getWidth();
    	
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemData.fxml"));
											
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		scene.getStylesheets().add("application/ApplicationStyle.css");
		
		controller = loader.getController();
		controller.setManager(this);
		
		loadItemData(item,messages);
		
		loadLanguale();

		if(controller.addItemButton!=null) {
			controller.addItemButton.setText( controller.addItemButton.getText() +" "+ messages.getString(item.getName()+"_2") );
			controller.addItemButton.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {	
			    	int modValue;
			    	try
			    	{
			    		modValue = Integer.parseInt(controller.modAmount.getText());
			    	} catch(Exception parsE) {
			    		modValue = 0;
					}			    	
			    	item.modifyQuantity( modValue );
					loadItemData(item,messages);
			    }
			});
			}
		if(controller.removeItemButton!=null) {
			controller.removeItemButton.setText( controller.removeItemButton.getText() +" "+ messages.getString(item.getName()+"_2") );
			controller.removeItemButton.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	int modValue;
			    	try
			    	{
			    		modValue = Integer.parseInt(controller.modAmount.getText());
			    	} catch(Exception parsE) {
			    		modValue = 0;
					}
			    	item.modifyQuantity(-modValue);
					loadItemData(item,messages);
			    }
			});
			}
		
		
		currentStage.setScene(scene);
		currentStage.setHeight(windowHeight);
		currentStage.setWidth(windowWidth);
		
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void loadItemData(Item item,ResourceBundle  messages) {

		String postfix;
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);;
		 
		if(item.getQuantity()==1) {
			postfix="_1";
		}else if(item.getQuantity()<5) {
			postfix="_2";
		}else if(item.getQuantity()<22) {
			postfix="_5";
		}else if( ((item.getQuantity()-10)%10 < 5) && ((item.getQuantity()-10)%10 >= 2))  {
			postfix="_2";
		}else {
			postfix="_5";
		}
		
		
		controller.IconImage.setImage( item.getIcon() );
		controller.nameLabel.setText( messages.getString(item.getName()+"_1") );
		controller.quantityLabel.setText( String.valueOf(item.getQuantity()) + " " + messages.getString(item.getName()+postfix) );
		controller.priceLabel.setText( numberFormatter.format(item.getPrice()) );

	}
	
	private void loadItemList(){
		
	    ResourceBundle  messages;
		messages = ResourceBundle.getBundle("data.Item",currentLocale);
		
		ArrayList<StackPane> itemButtons = new ArrayList<StackPane>();
		
		for(Item item:dataManager.getItems()) {
			StackPane stack = new StackPane();
			
			Button tmpBut = new Button(); 
			tmpBut.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	loadItemDataWindow(item);
			    }
			});
			
			VBox tmpBox = new VBox();
			tmpBox.setMouseTransparent(true);
			tmpBox.setAlignment(Pos.CENTER);
			
			ImageView tmpIcon = new ImageView(item.getIcon());
			tmpIcon.setMouseTransparent(true);
			
			Label tmpName = new Label(messages.getString(item.getName()+"_2"));
			tmpName.setMouseTransparent(true);
			
			tmpBox.getChildren().addAll(tmpIcon,tmpName);
			
			tmpBox.getStyleClass().add("box-item");
			tmpBut.getStyleClass().add("button-item");
			
			stack.getChildren().addAll(tmpBut,tmpBox);
			itemButtons.add(stack);
		}
		
		
		controller.dataFlow.getChildren().setAll(itemButtons);
	}

}
