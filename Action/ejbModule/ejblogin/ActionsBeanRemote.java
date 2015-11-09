package ejblogin;

import java.util.List;

import javax.ejb.Remote;

import data.Playlist;
import ligacao.Users;

@Remote
public interface ActionsBeanRemote {
	public boolean registerUser(String nome, String user, String password, String email);
	public boolean verifyRegister(String email);
	public Users loginUser(String email, String password);
	public Users devolverPorId(String id);
	
	public boolean editProfile(String userid, String name, String mail, String password);
	public boolean deleteProfile(String userid);
	public boolean addPlaylist(String userid, String playlist_name);
	public void editPlaylist(String playlistid, String playlist_name);
	public boolean deletePlaylistCommit(String playlistid);
	public boolean deletePlaylist(String playlistid);
	public List<Playlist> listMyPlaylists(String userid, String order);
	public void listMyMusicFiles(String userid, String playlist_name);
	public void addMusicFileToPlaylist(String userid, String playlist_name);
	public void deleteMusicFileFromPlaylist(String userid, String playlist_name);
	public void addMusicFile(String userid, String title, String artist, String album, String year, String path);
	public void editMusicFile(String userid, String title, String artist, String album, String year, String path);
	public void detachFromMusic(String userid, String title);
	public void listAllMusic(String userid);
	public void searchAndListMusic(String userid, String title, String artist);
}
