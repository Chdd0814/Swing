package ChocolateInventoryApp;

public class chocoDTO {
	private int chocoID;
	private String chocoName;
	private int price;
	private int quantity;
	
	public chocoDTO(int chocoID, String chocoName, int price, int quantity) {
		this.chocoID = chocoID;
		this.chocoName = chocoName;
		this.price = price;
		this.quantity = quantity;
	}

	public int getChocoID() {
		return chocoID;
	}

	public void setChocoID(int chocoID) {
		this.chocoID = chocoID;
	}

	public String getChocoName() {
		return chocoName;
	}

	public void setChocoName(String chocoName) {
		this.chocoName = chocoName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
