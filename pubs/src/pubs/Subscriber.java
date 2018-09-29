package pubs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub{

	public String subId;
	
	public void setSubId(String subId){
		this.subId = subId;
	}
	
	@Override
	public void onMessage(String channel, String newsId){
		
		System.out.println(subId + " receive a news ( " + newsId + " ) --> " +getMsg(newsId));
		
		//Test for unsubscribing channels
		if(channel.equals("business")||channel.equals("arts")){
			this.unsubscribe(channel);
			System.out.println(subId + " unsubscribe the channel " +  channel);
		}
	}

	private String getMsg(String newsId){
		
		//Need to setup a new connection, if not, we cannot access to the data in redis!
		Jedis jedisTmp = Config.getJedis();
		String text = jedisTmp.get(newsId);
		Config.closeJedis(jedisTmp);
		return text.split("\\|")[0];
	}
}
