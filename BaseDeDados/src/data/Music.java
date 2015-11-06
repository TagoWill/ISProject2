package data;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Music
 *
 */
@Entity

public class Music implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String user;
	private String title;
	private String artist;
	private String album;
	private String year;
	private String path;
	
	public Music() {
		super();
	}
	
	public Music(String user, String title, String artist, String album, String year, String path){
		this.setUser(user);
		this.setTitle(title);
		this.setArtist(artist);
		this.setAlbum(album);
		this.setYear(year);
		this.setPath(path);
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
   
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
