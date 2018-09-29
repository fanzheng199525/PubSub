package pubs;

import redis.clients.jedis.Jedis;

public class Publisher {
	public static int NEWS_NB = 6;
	private static Jedis jedisPub = null;
	
	public Publisher(){
		jedisPub = Config.getJedis();
	}
	
	private void init(){
		StringBuilder description1 = new StringBuilder();
		description1.append("Pollution threatens the future of killer whales");
		description1.append("|science");
		description1.append("|environment");
		jedisPub.set("news1", description1.toString());
		
		StringBuilder description2 = new StringBuilder();
		description2.append("Tesla: Shares fall after regulators launch Musk lawsuit");
		description2.append("|business");
		jedisPub.set("news2", description2.toString());

		
		StringBuilder description3 = new StringBuilder();
		description3.append("Elton John biopic: Rocketman is revealed");
		description3.append("|entertainment");
		description3.append("|arts");
		jedisPub.set("news3", description3.toString());
		
		StringBuilder description4 = new StringBuilder();
		description4.append("Modern Family actress Sarah Hyland and Cougar Town's Busy Philipps have revealed they were sexually assaulted in high school");
		description4.append("|entertainment");
		description4.append("|arts");
		jedisPub.set("news4", description4.toString());

		StringBuilder description5 = new StringBuilder();
		description5.append("Facebook has said ¡°almost 50 million¡± of its users were left exposed by a security flaw");
		description5.append("|technology");
		jedisPub.set("news5", description5.toString());

		StringBuilder description6 = new StringBuilder();
		description6.append("Ryanair cancels 250 flights as strike action hits tens of thousands");
		description6.append("|business");
		jedisPub.set("news6", description6.toString());
		
	}
	
	public void start(){
		this.init();
		
		for(int i = 1; i <= NEWS_NB; i++){
			String newsId = "news" + i;
			String val = jedisPub.get(newsId);
			String[] vals = null;
			
			if(val != null){
				vals = val.split("\\|");
				for(int j=1; j<vals.length; j++){
					System.out.println("publish the news (" + newsId + "), tag: "+ vals[j] );
					jedisPub.publish(vals[j], newsId);
					
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
			System.out.println("--------------------------------------");
		}
		Config.closeJedis(jedisPub);
	}
}
