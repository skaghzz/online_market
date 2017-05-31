package term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class couponDAO {
	public static int[] getCoupon(String email){
		//mycart.jsp
		/*
		SELECT email,discount, numberofcoupon 
		FROM coupon 
		WHERE email = ? 
		ORDER BY discount ASC
		 */
		String SQL = "SELECT email,discount, numberofcoupon FROM coupon WHERE email = ? ORDER BY discount ASC";
		int[] coupon = new int[100];
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(SQL);
					
			//순서대로 ?를 채워준다
			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				coupon[rs.getInt("discount")] = rs.getInt("numberofcoupon");
			}
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return coupon;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		couponDAO dao = new couponDAO();
	}
}
