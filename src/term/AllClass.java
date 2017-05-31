package term;

public class AllClass {
	private item Item;
	private user User;
	private seller Seller;
	private sellHistory _SellHistory;
	
	public item getItem() {
		return Item;
	}
	public void setItem(item item) {
		Item = item;
	}
	public user getUser() {
		return User;
	}
	public void setUser(user user) {
		User = user;
	}
	public seller getSeller() {
		return Seller;
	}
	public void setSeller(seller seller) {
		Seller = seller;
	}
	public sellHistory getSellHistory() {
		return _SellHistory;
	}
	public void setSellHistory(sellHistory _SellHistory) {
		this._SellHistory = _SellHistory;
	}
}
