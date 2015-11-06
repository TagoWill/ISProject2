package ejblogin;

import javax.ejb.Remote;

@Remote
public interface ActionsBeanRemote {
	public void addPlaylist(String user, String playlist_name);
	public void editPlaylist(String user, String playlist_name);
	public void deletePlaylist(String user, String playlist_name);
	public void listMyPlaylists(String user, String order);
	public void listMyMusicFiles(String user, String playlist_name);
	public void addMusicFileToPlaylist(String user, String playlist_name);
	public void deleteMusicFileFromPlaylist(String user, String playlist_name);
	public void addMusicFile(String user, String title, String artist, String album, String year, String path);
	public void editMusicFile(String user, String title, String artist, String album, String year, String path);
	public void detachFromMusic(String user, String title);
	public void listAllMusic(String user);
	public void searchAndListMusic(String user, String title, String artist);
	
}
