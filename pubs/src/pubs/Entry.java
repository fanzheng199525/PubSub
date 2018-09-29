package pubs;

public class Entry {

	public static void main(String[] args) {
		String[] channels1 = {"arts", "technology"};
		String[] channels2 = {"business"};
		new SubThread(channels1).start();
		new SubThread(channels2).start();
		
		Publisher pub = new Publisher();
		pub.start();	
	}

}
