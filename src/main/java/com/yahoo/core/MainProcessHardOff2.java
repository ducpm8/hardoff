//package com.yahoo.core;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Properties;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import com.yahoo.core.RequestHandler;
//import com.yahoo.entity.HardOffInfo;
//import com.yahoo.util.SendEmail;
//
//public class MainProcessHardOff2 {
//	private volatile static Map productList = new HashMap<String, HardOffInfo>();
//	
//	public static void main(String[] args) {
//		ScheduledExecutorService es1 = Executors.newScheduledThreadPool(10);
//		ScheduledExecutorService es2 = Executors.newScheduledThreadPool(10);
//		ScheduledExecutorService es3 = Executors.newScheduledThreadPool(10);
//		ScheduledExecutorService es4 = Executors.newScheduledThreadPool(10);
//		ScheduledExecutorService es5 = Executors.newScheduledThreadPool(10);
//		ScheduledExecutorService es6 = Executors.newScheduledThreadPool(10);
//		
//		//・パソコン
//		es1.scheduleAtFixedRate(new HardOffThread("00010002"), 0, 1, TimeUnit.SECONDS);
//		//・家電品
//		es2.scheduleAtFixedRate(new HardOffThread("00010008"), 0, 1, TimeUnit.SECONDS);
//		//・オーディオ・デジタル家電
//		es3.scheduleAtFixedRate(new HardOffThread("00010001"), 0, 1, TimeUnit.SECONDS);
//		
//		es4.scheduleAtFixedRate(new HardOffThread("1"), 0, 1, TimeUnit.SECONDS);
//		
//		es5.scheduleAtFixedRate(new HardOffThread("2"), 0, 10, TimeUnit.HOURS);
//		
//		//es.shutdown();
//	}
//	
//	public void gatherHardOff(String categoryNo) throws IOException, ClassNotFoundException, SQLException {
//		System.out.println("Start gatherHardOff " + categoryNo);
//		System.out.println("Time " + new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime()));
//		
//		StringBuffer builder = RequestHandler.response("https://netmall.hardoff.co.jp/cate/" + categoryNo + "/?p=1&s=1&pl=54");
//	    
//		Document doc = Jsoup.parse(builder.toString());
//		//Element content = doc.getElementById("itemBoxContainer");
//		Elements itemDivs = doc.getElementsByClass("p-goods__item");
//		
//		List<HardOffInfo> listProduct = new ArrayList<HardOffInfo>();
//		HardOffInfo product = new HardOffInfo();
//		
//		for (Element itemDiv : itemDivs) {
//			try {
//				product = new HardOffInfo();
//				
//				String brandValue = "";
//				String category = "";
//				String productName = "";
//				String productRank = "";
//				String nameCombine = "";
//				String productURL = "";
//				String productPrice = "";
//				String searchKey = "";
//				ArrayList<String> newProductFlagText = new ArrayList<String>();
//				
//				Elements newProducts = itemDiv.select(".c-status__item--new");
//				
//				if (newProducts.size() < 1) {
//					continue;
//				}
//				
//				Elements soldoutProducts = itemDiv.select(".c-status__item--soldout");
//				
//				if (soldoutProducts.size() > 0) {
//					continue;
//				}
//				
//				Elements prices = itemDiv.select(".p-goods__price");
//				for (Element price : prices) {
//					productPrice = price.text();
//					productPrice = productPrice.replaceAll(" ", "");
//				}
//				
//				Elements ranks = itemDiv.select(".p-goods__rank");
//				for (Element rank : ranks) {
//					productRank = rank.text();
//				}
//				
//				String tmp = productRank.substring(productRank.length()-1);
//				
//				if (productRank.isEmpty() 
//						|| (!tmp.equals("N")
//						&& !tmp.equals("S")
//						&& !tmp.equals("A"))) {
//					continue;
//				}
//				
//				//URL
//				Element a = itemDiv.select(".p-goods__link").first();
//				productURL = a.attr("href");
//				
//				//Full URL likes https://netmall.hardoff.co.jp/product/836815/
//				//Get the product ID as unique key
//				String tmpIdentify = productURL.replace("https://netmall.hardoff.co.jp/product", "");
//				
//				if (productList.containsKey(tmpIdentify)) continue;
//				
//				product.setIdentify(tmpIdentify);
//				
//				//productURL = "http://netmall.hardoff.co.jp" + productURL;
//				
//				Elements brands = itemDiv.getElementsByClass("p-goods__brand");
//				for (Element brand : brands) {
//					brandValue = brand.text();
//				}
//				
//				Elements cates = itemDiv.getElementsByClass("p-goods__nameClamp");
//				for (Element cate : cates) {
//					category = cate.text();
//				}
//				
//				Elements names = itemDiv.getElementsByClass("p-goods__type");
//				for (Element name : names) {
//					productName = name.text();
//				}
//				
////				Elements names = itemDiv.getElementsByClass("itemBox_name");
////				for (Element name : names) {
////					productName = name.text();
////					searchKey = name.getElementsByTag("span").text();
////				}
//				
//				nameCombine = "[" + brandValue + "][" +  category + "][" +  productName + "]";
//				
////				product.setSearchKey(searchKey);
//				product.setNameCombine(nameCombine);
//				product.setProductRank(productRank);
//				product.setProductURL(productURL);
//				product.setProductPrice(productPrice);
//				product.setProcessFlag(0);
//				
//				if (!productList.containsKey(tmpIdentify)) {
//		    		productList.put(tmpIdentify, product);
//		    	}
//				
//			} catch (Exception ex) {
//				System.out.println(categoryNo + " Exception inside loop product " + ex.getMessage());
//				continue;
//			}
//		}
//		
//		//System.out.println(categoryNo + " Scratch Found " + listProduct.size());
//	}
//	
//	private static String getQuery(HashMap<String, String> params) throws UnsupportedEncodingException
//	{
//	    StringBuilder result = new StringBuilder();
//	    boolean first = true;
//
//	    for (String key : params.keySet())
//	    {
//	        if (first)
//	            first = false;
//	        else
//	            result.append("&");
//
//	        result.append(URLEncoder.encode(key, "UTF-8"));
//	        result.append("=");
//	        result.append(URLEncoder.encode(params.get(key), "UTF-8"));
//	    }
//
//	    return result.toString();
//	}
//	
//	public void emailProcess() {
//        try {
//        	System.out.println("Email process. Size Total " + productList.size());
//        	
//        	//Email
//			String mailBody = "";
//			int count = 0;
//        	
//        	for (Object key : productList.keySet()) {
//        		//Each email thread process 5 products for maximum
//        		if (count > 5) break;
//        		
//        		HardOffInfo notifyProduct = (HardOffInfo) productList.get(key);
//        		
//        		// 0:New 1:Processing 2:Processed
//        		if (notifyProduct.getProcessFlag() < 1) {
//        			((HardOffInfo)productList.get(key)).setProcessFlag(1);
//        		} else {
//        			//System.out.println("Exist in pool so continue");
//        			continue;
//        		}
//        		
//				mailBody = mailBody + "==================================================" + "\r\n";
//				mailBody = mailBody + notifyProduct.getNameCombine() + "\r\n";
//				mailBody = mailBody + notifyProduct.getProductRank() + "\r\n";
//				mailBody = mailBody + "価格: " + notifyProduct.getProductPrice() + "\r\n";
//				mailBody = mailBody + "リンク: " + notifyProduct.getProductURL() + "\r\n";
//				
//				((HardOffInfo)productList.get(key)).setProcessFlag(2);
//				//System.out.println("Set process flag = 2 " + key);
//				
//				count++;
//					
//        	}
//        	
//        	if (!mailBody.isEmpty()) {
//				SendEmail.sendMail("Hardoff", mailBody);
//			}
//        } catch (Exception ioe) {
//        	
//        }
//	}
//	
//	public void poolClear() {
//		System.out.println("Pool clear . Total size " + productList.size());
//
//        try {
//        	
//        	for(Iterator<Map.Entry<String,HardOffInfo>> it = productList.entrySet().iterator(); it.hasNext();){
//        	     Map.Entry<String, HardOffInfo> entry = it.next();
//        	     if (entry.getValue().getProcessFlag() > 1) {
//        	    	 //System.out.println("remove key " + entry.getKey());
//        	          it.remove();
//        	     } else {
//         			//System.out.println("Do not remove key " + entry.getKey());
//         		}
//        	 }
//        } catch (Exception ioe) {
//        	System.out.println("Exception in poolClear(). " + ioe.getMessage());
//        }
//	}
//}
//
//class HardOffThread implements Runnable {
//    String categoryNo;
//
//    HardOffThread(String n) {
//    	categoryNo = n;
//    	
//    	ExecutorService pool = Executors.newCachedThreadPool();
//    	
//    	pool.execute(this);
//    	
//        //new Thread(this);
//    }
//
//    public void run() {
//        try {
//        	
//        	MainProcessHardOff2 mp = new MainProcessHardOff2();
//        	
//        	if (categoryNo.equals("1")) {
//        		mp.emailProcess();
//        	} else if (categoryNo.equals("2")) {
//        		mp.poolClear();
//        	} else {
//        		mp.gatherHardOff(categoryNo);
//        	}
//            
//        } catch (Exception ioe) {
//        	
//        }
//    }
//}
//
//
//
//
