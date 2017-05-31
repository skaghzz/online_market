package term;

public class item {
	private String itemcode;
	private String itemname;
	private int price;
	private String brand; 
	private String sellerCode;
	private int stock;
	private int sales;
	private int counter;
	private int totalPrice;
	
	public String getItemCode() {
		return itemcode;
	}
	public void setItemCode(String code) {
		itemcode = code;
	}
	public String getItemName() {
		return itemname;
	}
	public void setItemName(String name) {
		this.itemname = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int bestSellItemCount) {
		counter = bestSellItemCount;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

}
