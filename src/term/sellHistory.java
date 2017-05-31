package term;

public class sellHistory {
	private String email;
	private String itemCode;
	private String selltime;
	private int numberOfPurchase;
	private String coupon;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNumberOfPurchase() {
		return numberOfPurchase;
	}
	public void setNumberOfPurchase(int count) {
		this.numberOfPurchase = count;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getSelltime() {
		return selltime;
	}
	public void setSelltime(String selltime) {
		this.selltime = selltime;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
}
