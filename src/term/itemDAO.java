package term;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class itemDAO {
	public static int insertItem(item _item) {
		int status = 0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					"INSERT INTO item(itemcode, itemname, brand, sellercode, price, numberofstock, numberofsales) VALUES(?, ?, ?, ?, ?,?,?)"
					);
			
			//순서대로 ?를 채워준다
			pst.setString(1, _item.getItemCode());
			pst.setString(2, _item.getItemName());
			pst.setString(3, _item.getBrand());
			pst.setString(4, _item.getSellerCode());
			pst.setInt(5, _item.getPrice());
			pst.setInt(6, _item.getStock());
			pst.setInt(7, 0);
			
			status = pst.executeUpdate();
			
			pst.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static List<item> selectCartItem(String email) {
		//mycart.jsp
		String SQL = "SELECT item.itemcode,itemname,brand,price,item.sellercode,numberofstock,numberofsales,numberofcart,numberofcart*price AS totalprice "
				+ "FROM item,(SELECT itemcode,sellercode,sum(numberofcart) AS numberofcart "
							+ "FROM shoppingcart "
							+ "WHERE email = ? "
							+ "GROUP BY itemcode,sellercode) tempTable "
				+ "WHERE item.itemcode = tempTable.itemcode "
				+ "AND item.sellercode = tempTable.sellercode";
        List<item> cartItem = new ArrayList<item>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = null;
            PreparedStatement pst = conn.prepareStatement(SQL);
            
            pst.setString(1, email);
            
            rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
               
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setPrice(rs.getInt("price"));
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setStock(rs.getInt("numberofstock"));
                _item.setSales(rs.getInt("numberofsales"));
                _item.setCounter(rs.getInt("numberofcart"));
                _item.setTotalPrice(rs.getInt("totalprice"));
                
                cartItem.add(_item);
            }
            
            st.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return cartItem;
}
	
	public static int insertUser(user _user) {
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
	
	public static int updateSellCount(String email) {
		//dobuy.jps
		/*
		UPDATE item 
		SET numberofsales = numberofsales + (SELECT sum(numberofcart) 
											FROM shoppingcart 
											WHERE item.itemcode = shoppingcart.itemcode 
											AND item.sellercode = shoppingcart.sellercode 
											AND email=? 
											GROUP BY itemcode,sellercode),
			numberofstock = numberofstock - (SELECT sum(numberofcart) 
											FROM shoppingcart  
											WHERE item.itemcode = shoppingcart.itemcode 
											AND item.sellercode = shoppingcart.sellercode 
											AND email=? 
											GROUP BY itemcode,sellercode) 
		WHERE EXISTS (SELECT sum(numberofcart) 
					  FROM shoppingcart 
					  WHERE item.itemcode = shoppingcart.itemcode 
					  AND item.sellercode = shoppingcart.sellercode 
					  AND email=? 
					  GROUP BY itemcode,sellercode);
		 */
		String SQL = "UPDATE item SET numberofsales = numberofsales + (SELECT sum(numberofcart) FROM shoppingcart WHERE item.itemcode = shoppingcart.itemcode AND item.sellercode = shoppingcart.sellercode AND email=? GROUP BY itemcode,sellercode),numberofstock = numberofstock - (SELECT sum(numberofcart) FROM shoppingcart  WHERE item.itemcode = shoppingcart.itemcode  AND item.sellercode = shoppingcart.sellercode  AND email=? GROUP BY itemcode,sellercode) WHERE EXISTS (SELECT sum(numberofcart) FROM shoppingcart  WHERE item.itemcode = shoppingcart.itemcode AND item.sellercode = shoppingcart.sellercode AND email=? GROUP BY itemcode,sellercode);";
		int status = 0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(SQL);
			
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
	
	public static Boolean checkEmail(user _user){
		Boolean isLogin = false;
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			PreparedStatement pst = conn.prepareStatement(
					"SELECT * FROM client WHERE email = ? AND pw = ?");
					
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
	
	public static ArrayList<AllClass> selectAllitem() {
		//listUser.jsp
		/*
		 SELECT DISTINCT * 
		 FROM item 
		 NATURAL JOIN seller 
		 ORDER BY itemcode
		 */
		String sql = "SELECT DISTINCT * FROM item NATURAL JOIN seller ORDER BY itemcode";
        ArrayList<AllClass> listitem = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            Statement st = conn.createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
            	AllClass _AllClass = new AllClass();
                item _item = new item();
                seller _seller = new seller();
                
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setPrice(rs.getInt("price"));
                _item.setBrand(rs.getString("brand"));
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setStock(rs.getInt("numberofstock"));
                _item.setSales(rs.getInt("numberofsales"));
                _AllClass.setItem(_item);
                
                _seller.setSellerName(rs.getString("sellername"));
                _seller.setSellerCode(rs.getString("sellercode"));
                _AllClass.setSeller(_seller);
                
                listitem.add(_AllClass);
            }
            
            st.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return listitem;
	}
	
	public static ArrayList<AllClass> selectSearchitem(String searchKey, String searchValue) {
		//listUser.jsp
		/*
		SELECT DISTINCT * 
		FROM item 
		NATURAL JOIN seller 
		WHERE 'searchKey' like ? 
		ORDER BY itemcode
		 */
		String sql = "SELECT DISTINCT * FROM item NATURAL JOIN seller WHERE "+searchKey+" like ? ORDER BY itemcode";
        ArrayList<AllClass> listitem = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            
            //pst.setString(1, searchKey);
            pst.setString(1, "%"+searchValue+"%");
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
            	AllClass _AllClass = new AllClass();
                item _item = new item();
                seller _seller = new seller();
                
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setPrice(rs.getInt("price"));
                _item.setBrand(rs.getString("brand"));
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setStock(rs.getInt("numberofstock"));
                _item.setSales(rs.getInt("numberofsales"));
                _AllClass.setItem(_item);
                
                _seller.setSellerName(rs.getString("sellername"));
                _seller.setSellerCode(rs.getString("sellercode"));
                _AllClass.setSeller(_seller);
                
                listitem.add(_AllClass);
            }
            
            pst.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return listitem;
	}
	
	public static ArrayList<item> selectSellerItem(String sellercode) {
		//listUser.jsp
		String sql = "SELECT * from item where sellercode = ?;";
        ArrayList<item> listitem = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
					
			//순서대로 ?를 채워준다
			pst.setString(1, sellercode);
			
			ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setPrice(rs.getInt("price"));
                _item.setBrand(rs.getString("brand"));
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setStock(rs.getInt("numberofstock"));
                _item.setSales(rs.getInt("numberofsales"));
                listitem.add(_item);
            }
            
            pst.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return listitem;
	}
	
	public static item selectItemCode(String _itemCode) {
		 item _item = new item();
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(
					"SELECT * FROM item WHERE itemcode = ?");
					
			//순서대로 ?를 채워준다
			pst.setString(1, _itemCode);
			
			ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String sitemcode = rs.getString("itemcode");
                String sitemname = rs.getString("itemname");
                int sprice = rs.getInt("price");
                String sbrand = rs.getString("brand");
                String ssellercode = rs.getString("sellercode");
                int sstock = rs.getInt("stock");
                int ssales = rs.getInt("sales");
                
                _item.setItemCode(sitemcode);
                _item.setItemName(sitemname);
                _item.setPrice(sprice);
                _item.setBrand(sbrand);
                _item.setSellerCode(ssellercode);
                _item.setStock(sstock);
                _item.setSales(ssales);
                
            }
            
            pst.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return _item;
}
	
	public static List<item> selectSellCount(String sellercode) {
		List<item> sellCountList = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            Statement st = conn.createStatement();
            
            ResultSet rs = st.executeQuery(
            		"select * from item where sellercode = '161101'"
            		);
            while(rs.next()) {
                item _item = new item();
                
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setSales(rs.getInt("numberofsales"));
                _item.setStock(rs.getInt("numberofstock"));
                
                sellCountList.add(_item);
            }
            
            st.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return sellCountList;   
	}
	
	public static List<item> selectBest3Selling(String startDate,String endDate) {
		//bestItemDuringTime.jsp
		System.out.println(startDate);
		System.out.println(endDate);
		String sql = "SELECT DISTINCT itemcode,itemname,brand,sum "
				+ "FROM item "
				+ "NATURAL JOIN(SELECT itemcode,sum(numberofpurchase) "
				+ "FROM sellhistory WHERE ? < purchasetime "
				+ "AND purchasetime < ? GROUP BY itemcode) tempTable "
				+ "ORDER BY sum desc";	
		List<item> best3Selling = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setTimestamp(1, Timestamp.valueOf(startDate.replace("T", " ")));
            pst.setTimestamp(2, Timestamp.valueOf(endDate.replace("T", " ")));
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setCounter(rs.getInt("sum"));
        
                
                best3Selling.add(_item);
            }
            
            pst.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return best3Selling;   
	}
	
	public static List<item> select20to30BestItem() {
		//bestItem20to30.jsp
		/*
		 * select itemcode,itemname,brand,sum(numberofsales)
from item
where itemcode IN 
((select itemcode
 from(select itemcode,sum(numberofpurchase) 
      FROM sellhistory 
      WHERE email IN (select email 
                      from client 
                      where age(birthday) >= '20year'::interval 
                      AND age(birthday) <= '29year'::interval) 
      group by itemcode
      ORDER BY sum DESC
      LIMIT 10)tempTable1
)
intersect
(select itemcode
 from(select itemcode,sum(numberofpurchase) 
      FROM sellhistory 
      WHERE email IN (select email 
                      from client 
                      where age(birthday) >= '30year'::interval 
                      AND age(birthday) <= '39year'::interval) 
      group by itemcode
      ORDER BY sum DESC
      LIMIT 10)tempTable2
))
group by itemcode,itemname,brand
		 */
		String sql = "select itemcode,itemname,brand,sum(numberofsales) from item where itemcode IN ((select itemcode from(select itemcode,sum(numberofpurchase) FROM sellhistory WHERE email IN (select email from client where age(birthday) >= '20year'::interval AND age(birthday) <= '29year'::interval) group by itemcode ORDER BY sum DESC LIMIT 10)tempTable1) intersect (select itemcode from(select itemcode,sum(numberofpurchase) FROM sellhistory WHERE email IN (select email from client where age(birthday) >= '30year'::interval AND age(birthday) <= '39year'::interval) group by itemcode ORDER BY sum DESC LIMIT 10)tempTable2)) group by itemcode,itemname,brand";
		List<item> best20to30 = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setCounter(rs.getInt("sum"));
        
                best20to30.add(_item);
            }
            
            pst.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return best20to30;   
	}
	
	public static List<item> selectBest10Item(String sellercode) {
		//bestItemExceptOneSeller.jsp
		String sql = "SELECT sellercode,itemcode,itemname,brand,numberofsales,numberofsales*price as income FROM item WHERE sellercode <> ? ORDER BY income DESC";
		List<item> best10Selling = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, sellercode);
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setSales(rs.getInt("numberofsales"));
                _item.setCounter(rs.getInt("income"));
                
                
                best10Selling.add(_item);
            }
            
            pst.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return best10Selling;   
	}
	
	public static List<item> selectBest10ItemExceptSeller(String sellercode) {
		//bestItemExceptOneSeller.jsp
		/*
select *
from (
(SELECT sellercode,itemcode,itemname,brand,price,numberofsales
FROM item 
ORDER BY numberofsales*price DESC 
limit 10)
except
(SELECT sellercode,itemcode,itemname,brand,price,numberofsales
FROM item
WHERE sellercode = '161102'
ORDER BY numberofsales*price DESC)
) tempTable
order by price*numberofsales DESC
		 */
		String sql = "select * from ( (SELECT sellercode,itemcode,itemname,brand,price,numberofsales FROM item ORDER BY numberofsales*price DESC limit 10) except (SELECT sellercode,itemcode,itemname,brand,price,numberofsales FROM item WHERE sellercode = ? ORDER BY numberofsales*price DESC)) tempTable order by price*numberofsales DESC";
		List<item> best10Selling = new ArrayList<>();
        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, sellercode);
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                item _item = new item();
                
                _item.setSellerCode(rs.getString("sellercode"));
                _item.setItemCode(rs.getString("itemcode"));
                _item.setItemName(rs.getString("itemname"));
                _item.setBrand(rs.getString("brand"));
                _item.setPrice(rs.getInt("price"));
                _item.setSales(rs.getInt("numberofsales"));
                
                
                best10Selling.add(_item);
            }
            
            pst.close();
            conn.close();
        } catch(Exception e) {
                e.printStackTrace();
        }
        return best10Selling;   
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		itemDAO dao = new itemDAO();
		//dao.selectAllitem();
		
	}

}
