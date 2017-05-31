package term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;

public class sellHistoryDAO {
	
	public static int insertCartToSellHistory(String email,String coupon){
		//dobuy.jsp
		/*
		 INSERT INTO sellhistory(email,itemcode,sellercode,numberofpurchase,purchasetime,coupon) 
		 	SELECT ?,itemcode,sellercode,sum(numberofcart),to_timestamp(to_char(now(),'yyyy-MM-DD HH24:MI:SS'),'yyyy-MM-DD HH24:MI:SS'),? 
		 	FROM shoppingcart 
		 	where email=? 
		 	GROUP BY itemcode,sellercode
		 */
		String SQL = "INSERT INTO sellhistory(email,itemcode,sellercode,numberofpurchase,purchasetime,coupon) SELECT ?,itemcode,sellercode,sum(numberofcart),to_timestamp(to_char(now(),'yyyy-MM-DD HH24:MI:SS'),'yyyy-MM-DD HH24:MI:SS'),? FROM shoppingcart where email=? GROUP BY itemcode,sellercode";
		int status = 0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					SQL
					);
					
			//순서대로 ?를 채워준다
			pst.setString(1, email);
			pst.setString(2, coupon);
			pst.setString(3, email);
			
			status = pst.executeUpdate();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static List<AllClass> selectUserHistory(String email) {
		//myhistory.jsp
		/*
		SELECT * 
		FROM (SELECT DISTINCT * 
			  FROM seller 
			  NATURAL join item )ab 
		NATURAL JOIN sellhistory
		WHERE email = ?
		 */
		List<AllClass> userHistoryList = new ArrayList<>();
		String query = "select * from (select DISTINCT * from seller NATURAL join item )ab NATURAL JOIN sellhistory WHERE email = ?";
        try {
            Connection conn = ConnectionProvider.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = null;
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, email);
            
            rs = pst.executeQuery();
            while(rs.next()) {
            	item _item = new item();
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
            	_item.setSellerCode(rs.getString("sellercode"));
            	_item.setPrice(rs.getInt("price"));
                
            	sellHistory _sellHistory = new sellHistory();
                
                
                _sellHistory.setItemCode(rs.getString("itemcode"));
                _sellHistory.setNumberOfPurchase(rs.getInt("numberofpurchase"));
                _sellHistory.setSelltime(rs.getString("purchasetime"));
                _sellHistory.setCoupon(rs.getString("coupon"));
                
                seller _seller = new seller();
                _seller.setSellerCode(rs.getString("sellercode"));
                _seller.setSellerName(rs.getString("sellername"));
                
                AllClass _AllClass = new AllClass();
                _AllClass.setItem(_item);
                _AllClass.setSeller(_seller);
                _AllClass.setSellHistory(_sellHistory);
                
                userHistoryList.add(_AllClass);
            }
            
            st.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return userHistoryList;
}
	
	public static List<AllClass> selectSellerHistory(String sellercode) {
		List<AllClass> userHistoryList = new ArrayList<>();
		String query = "select * from sellhistory NATURAL JOIN item WHERE sellercode=?";
        try {
            Connection conn = ConnectionProvider.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = null;
            PreparedStatement pst = conn.prepareStatement(query);
            
            pst.setString(1, sellercode);
            
            rs = pst.executeQuery();
            while(rs.next()) {
            	item _item = new item();
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
            	_item.setSellerCode(rs.getString("sellercode"));
            	_item.setPrice(rs.getInt("price"));
                
            	sellHistory _sellHistory = new sellHistory();
                
                
                _sellHistory.setItemCode(rs.getString("itemcode"));
                _sellHistory.setNumberOfPurchase(rs.getInt("numberofpurchase"));
                _sellHistory.setSelltime(rs.getString("purchasetime"));
                _sellHistory.setEmail(rs.getString("email"));
                _sellHistory.setCoupon(rs.getString("coupon"));
                
                AllClass _AllClass = new AllClass();
                _AllClass.setItem(_item);
                _AllClass.setSellHistory(_sellHistory);
                
                userHistoryList.add(_AllClass);
            }
            
            st.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return userHistoryList;
}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sellHistoryDAO dao = new sellHistoryDAO();
		    //dao.selectAllSeller();
	}

}
