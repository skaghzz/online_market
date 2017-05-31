package term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AllClassDAO {
	
	public static int[] insertCartData(List<AllClass> cartItemList){
		//doPutInCart.jsp
		/*
		 INSERT INTO shoppingcart(email, itemcode, sellercode, numberofcart) 
		 				VALUES (?, ?, ?, ?)
		 */
		String SQL = "INSERT INTO shoppingcart(email, itemcode, sellercode, numberofcart) VALUES (?, ?, ?, ?)";
		int[] status = {0,};

		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(SQL);
			for(AllClass e : cartItemList){
				pst.setString(1, e.getUser().getEmail());
				pst.setString(2, e.getItem().getItemCode());
				pst.setString(3, e.getSeller().getSellerCode());
				pst.setInt(4, e.getItem().getCounter());
				pst.addBatch();
			}

			status = pst.executeBatch();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AllClassDAO dao = new AllClassDAO();
	}

}
