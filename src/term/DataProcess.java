package term;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

public class DataProcess {
	static ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String,String>>();
	static ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String,String>>();
	public static String userCSVfile = "C:\\Users\\skagh\\OneDrive\\문서\\2016년 가을 3학년 2학기\\데이터베이스 - 이기준\\miniTerm\\sample\\user.csv";
	public static String itemCSVfile = "C:\\Users\\skagh\\OneDrive\\문서\\2016년 가을 3학년 2학기\\데이터베이스 - 이기준\\miniTerm\\sample\\item.csv";

	public static void main(){
		//DataProcess D = new DataProcess();
	}
	
	public void start(){
		System.out.println("-----------------delete all tuple------------");
		deleteAll();
		System.out.println("-----------------userCSV------------");
		userCSV();
		System.out.println("-----------------itemCSV------------");
		itemCSV();
		insertClient();
		insertCoupon();
		insertSeller();
		insertItem();
		insertSellHistory();
		insertShoppingCart();
		userList.clear();
		itemList.clear();
	}
	public static void userCSV(){
			try {
				File csv = new File(userCSVfile);
				BufferedReader br = new BufferedReader(new FileReader(csv));
				String line = "";
				
				br.readLine();//가장 첫 줄. 즉, column이름 읽음
				while((line = br.readLine()) != null){//한줄씩 읽음
					String[] token = line.split(",", -1);
					HashMap<String, String> userMap = new HashMap<>();
					userMap.put("email", token[0]);
					userMap.put("password", token[1]);
					userMap.put("firstname", token[2]);
					userMap.put("lastname", token[3]);
					userMap.put("birth", token[4]);
					userMap.put("coupons", token[5]);
					userList.add(userMap);
				}
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(HashMap<String,String> map : userList){
				System.out.print(map.get("email") + ", ");
				System.out.print(map.get("password") + ", ");
				System.out.print(map.get("firstname") + ", ");
				System.out.print(map.get("lastname") + ", ");
				System.out.print(map.get("birth") + ", ");
				System.out.println(map.get("coupons"));
			}
	}
	public static void itemCSV(){
		try {
		File csv = new File(itemCSVfile);
			BufferedReader br = new BufferedReader(new FileReader(csv));
			String line = "";
			
			br.readLine();//가장 첫 줄. 즉, column이름 읽음
			while((line = br.readLine()) != null){//한줄씩 읽음
				String[] token = line.split(",", -1);
				HashMap<String, String> itemMap = new HashMap<>();
				itemMap.put("itemcode", token[0]);
				itemMap.put("itemname", token[1]);
				itemMap.put("brand", token[2]);
				itemMap.put("sellercode", token[3]);
				itemMap.put("sellername", token[4]);
				itemMap.put("price", token[5]);
				itemMap.put("numberofstock", token[6]);
				itemMap.put("numberofsales", token[7]);
				itemMap.put("cart", token[8]);
				itemMap.put("purchase", token[9]);
				itemList.add(itemMap);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(HashMap<String,String> map : itemList){
			System.out.print(map.get("itemcode") + ", ");
			System.out.print(map.get("itemname") + ", ");
			System.out.print(map.get("brand") + ", ");
			System.out.print(map.get("sellercode") + ", ");
			System.out.print(map.get("sellername") + ", ");
			System.out.print(map.get("price") + ", ");
			System.out.print(map.get("numberofstock") + ", ");
			System.out.print(map.get("numberofsales") + ", ");
			System.out.print(map.get("cart") + ", ");
			System.out.println(map.get("purchase"));
		}
	}
	
	public static void deleteAll(){
		
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			conn.prepareStatement("DELETE FROM client").executeUpdate();
			conn.prepareStatement("DELETE FROM coupon").executeUpdate();
			conn.prepareStatement("DELETE FROM item").executeUpdate();
			conn.prepareStatement("DELETE FROM seller").executeUpdate();
			conn.prepareStatement("DELETE FROM sellhistory").executeUpdate();
			conn.prepareStatement("DELETE FROM shoppingcart").executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static int insertClient(){
		System.out.println("--------------------Insert Client--------------------------");
		int[] result = {0,};
		String sql = "INSERT INTO client(email,pw,familyname,firstname,birthday,totalpurchase) VALUES(?,?,?,?,CAST(? AS DATE),?)";
				
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			for(HashMap<String,String> map : userList){
				pst.setString(1, map.get("email"));
				pst.setString(2, map.get("password"));
				pst.setString(3, map.get("firstname"));
				pst.setString(4, map.get("lastname"));
				pst.setString(5, map.get("birth"));
				int totalprice = 0;
				for(HashMap<String,String> itemMap : itemList){
					String purchase = itemMap.get("purchase");
					if(!purchase.equals("")){
						String[] token = purchase.split("\\|",-1);//a@gamil.com 3 0 2016-12-10 10:10:10
						for(String str : token){
							String[] each = str.split("\\s",-1);
							if(each[0].equals(map.get("email"))){//email, 개수, 할인율, 시간
								int count = Integer.parseInt(each[1]);
								double discount = Integer.parseInt(each[2]);
								totalprice += (count * Integer.parseInt(itemMap.get("price"))) * ((100-discount)/100);
							}
						}
					}
				}
				pst.setInt(6, totalprice);
				pst.addBatch();
			}
			
			
			result = pst.executeBatch();
			
			pst.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result[0];
	}


	public static int insertCoupon(){
		System.out.println("--------------------Insert Coupon--------------------------");
		int[] result = {0,};
		String sql = "INSERT INTO coupon(email,discount,numberofcoupon) VALUES(?,?,?)";
				
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			for(HashMap<String,String> map : userList){
				pst.setString(1, map.get("email"));
				int[] arr = new int[100];
				String coupons = map.get("coupons");
				if(!coupons.equals("")){
					String[] token = coupons.split("\\|",-1);//000000 VVIP-Coupon 30
					for(String str : token){
						String[] each = str.split("\\s",-1);//ID, 쿠폰이름, 할인율
						arr[Integer.parseInt(each[2])]++;
					}
				}
				for(int i = 0; i < 100; i++){
					if(arr[i] != 0){
						pst.setInt(2, i);
						pst.setInt(3, arr[i]);
						pst.addBatch();
					}
				}
				
			}
			
			result = pst.executeBatch();
			
			pst.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result[0];
	}

	public static int insertSeller(){
		System.out.println("--------------------Insert Seller--------------------------");
		int[] result = {0,};
		String sql = "INSERT INTO seller(sellercode,sellername,pw) SELECT ?,?,? WHERE NOT EXISTS (SELECT 1 FROM seller WHERE sellercode=? AND sellername=?)";
		
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			for(HashMap<String,String> map : itemList){
				pst.setString(1, map.get("sellercode"));
				pst.setString(2, map.get("sellername"));
				pst.setString(3, "1234");//원래 seller의 pw는 1234로 한다.
				pst.setString(4, map.get("sellercode"));
				pst.setString(5, map.get("sellername"));
				
				pst.addBatch();
			}
			
			result = pst.executeBatch();
			
			pst.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result[0];
	}
	public static int insertItem(){
		int[] result = {0,};
		String sql = "INSERT INTO item(itemcode,itemname,brand,sellercode,price,numberofstock,numberofsales) VALUES(?,?,?,?,?,?,?)";
		
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			for(HashMap<String,String> map : itemList){
				pst.setString(1, map.get("itemcode"));
				pst.setString(2, map.get("itemname"));
				pst.setString(3, map.get("brand"));
				pst.setString(4, map.get("sellercode"));
				pst.setInt(5, Integer.parseInt(map.get("price")));
				pst.setInt(6, Integer.parseInt(map.get("numberofstock")));
				pst.setInt(7, Integer.parseInt(map.get("numberofsales")));
				pst.addBatch();
			}
			
			result = pst.executeBatch();
			
			pst.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result[0];
	}

	public static int insertSellHistory(){
		System.out.println("--------------------Insert Sellhistory--------------------------");
		int[] result = {0,};
		String sql = "INSERT INTO sellhistory(email,itemcode,sellercode,numberofpurchase,purchasetime,coupon) VALUES(?,?,?,?,?,?)";
		
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			for(HashMap<String,String> map : itemList){
				String purchase = map.get("purchase");
				if(!purchase.equals("")){
					String[] token = purchase.split("\\|",-1);
					for(String str : token){
						String[] each = str.split("\\s",-1);//email, 개수, 쿠폰, 날짜, 시간
						pst.setString(1, each[0]);
						pst.setString(2, map.get("itemcode"));
						pst.setString(3, map.get("sellercode"));
						pst.setInt(4, Integer.parseInt(each[1]));
						
						Timestamp ts = Timestamp.valueOf(each[3]+" "+each[4]);
						pst.setTimestamp(5, ts);
						pst.setString(6, each[2]);
						pst.addBatch();
					}
				}
				
			}
			
			result = pst.executeBatch();
			
			pst.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result[0];
	}
	public static int insertShoppingCart(){
		System.out.println("--------------------Insert shoppingcart--------------------------");
		int[] result = {0,};
		String sql = "INSERT INTO shoppingcart(email,itemcode,sellercode,numberofcart) VALUES(?,?,?,?)";
		
		Connection conn;
		try {
			conn = ConnectionProvider.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			for(HashMap<String,String> map : itemList){
				String cart = map.get("cart");
				if(!cart.equals("")){
					String[] token = cart.split("\\|",-1);
					for(String str : token){
						String[] each = str.split("\\s",-1);
						System.out.println(each[0]);
						System.out.println(each[1]);
						pst.setString(1, each[0]);
						pst.setString(2, map.get("itemcode"));
						pst.setString(3, map.get("sellercode"));
						pst.setInt(4, Integer.parseInt(each[1]));
						pst.addBatch();
					}
				}
				
			}
			
			result = pst.executeBatch();
			
			pst.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result[0];
	}
}