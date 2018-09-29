# PubSub
使用list的数据操作可是lpush遇到问题，插入的内容会重复多次，未解决
使用hashmap的数据操作，存放的顺序与本身的顺序不一致，未解决
最终选用了string存储
了解一下hset和hget
可以实现多个用户的订阅，每有一个新客户，代表生成一个新线程
但是未能解决随机的取消订阅问题以及随机的同一个用户多次订阅问题。
对于一个用户多次订阅想法：
一个类subThread，里边会创建一个Subscriber，并且有一个自定义的subscribe函数，
每此类的一个实例调用subscribe函数，又会生成新线程，但保持是同一个subscriber
代码如下：
public class Subscriber extends JedisPubSub{

  	public static int num = 0;
  	public final String subId;
	
  	public Subscriber() {
		num ++;
		subId = "client" + num;
	}
	
	public void subscribe(Subscriber sub, String...channels){
		new Thread(new Runnable(){
			@Override
			public void run(){
        Jedis jedisSub = Config.getJedis();
				String tags = new String();
				tags = channels[0];
				for(int i=1; i<channels.length; i++){
					tags = channels[i] + " , " + tags ;
				}
				System.out.println(subId + " subscribe channels: " + tags);
				System.out.println("-----------------------------------------------");
				jedisSub.subscribe(sub, channels);	
			}
		}).start();
	}

}
