package ejblogin;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import data.Music;
import data.Playlist;
import ligacao.Users;

/**
 * Session Bean implementation class ActionsBean
 */
@Stateless
@TransactionManagement(value=TransactionManagementType.BEAN)
public class ActionsBean implements ActionsBeanRemote {

	@PersistenceContext(name = "Users")
	EntityManager Cursor;

	@Resource
	private UserTransaction userTransaction;

	/**
	 * Default constructor. 
	 */
	public ActionsBean() {

	}

	@Override
	public boolean registerUser(String nome, String user, String password, String email){

		try{
			userTransaction.begin();
			Users novaconta = new Users(nome, user, password, email);
			Cursor.persist(novaconta);
			userTransaction.commit();
			return true;
		}catch(Exception e){
			//System.out.println("ERROR: "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			return false;
		}

	}

	@Override
	public boolean verifyRegister(String email){
		try{
			javax.persistence.Query q = Cursor.createQuery("from Users u where u.email = :t");
			q.setParameter("t", email);

			@SuppressWarnings("all")
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());

			if(conta == null){
				return true;
			}else{
				return false;
			}

		}catch(Exception e){
			//return null;
			return true;
		}
	}

	@Override
	public Users loginUser(String email, String password){
		try{
			javax.persistence.Query q = Cursor.createQuery("from Users u where u.email = :t"
					+ " and password = :p");
			q.setParameter("t", email);
			q.setParameter("p", password);

			@SuppressWarnings("all")
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			return conta;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Users devolverPorId(String id){
		javax.persistence.Query q = Cursor.createQuery("from Users u where u.id = :t");
		q.setParameter("t", Integer.parseInt(id));

		try{
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			return conta;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean editProfile(String userid, String name, String mail, String password) {
		// Editar perfil do Utilizador
		try{
			userTransaction.begin();
			String sql = "UPDATE Users u SET u.nome= :a, u.email= :c, u.password= :d WHERE u.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", name);
			queue.setParameter("c", mail);
			queue.setParameter("d", password);
			queue.setParameter("b", Integer.parseInt(userid));
			queue.executeUpdate();
			userTransaction.commit();
			return true;
		}catch(Exception e){
			System.out.println("Erro editProfile: "+e);

			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}

			return false;
		}
	}

	@Override
	public boolean deleteProfile(String userid) {
		// Apagar Perfil de Utilizador

		String sql;
		javax.persistence.Query queue;


		try{
			userTransaction.begin();
			Users conta = devolverPorId(userid);

			for(Playlist list : conta.getPlaylist()){
				deletePlaylist(Integer.toString(list.getId()), false);
			}

			for(Music list : conta.getSong()){
				detachFromMusic(Integer.toString(list.getId()), false);
			}

			sql = "DELETE FROM Users u WHERE u.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("b", conta.getId());
			queue.executeUpdate();
			userTransaction.commit();
			return true;
		}catch(Exception e){
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			System.out.println("Erro deleteProfile: "+e);
			return false;
		}
	}

	@Override
	public Playlist getPlaylistName(String playlistid) {
		javax.persistence.Query q = Cursor.createQuery("FROM Playlist p WHERE p.id = :i");
		q.setParameter("i", Integer.parseInt(playlistid));
		try{
			Playlist conta = (Playlist) q.getSingleResult();
			return conta;
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean addPlaylist(String userid, String playlist_name) {
		// As a	user, I	want to	create new playlists and assign	them a name
		try{
			Users conta = devolverPorId(userid);

			String sql = "FROM Playlist p WHERE p.playlist_name = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", playlist_name);
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				return false;
			}else{
				userTransaction.begin();
				Playlist novaplaylist = new Playlist(conta, playlist_name, null);
				Cursor.persist(novaplaylist);
				userTransaction.commit();
				return true;
			}
		}catch(Exception e){
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			System.out.println("Erro addPlaylist: "+e);
			return false;
		}
	}

	@Override
	public boolean editPlaylist(String playlistid, String playlist_name) {
		// As a	user, I	want to	edit the name of the playlists.
		try{
			userTransaction.begin();
			String sql = "UPDATE Playlist p SET p.playlist_name= :a WHERE p.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", playlist_name);
			queue.setParameter("b", Integer.parseInt(playlistid));
			queue.executeUpdate();
			userTransaction.commit();
			return true;
		}catch(Exception e){
			System.out.println("Erro editPlaylist: "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public boolean deletePlaylist(String playlistid, boolean iscommit) {
		// As a	user, I	want to	be able	to delete a	playlist. Deleting a playlist should not delete	the	associated music.
		try{
			if(iscommit == true)
			{
				userTransaction.begin();
			}
			String sql = "DELETE FROM Playlist p WHERE p.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(playlistid));
			queue.executeUpdate();
			if(iscommit == true)
			{
				userTransaction.commit();
			}
			return true;
		}catch(Exception e){
			System.out.println("Erro deleteProfile: "+e);
			if(iscommit == true)
			{
				try {
					userTransaction.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}

	@Override
	public List<Playlist> listMyPlaylists(String userid, String order) {
		// As a user, I	want to	list my	playlists in ascending or descending order.
		try{
			Users conta = devolverPorId(userid);

			String sql;
			if(order.equals("ASC"))
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name ASC";
			else
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name DESC";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", conta);
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
	public List<Music> listMyMusicFiles(String userid, String order) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.
		try{
			Users conta = devolverPorId(userid);

			String sql;
			sql = "FROM Music m WHERE m.user = :a ORDER BY m.title ASC";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", conta);
			//queue.setParameter("b", order);
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			return list;
		}catch(Exception e){
			System.out.println("Erro listMyPlaylist: "+e);
			return null;
		}
	}

	@Override
	public List<Music> listMyMusicFilesByPlaylist(String userid, String playlistid) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.
		//TODO verificar se necessito mesmo do iduser
		try{
			userTransaction.begin();
			String sql = "from Playlist p where p.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			
			List<Music> musica = playlist.getPlaylistSongs();
			System.out.println("A musica: "+musica.get(0));
			userTransaction.commit();
			return musica;
		}catch(Exception e){
			System.out.println("Erro listMyMusicFilesByPlaylist: "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public boolean addMusicFileToPlaylist(String musicid, String playlistid) {
		// As a	user I want	to add music files to a playlist.
		try{
			userTransaction.begin();
			String sql = "from Music m where m.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(musicid));
			Music musica = (Music) queue.getSingleResult();
			
			
			sql = "from Playlist p where p.id= :a";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			
			List<Playlist> addplaylist = musica.getPlaylist();
			List<Music> addmusic = playlist.getPlaylistSongs();
			
			addplaylist.add(playlist);
			addmusic.add(musica);
			
			musica.setPlaylist(addplaylist);
			playlist.setPlaylistSongs(addmusic);
			
			
			Cursor.persist(musica);
			Cursor.persist(playlist);
			userTransaction.commit();
			return true;
		}catch(Exception e){
			System.out.println("Erro listMyMusic: "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public boolean deleteMusicFileFromPlaylist(String music_id, String playlist_id) {
		// As a	user I want	to delete music files from a playlist.
		try{
			userTransaction.begin();
			String sql = "from Music m where m.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(music_id));
			Music musica = (Music) queue.getSingleResult();
			
			
			sql = "from Playlist p where p.id= :a";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlist_id));
			Playlist playlist = (Playlist) queue.getSingleResult();
			
			
			List<Music> listmusic = playlist.getPlaylistSongs();
			
			listmusic.remove(musica);
			
			

			playlist.setPlaylistSongs(listmusic);

			Cursor.persist(playlist);
			userTransaction.commit();
			return true;
		}catch(Exception e){
			System.out.println("Erro listMyMusic: "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public boolean addMusicFile(String userid, String title, String artist, String album, String year, String path) {
		// As a	user, I	want to	add	new	music to the application, identifying the title, artist, album, year and path to the file to upload	to the server.
		try{
			Users conta = devolverPorId(userid);
			userTransaction.begin();
			Music novamusica = new Music(conta, title, artist, album, year, path);
			Cursor.persist(novamusica);
			userTransaction.commit();
			return true;
		}catch(Exception e){
			System.out.println("Error addMusicFile: "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}
	@Override
	public Music getInfoMusicFile(String musicid){
		// Return Music info
		javax.persistence.Query q = Cursor.createQuery("from Music m where m.id = :t");
		q.setParameter("t", Integer.parseInt(musicid));

		try{
			Music infomusic = (Music) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			return infomusic;
		}catch(Exception e){
			return null;
		}

	}


	@Override
	public boolean editMusicFile(String musicid, String title, String artist, String album, String year) {
		// As a	user, I	want to	edit the data of music I added to the application.
		try{
			userTransaction.begin();
			String sql = "UPDATE Music m SET m.title= :a, m.artist= :c, m.album= :d, m.year= :e WHERE m.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", title);
			queue.setParameter("c", artist);
			queue.setParameter("d", album);
			queue.setParameter("e", year);
			queue.setParameter("b", Integer.parseInt(musicid));
			queue.executeUpdate();
			userTransaction.commit();
			return true;
		}catch(Exception e){
			System.out.println("Erro editProfile: "+e);

			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}

			return false;
		}
	}

	@Override
	public boolean detachFromMusic(String musicid, boolean iscommit) {
		// As a	user, I	want to	detach myself from music I uploaded. This should neither delete	music from the server, nor from	playlists.
		try{
			if(iscommit)
				userTransaction.begin();
			String sql = "UPDATE Music m SET m.user= NULL WHERE m.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(musicid));
			queue.executeUpdate();
			if(iscommit)
				userTransaction.commit();
			return true;
		}catch(Exception e){
			System.out.println("Erro editProfile: "+e);
			if(iscommit){
				try {
					userTransaction.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}

	@Override
	public List<Music> listAllMusic() {
		// As a	user, I	want to	list all the music registered in the application by	all	other users.
		try{
			String sql = "FROM Music m";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			return list;
		}catch(Exception e){
			System.out.println("Erro listAllMusic: "+e);
			return null;
		}
	}

	@Override
	public List<Music> searchAndListMusic(int tipo, String title, String artist) {
		// As a	user, I	want to	list all music registered in the application that satisfies some search	criteria over the title	and/or artist.
		try{
			String sql;
			javax.persistence.Query queue;
			if(tipo==0)
			{
				sql = "FROM Music m WHERE m.title = :a";
				queue = Cursor.createQuery(sql);
				queue.setParameter("a", title);
				System.out.println("title: "+title);
			} else {
				sql = "FROM Music m WHERE m.artist = :a";
				queue = Cursor.createQuery(sql);
				queue.setParameter("a", artist);
				System.out.println("artist: "+artist);
			}
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			System.out.println("musica: "+list.get(0).getTitle());
			return list;
		}catch(Exception e){
			System.out.println("Erro searchAndListMusic: "+e);
			return null;
		}
	}

}
