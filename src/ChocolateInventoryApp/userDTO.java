package ChocolateInventoryApp;

public class userDTO {
	private String id;
	private String pass;
	private String name;
	private int tel;
	
    public userDTO(String id, String pass, String name, int tel) {
        this.id = id;
        this.pass = pass;
        this.name = name;
        this.tel = tel;
    }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	
	
}
