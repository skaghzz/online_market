package term;

public class seller{
	private String sellerCode;
	private String sellerName;
	private String password;
	
	public void setSellerCode(String _sellercode){
		this.sellerCode = _sellercode;
	}
	
	public void setPassword(String _password){
		this.password = _password;
	}
	
	public String getSellerCode(){
		return this.sellerCode;
	}
	public String getPassword(){
		return this.password;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
}
