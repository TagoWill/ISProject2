package ejblogin;

import java.util.List;

import javax.ejb.Remote;

import data.Music;
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
	public Playlist getPlaylistName(String playlistid);
	public boolean addPlaylist(String userid, String playlist_name);
	public boolean editPlaylist(String playlistid, String playlist_name);
	public boolean deletePlaylist(String playlistid, boolean iscommit);
	public List<Playlist> listMyPlaylists(String userid, String order);
	public List<Music> listMyMusicFiles(String userid, String order);
	public List<Music> listMyMusicFilesByPlaylist(String userid, String playlistid);
	public boolean addMusicFileToPlaylist(String musicid, String playlistid);
	public boolean deleteMusicFileFromPlaylist(String music_id, String playlist_id);
	public boolean addMusicFile(String userid, String title, String artist, String album, String year, String path);
	public boolean editMusicFile(String musicid, String title, String artist, String album, String year);
	public Music getInfoMusicFile(String musicid);
	public boolean detachFromMusic(String musicids, boolean iscommit);
	public List<Music> listAllMusic();
	public List<Music> searchAndListMusic(int tipo, String title, String artist);
}
