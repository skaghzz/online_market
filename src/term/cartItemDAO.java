package term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class cartItemDAO {	
	
	public static int deleteCartItem(String email){
		//dobuy.jsp
		String query = "DELETE FROM shoppingcart WHERE email = ?";
		int status = 0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(query);
					
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
	
	public static List<item> selectCartItem(){
		//cartItem.jsp
		String query = "select * FROM item,(select itemcode,sellercode,sum(numberofcart) as oncart from shoppingcart group by itemcode,sellercode) tt where numberofstock < tt.oncart AND tt.itemcode = item.itemcode AND tt.sellercode = item.sellercode";
		List<item> cartItem = new ArrayList<>();
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(query);
					
			
			ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setSales(rs.getInt("numberofsales"));
                _item.setStock(rs.getInt("numberofstock"));
                _item.setCounter(rs.getInt("oncart"));
                
                
                cartItem.add(_item);
            }
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cartItem;
	}
	
	public static List<item> selectLessStock(String email){
		//mycart.jsp
		String query1 = "select * "
				+ "FROM item it "
				+ "WHERE numberofstock < (SELECT sum(numberofcart) AS numberofcart "
										+ "FROM shoppingcart sh "
										+ "WHERE email = ? "
										+ "AND sh.itemcode = it.itemcode "
										+ "AND sh.sellercode = it.sellercode "
										+ "GROUP BY itemcode,sellercode)";
		List<item> cartItem = new ArrayList<>();
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(query1);
			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setStock(rs.getInt("numberofstock"));
                
                cartItem.add(_item);
            }
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cartItem;
	}
	
	public static void deleteLessStock(String email){
		//mycart.jsp
		/*
		SELECT itemcode,sellercode 
		FROM item it 
		WHERE numberofstock < (SELECT sum(numberofcart) AS numberofcart 
								FROM shoppingcart sh 
								WHERE email = ? 
								AND sh.itemcode = it.itemcode 
								AND sh.sellercode = it.sellercode 
								GROUP BY itemcode,sellercode)
		 */
		String query1 = "select itemcode,sellercode FROM item it WHERE numberofstock < (SELECT sum(numberofcart) AS numberofcart FROM shoppingcart sh WHERE email = ? AND sh.itemcode = it.itemcode AND sh.sellercode = it.sellercode GROUP BY itemcode,sellercode)";
		List<item> cartItem = new ArrayList<>();
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(query1);
			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setItemCode(rs.getString("itemcode"));
                
                cartItem.add(_item);
            }
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String query2 = "DELETE FROM shoppingcart WHERE email = ? AND itemcode = ? AND sellercode = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(query2);
					
			//순서대로 ?를 채워준다
			for(item e : cartItem){
				pst.setString(1, email);
				pst.setString(2, e.getItemCode());
				pst.setString(3, e.getSellerCode());
				pst.addBatch();
			}
			
			pst.executeBatch();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		cartItemDAO dao = new cartItemDAO();
		//dao.selectAllitem();
		
	}

}
