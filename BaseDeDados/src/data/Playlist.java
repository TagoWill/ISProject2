package data;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Playlist
 *
 */
@Entity

public class Playlist implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String user;
	private String playlist_name;
	
	public Playlist() {
		super();
	}
	
	public Playlist(String user, String playlist_name){
		this.setUser(user);
		this.setPlaylistName(playlist_name);
	}
   
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPlaylistName() {
		return playlist_name;
	}

	public void setPlaylistName(String playlist_name) {
		this.playlist_name = playlist_name;
	}
	
}
