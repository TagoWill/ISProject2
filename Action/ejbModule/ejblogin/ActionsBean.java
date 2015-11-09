package ejblogin;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.Music;
import data.Playlist;
import ligacao.Users;

/**
 * Session Bean implementation class ActionsBean
 */
@Stateless
@LocalBean
public class ActionsBean implements ActionsBeanRemote {

	@PersistenceContext(name = "Users")
	EntityManager Cursor;
	/**
	 * Default constructor. 
	 */
	public ActionsBean() {

	}

	@Override
	public boolean editProfile(String userid, String name, String mail, String password) {
		// Editar perfil do Utilizador
		try{
			String sql = "UPDATE Users u SET u.nome= :a, u.email= :c, u.password= :d WHERE u.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", name);
			queue.setParameter("c", mail);
			queue.setParameter("d", password);
			queue.setParameter("b", Integer.parseInt(userid));
			queue.executeUpdate();
			return true;
		}catch(Exception e){
			System.out.println("Erro editProfile: "+e);
			return false;
		}

	}

	@Override
	public boolean deleteProfile(Users userid) {
		// Apagar Perfil de Utilizador

		//TODO NAO ESTA COMPLETO!!!!
		String sql;
		javax.persistence.Query queue;
		try{
			sql = "DELETE FROM Playlist p WHERE p.user = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("b", userid);
			queue.executeUpdate();
			
			sql = "DELETE FROM Users u WHERE u.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("b", userid.getId());
			queue.executeUpdate();
			
			return true;
		}catch(Exception e){
			System.out.println("Erro deleteProfile: "+e);
			return false;
		}
	}

	@Override
	public boolean addPlaylist(Users userid, String playlist_name) {
		// As a	user, I	want to	create new playlists and assign	them a name
		try{
			String sql = "FROM Playlist p WHERE p.playlist_name = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", playlist_name);
			List entities = queue.getResultList();
			if(entities.size()>0){
				return false;
			}else{
				Playlist novaplaylist = new Playlist(userid, playlist_name, null);
				Cursor.persist(novaplaylist);
				return true;
			}
		}catch(Exception e){
			System.out.println("Erro addPlaylist: "+e);
			return false;
		}
	}

	@Override
	public void editPlaylist(String playlistid, String playlist_name) {
		// As a	user, I	want to	edit the name of the playlists.

	}

	@Override
	public boolean deletePlaylist(String playlistid) {
		// As a	user, I	want to	be able	to delete a	playlist. Deleting a playlist should not delete	the	associated music.
		try{
			String sql = "DELETE FROM Playlist p WHERE p.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(playlistid));
			queue.executeUpdate();
			return true;
		}catch(Exception e){
			System.out.println("Erro deleteProfile: "+e);
			return false;
		}
	}

	@Override
	public List<Playlist> listMyPlaylists(Users userid, String order) {
		// As a user, I	want to	list my	playlists in ascending or descending order.
		try{
			String sql;
			if(order.equals("ASC"))
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name ASC";
			else
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name DESC";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", userid);
			//queue.setParameter("b", order);
			@SuppressWarnings("unchecked")
			List<Playlist> list = queue.getResultList();
			return list;
		}catch(Exception e){
			System.out.println("Erro listMyPlaylist: "+e);
			return null;
		}
	}

	@Override
	public void listMyMusicFiles(String userid, String playlist_name) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.

	}

	@Override
	public void addMusicFileToPlaylist(String userid, String playlist_name) {
		// As a	user I want	to add music files to a playlist.

	}

	@Override
	public void deleteMusicFileFromPlaylist(String userid, String playlist_name) {
		// As a	user I want	to delete music files from a playlist.

	}

	@Override
	public void addMusicFile(String userid, String title, String artist, String album, String year, String path) {
		// As a	user, I	want to	add	new	music to the application, identifying the title, artist, album, year and path to the file to upload	to the server.

	}

	@Override
	public void editMusicFile(String userid, String title, String artist, String album, String year, String path) {
		// As a	user, I	want to	edit the data of music I added to the application.

	}

	@Override
	public void detachFromMusic(String userid, String title) {
		// As a	user, I	want to	detach myself from music I uploaded. This should neither delete	music from the server, nor from	playlists.

	}

	@Override
	public void listAllMusic(String userid) {
		// As a	user, I	want to	list all the music registered in the application by	all	other users.

	}

	@Override
	public void searchAndListMusic(String userid, String title, String artist) {
		// As a	user, I	want to	list all music registered in the application that satisfies some search	criteria over the title	and/or artist.

	}

}
