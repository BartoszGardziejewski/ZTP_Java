package data;

import javax.xml.bind.annotation.*;

import javafx.scene.image.Image;

/**
 * Item represents item loaded from XML 
 *
 * @author Bartosz Gardziejewski
 * @version 1.0.0
 */
@XmlType(name = "Item",propOrder = { "name", "quantity", "price" } )
@XmlRootElement(name = "Item")
public class Item {
	
	private String name;
	private Image icon;
	private int quantity;
	private double price;
	
	
	// Constructors
	public Item() {
		name = "Non";
		quantity = 0;
		price = 0;
		icon = new Image("file:./resources/noIcon.png");
	}
	public Item(String name, int quantity, double price){
		this.name = name;
		this.quantity= quantity;
		this.price = price;
		icon = new Image("file:./resources/noIcon.png");
	}
	public Item(String name, Image icon, int quantity, double price){
		this.name = name;
		this.quantity= quantity;
		this.price = price;
		this.icon = icon;
	}
		
	//	Getters
    @XmlElement (name = "name")
	public String getName() {
		return name;
	}
    public void setName(String name) {
    	this.name = name;
		icon = new Image("file:./resources/"+name+".png");
	}
    
    //@XmlElement (name = "icon")	
	public String getIconName() {
		return name;
	}
    
	public Image getIcon() {
		return icon;
	}
	
    @XmlElement (name = "quantity")
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
    	this.quantity = quantity;
	}

    @XmlElement (name = "price")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
    	this.price = price;
	}

	
    // Methods
	
	/**
	 * Modifies item quantity
	 *
	 * @param quantity that will by added or removed
	 */
	public void modifyQuantity(int quantity) {
		if(this.quantity+quantity>=0){
			this.quantity += quantity;
		}
	}
	
}
