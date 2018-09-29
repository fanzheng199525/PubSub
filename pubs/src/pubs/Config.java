package pubs;

import java.util.HashMap;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//127.0.0.1:6379

//The details of connection needs to be improved!
public class Config {
	
	private static String ip="127.0.0.1";
	private static int port=6379;
	private static HashMap<String,JedisPool> maps = new HashMap<String,JedisPool>();
	
	private static JedisPool getPool(String ip, int port){
		String key = ip+":"+port;
		JedisPool pool = null;
		if(!maps.containsKey(key)){
			pool = new JedisPool(ip, port);
			maps.put(key, pool);
		}
		else{
			pool = maps.get(key);
		}
		return pool;
	}
	
	public static Jedis getJedis(){
		return getPool(ip,port).getResource();
	}
	
	public static void closeJedis(Jedis jedis){
		if(jedis != null){
			jedis.close();
		}
	}

}
