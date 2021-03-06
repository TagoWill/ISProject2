package ligacao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import data.Music;
import data.Playlist;

/**
 * Entity implementation class for Entity: Users
 *
 */
@Entity

//@SequenceGenerator(name="seq", initialValue=2, allocationSize=1000)
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	//@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private int id;
	private String username;
	private String password;
	private String nome;
	private String email;
	@OneToMany (mappedBy="user")
	private List<Music> song;
	@OneToMany (mappedBy="user")
	private List<Playlist> playlist;


	public Users() {
		super();
	}

	public Users(String nome, String username, String password, String email){
		this.setUser(username);
		this.setPassword(password);
		this.setNome(nome);
		this.setEmail(email);
	}

	public String getUser() {
		return username;
	}

	public void setUser(String username) {
		this.username = username;
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
	
	public List<Playlist> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(List<Playlist> playlist) {
		this.playlist = playlist;
	}
	
	public List<Music> getSong() {
		return song;
	}

	public void setSong(List<Music> song) {
		this.song = song;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
