package ligacao;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Users
 *
 */
@Entity

public class Users implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String user;
	private String password;
	private String nome;
	private String email;

	public Users() {
		super();
	}

	public Users(String nome, String user, String password, String email){
		this.setUser(user);
		this.setPassword(password);
		this.setNome(nome);
		this.setEmail(email);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
