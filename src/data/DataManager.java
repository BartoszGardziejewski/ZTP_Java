package data;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * DataManager manages data stored in application
 *
 * @author Bartosz Gardziejewski
 * @version 1.0.0
 */
public class DataManager {
	
	private ArrayList<Item> items;
	
	// Constructors
	public DataManager(){
		items = new ArrayList<Item>();
	}
	public DataManager(ArrayList<Item> items){
		this.items = items;
	}
	
	//	Getters
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public Item getItem(int index){
		return items.get(index);
	}
	
	//	Methods
	
	/**
	 * Loads data from XML file 
	 *
	 * @param path to XML file
	 */
	public void loadData(String path) {		
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ItemsWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        File file = new File(path);
	        
	        ItemsWrapper wrapper = (ItemsWrapper) um.unmarshal(file);

	        items.clear();
	        items.addAll( wrapper.getItems() );

	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + path+"\n"+e.toString());

	        alert.showAndWait();
	    }
	}
	
	/**
	 * Saves data in XML file 
	 *
	 * @param path to XML file
	 */
	public void saveData(String path) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ItemsWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        ItemsWrapper wrapper = new ItemsWrapper();
	        wrapper.setItems(items);
	        
	        File file = new File(path);
	        
	        m.marshal(wrapper, file);
	        
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + path);

	        alert.showAndWait();
	    }
	}

}

/**
 * ItemsWrapper wraps item list with JAXB data.
 *
 * @author Bartosz Gardziejewski
 * @version 1.0.0
 */
@XmlRootElement(name = "Items")
class ItemsWrapper {

    private ArrayList<Item> items;

    @XmlElement(name = "Item")
    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
