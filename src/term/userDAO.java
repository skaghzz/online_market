package term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userDAO {
	
	public static int insertUser(user _user) {
		//doregister.jsp
		int status = 0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					"INSERT INTO client (email,familyname,firstname,birthday,pw) VALUES(?, ?, ?, ?, ?)");
			//순서대로 ?를 채워준다
			pst.setString(1, _user.getEmail());
			pst.setString(2, _user.getFamilyName());
			pst.setString(3, _user.getFirstName());
			pst.setString(4, _user.getbirthDate());
			pst.setString(5, _user.getPassword());
			
			status = pst.executeUpdate();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static Boolean checkEmail(user _user){
		//dologin.jsp
		/*
		 SELECT * 
		 FROM client 
		 WHERE email = ? 
		 AND pw = ?
		 */
		Boolean isLogin = false;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					"SELECT * FROM client WHERE email = ? AND pw = ?"
					);
					
			//순서대로 ?를 채워준다
			pst.setString(1, _user.getEmail());
			pst.setString(2, _user.getPassword());
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				//rs.next가 true라면 = 정보가 있다
				isLogin = true;
			}
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isLogin;
	}
	
	public static int updateTotalBuyPrice(String email, int buyPrice) {
		//dobuy.jsp
		String query = "UPDATE client SET totalpurchase = totalpurchase + ? WHERE email = ?";
		int status = 0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(query);
			
			//순서대로 ?를 채워준다
			pst.setInt(1, buyPrice);
			pst.setString(2, email);
			
			status = pst.executeUpdate();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int updateCouponNumber(String email, String couponNumber) {
		//dobuy.jsp
		int status = 0;
		if(couponNumber.equals("0")){//쿠폰이 0일때
			status = 1;
			return status;
		}
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = null;
			if(couponNumber.equals("10")){
				pst = conn.prepareStatement(
						"UPDATE coupon SET numberofcoupon = numberofcoupon - 1 WHERE email = ? AND discount = 10"
						);
			}else if(couponNumber.equals("30")){
				pst = conn.prepareStatement(
						"UPDATE coupon SET numberofcoupon = numberofcoupon - 1 WHERE email = ? AND discount = 30"
						);
			}
			
			//순서대로 ?를 채워준다
			pst.setString(1, email);
			
			status = pst.executeUpdate();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static int updateGiveOneCoupon(String email, int couponNumber) {
		//dobuy.jsp
		/*
		UPDATE coupon 
		SET numberofcoupon = numberofcoupon + 1 
		where email = ? 
		AND discount = 10;
		 
		INSERT INTO coupon(email, discount, numberofcoupon) 
		select ?,10,1 
		WHERE not exists (select 1 
						  FROM coupon 
						  WHERE email=? 
						  AND discount = 10)
		 */
		int status = 0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = null;
			if(couponNumber == 10){
				pst = conn.prepareStatement(
						"UPDATE coupon SET numberofcoupon = numberofcoupon + 1 where email = ? AND discount = 10; INSERT INTO coupon(email, discount, numberofcoupon) select ?,10,1 where not exists (select 1 from coupon where email=? AND discount = 10);"
						);
			}else if(couponNumber == 30){
				pst = conn.prepareStatement(
						"UPDATE coupon SET numberofcoupon = numberofcoupon + 1 where email = ? AND discount = 30; INSERT INTO coupon(email, discount, numberofcoupon) select ?,30,1 where not exists (select 1 from coupon where email=? AND discount = 30);"
						);
			}
			
			//순서대로 ?를 채워준다
			pst.setString(1, email);
			pst.setString(2, email);
			pst.setString(3, email);
			
			status = pst.executeUpdate();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	public static user selectUser(String email){
		//listUser.jsp
		//dobuy.jsp
		//mycart.jsp
		/*
		 "SELECT * 
		 FROM client 
		 WHERE email = ?"
		 */
		user _user = new user();
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					"SELECT * FROM client WHERE email = ?"
					);
					
			//순서대로 ?를 채워준다
			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
			rs.next();
			
			_user.setEmail(rs.getString("email"));
			_user.setFamilyName(rs.getString("familyname"));
			_user.setFirstName(rs.getString("firstname"));
			_user.setbirthDate(rs.getString("birthday"));
			_user.setAmountOfPurchase(rs.getInt("totalpurchase"));
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return _user;
	}
	
	public static ArrayList<user> selectEmail(){
		ArrayList<user> userList = new ArrayList<>();
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement("SELECT email FROM client");
					
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				user _user = new user();
				_user.setEmail(rs.getString("email"));
				userList.add(_user);
			}
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		userDAO dao = new userDAO();
	}

}
