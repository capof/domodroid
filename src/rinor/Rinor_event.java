package rinor;

public class Rinor_event {
	public int ticket_id = 0;
	public int item = 0;
	public int device_id;
	public String key;
	public String Value;
	
	public Rinor_event ( int ticket, int item, int dev_id, String key, String value) {
		this.ticket_id = ticket;
		this.item = item;
		this.device_id = dev_id;
		this.key = key;
		this.Value = value;
	}
	
}
