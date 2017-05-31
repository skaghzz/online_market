package term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class sellerDAO {
	
	public static Boolean checkAccount(seller _seller){
		Boolean isLogin = false;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					"SELECT * FROM seller WHERE sellercode = ? AND pw = ?");
					
			//순서대로 ?를 채워준다
			pst.setString(1, _seller.getSellerCode());
			pst.setString(2, _seller.getPassword());
			
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
	
	public static seller selectSellerName(String sellercode){
		//mycart.jsp
		//sellerHistory.jsp - 관리자 모드
		seller _seller = new seller();
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					"SELECT * FROM seller WHERE sellercode = ?"
					);
					
			//순서대로 ?를 채워준다
			pst.setString(1, sellercode);
			
			ResultSet rs = pst.executeQuery();
			rs.next();
			_seller.setSellerName(rs.getString("sellername"));
			
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return _seller;
	}
	
	public static ArrayList<seller> selectSellercode(){
		ArrayList<seller> sellerList = new ArrayList<>();
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement("SELECT sellercode,sellername FROM seller");
					
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				seller _seller = new seller();
				_seller.setSellerCode(rs.getString("sellercode"));
				_seller.setSellerName(rs.getString("sellername"));
				sellerList.add(_seller);
			}
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sellerList;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sellerDAO dao = new sellerDAO();
	}

}
