package eu.ueb.acem.web.viewbeans;

public class CounterBean {
	
	private int count = 1;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void increment() {
		count++;
	}
}