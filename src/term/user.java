package term;

public class user{
	private String email;
	private String familyName;
	private String firstName;
	private String birthday;
	private int amountOfPurchase;
	private String password;
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setFamilyName(String _familyName){
		this.familyName = _familyName;
	}
	
	public void setFirstName(String _firstName){
		this.firstName = _firstName;
	}
	
	public void setbirthDate(String _birthday){
		this.birthday = _birthday;
	}
	
	public void setAmountOfPurchase(int _AOP){
		this.amountOfPurchase = _AOP;
	}
	
	public void setPassword(String _password){
		this.password = _password;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getFamilyName(){
		return this.familyName;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public String getbirthDate(){
		return this.birthday;
	}
	
	public int getAmountOfPurchase(){
		return this.amountOfPurchase;
	}
	public String getPassword(){
		return this.password;
	}
}
