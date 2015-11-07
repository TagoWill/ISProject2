package data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import data.Music;
import ligacao.Users;

/**
 * Entity implementation class for Entity: Playlist
 *
 */
@Entity

public class Playlist implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Users user;
	private String playlist_name;
	@OneToMany(mappedBy = "playlist")
	private List<Music> song;
	
	public Playlist() {
		super();
	}
	
	public Playlist(Users user, String playlist_name, List<Music> song){
		this.setUser(user);
		this.setPlaylistName(playlist_name);
		this.setPlaylistSongs(song);
	}
   
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getPlaylistName() {
		return playlist_name;
	}

	public void setPlaylistName(String playlist_name) {
		this.playlist_name = playlist_name;
	}
	
	public List<Music> getPlaylistSongs() {
		return song;
	}
	
	public void setPlaylistSongs(List<Music> song) {
		this.song = song;
	}
}
