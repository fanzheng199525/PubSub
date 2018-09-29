package pubs;

import redis.clients.jedis.Jedis;

public class SubThread extends Thread {

	public static int num = 0;
	public final String subId;
	String channels[] = null;
	Subscriber sub = null;

	public SubThread(String[] channels) {
		super();
		num ++;
		subId = "client" + num;
		this.channels = channels;
	}

	@Override 
	public void run(){
		Jedis jedis = Config.getJedis();
		sub = new Subscriber();
		sub.setSubId(subId);
		String tags = new String();
		tags = channels[0];

		for(int i=1; i<channels.length; i++){
			tags = channels[i] + " , " + tags ;
		}
		
		System.out.println(subId + " subscribe channels: " + tags);
		System.out.println("-----------------------------------------------");
		jedis.subscribe(sub, channels);	
	}
	

}
