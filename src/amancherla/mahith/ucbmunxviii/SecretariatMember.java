package amancherla.mahith.ucbmunxviii;

public class SecretariatMember {
	String name;
	String position;
	String number;
	String email;
	
	public SecretariatMember(String name, String position, String email) {
		this(name,position,email,null);
	}

	public SecretariatMember(String name, String position, String email, String number) {
		this.name = name;
		this.position = position;
		this.email = email;
		this.number = number;
	}
}
