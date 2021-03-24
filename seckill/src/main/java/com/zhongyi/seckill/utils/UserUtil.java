package com.zhongyi.seckill.utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhongyi.seckill.entity.SkUser;

public class UserUtil {

    
    public static void creatUser(int count) throws Exception{
        List<SkUser> list = new ArrayList<>();
        for(int i = 0;i < count;i++){
            SkUser user = new SkUser();
            user.setId(13000000000L+i);
            user.setLoginCount(1);
            user.setSalt("1a2b3c4d");
            user.setNickname("user" + i);
            user.setPassword(MD5Util.inputPassToDBPass("123456", "1a2b3c4d"));
            user.setRegisterDate(LocalDateTime.now());
            list.add(user);
        }
        Connection con = getConn();
        String sql = "insert into sk_user(id,nickname,password,salt,login_count) values(?,?,?,?,?)";
        try(PreparedStatement state =  con.prepareStatement(sql)){
            for(SkUser user : list){
                state.setLong(1, user.getId());
                state.setString(2, user.getNickname());
                state.setString(3, user.getPassword());
                state.setString(4, user.getSalt());
                state.setInt(5, user.getLoginCount());
                state.addBatch();
            }
            state.executeBatch();
            state.clearParameters();
        }
        //登录,生成令牌

        String urlString = "http://localhost:8080/login/doLogin";
		File file = new File("D:/tokens.txt");
		if(file.exists()) {
			file.delete();
		}
		try(RandomAccessFile raf = new RandomAccessFile(file, "rw")){
		file.createNewFile();
		raf.seek(0);
		for(int i=0;i<list.size();i++) {
			SkUser user = list.get(i);
			URL url = new URL(urlString);
			HttpURLConnection co = (HttpURLConnection)url.openConnection();
			co.setRequestMethod("POST");
			co.setDoOutput(true);
			OutputStream out = co.getOutputStream();
			String params = "mobile="+user.getId()+"&password="+MD5Util.inputPassToFormPass("123456");
			out.write(params.getBytes());
			out.flush();
			InputStream inputStream = co.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte buff[] = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buff)) >= 0) {
				bout.write(buff, 0 ,len);
			}
			inputStream.close();
			bout.close();
			String response = new String(bout.toByteArray());
			JSONObject jo = JSON.parseObject(response);
			String token = jo.getString("data");
			System.out.println("create token : " + user.getId());
			
			String row = user.getId()+","+token;
			raf.seek(raf.length());
			raf.write(row.getBytes());
			raf.write("\r\n".getBytes());
			System.out.println("write to file : " + user.getId());
		}
    }

        
        
    }
    public static Connection getConn() throws Exception{
        String url = "jdbc:mysql://localhost:3306/miaosha?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String username = "root";
        String password = "zy864134234";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }    
     public static void main(String[] args) throws Exception {
        
        creatUser(5000);

    }
}
