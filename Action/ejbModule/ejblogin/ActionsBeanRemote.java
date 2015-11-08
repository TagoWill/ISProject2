package ejblogin;

import javax.ejb.Remote;

@Remote
public interface ActionsBeanRemote {
	public boolean editProfile(String userid, String name, String mail, String password);
	public boolean deleteProfile(String userid);
	public void addPlaylist(String userid, String playlist_name);
	public void editPlaylist(String userid, String playlist_name);
	public void deletePlaylist(String userid, String playlist_name);
	public void listMyPlaylists(String userid, String order);
	public void listMyMusicFiles(String userid, String playlist_name);
	public void addMusicFileToPlaylist(String userid, String playlist_name);
	public void deleteMusicFileFromPlaylist(String userid, String playlist_name);
	public void addMusicFile(String userid, String title, String artist, String album, String year, String path);
	public void editMusicFile(String userid, String title, String artist, String album, String year, String path);
	public void detachFromMusic(String userid, String title);
	public void listAllMusic(String userid);
	public void searchAndListMusic(String userid, String title, String artist);
}
