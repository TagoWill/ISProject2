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
import org.jboss.logging.Logger;
import data.Music;
import data.Playlist;
import data.Users;

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

	private static Logger logger = Logger.getLogger(ActionsBean.class);
	/**
	 * Default constructor. 
	 */
	public ActionsBean() {

	}

	@Override
	public boolean registerUser(String nome, String user, String password, String email) {
		try{
			userTransaction.begin();
			logger.info("registerUser: Iniciado");
			Users novaconta = new Users(nome, user, password, email);
			logger.info("registerUser: Criado Objecto User");
			Cursor.persist(novaconta);
			logger.info("registerUser: Persist ao User");
			userTransaction.commit();
			logger.info("registerUser: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("registerUser: Erro "+e);
				try {
					userTransaction.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					e1.printStackTrace();
				}
			return false;
		}
	}

	@Override
	public boolean verifyRegister(String email){
		try{
			logger.info("verifyRegister: Iniciado");
			javax.persistence.Query q = Cursor.createQuery("FROM Users u WHERE u.email = :t");
			q.setParameter("t", email);
			logger.info("verifyRegister: Executada Query Select User");
			@SuppressWarnings("all")
			Users conta = (Users) q.getSingleResult();
			if(conta == null){
				logger.info("verifyRegister: Sucesso!");
				return true;
			}else{
				logger.info("verifyRegister: Falhou!");
				return false;
			}
		}catch(Exception e){
			return true;
		}
	}

	@Override
	public Users loginUser(String email, String password){
		try{
			logger.info("loginUser: Iniciado");
			javax.persistence.Query q = Cursor.createQuery("FROM Users u WHERE u.email = :t"
					+ " and password = :p");
			q.setParameter("t", email);
			q.setParameter("p", password);
			logger.info("loginUser: Executada Query Select User");
			@SuppressWarnings("all")
			Users conta = (Users) q.getSingleResult();
			logger.info("loginUser: Sucesso!");
			return conta;
		}catch(Exception e){
			logger.error("loginUser: Falhou!");
			return null;
		}
	}

	@Override
	public Users getUserByID(String id){
		logger.info("getUserByID: Iniciado");
		javax.persistence.Query q = Cursor.createQuery("FROM Users u WHERE u.id = :t");
		q.setParameter("t", Integer.parseInt(id));
		logger.info("getUserByID: Executada Query Select User");
		try{
			Users conta = (Users) q.getSingleResult();
			logger.info("getUserByID: Sucesso!");
			return conta;
		}catch(Exception e){
			logger.error("getUserByID: Falhou!");
			return null;
		}
	}

	@Override
	public boolean editProfile(String userid, String name, String mail, String password) {
		// Editar perfil do Utilizador
		try{
			logger.info("editProfile: Iniciado");
			userTransaction.begin();
			String sql = "UPDATE Users u SET u.nome= :a, u.email= :c, u.password= :d WHERE u.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", name);
			queue.setParameter("c", mail);
			queue.setParameter("d", password);
			queue.setParameter("b", Integer.parseInt(userid));
			queue.executeUpdate();
			logger.info("editProfile: Executada Query Update User");
			userTransaction.commit();
			logger.info("editProfile: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("editProfile: Falhou! "+e);
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
		logger.info("deleteProfile: Iniciado");
		String sql;
		javax.persistence.Query queue;
		try{
			userTransaction.begin();
			logger.info("deleteProfile: Link para GetUserByID");
			Users conta = getUserByID(userid);
			for(Playlist list : conta.getPlaylist()){
				deletePlaylist(Integer.toString(list.getId()), false);
				logger.info("deleteProfile: Apagada Playlist");
			}
			for(Music list : conta.getSong()){
				detachFromMusic(Integer.toString(list.getId()), false);
				logger.info("deleteProfile: Apagada Musica");
			}
			sql = "DELETE FROM Users u WHERE u.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("b", conta.getId());
			queue.executeUpdate();
			logger.info("deleteProfile: Executada Query Delete User");
			userTransaction.commit();
			logger.info("deleteProfile: Sucesso!");
			return true;
		}catch(Exception e){
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			logger.error("deleteProfile: Falhou! "+e);
			return false;
		}
	}

	@Override
	public Playlist getPlaylistName(String playlistid) {
		logger.info("getPlaylistName: Iniciado");
		javax.persistence.Query q = Cursor.createQuery("FROM Playlist p WHERE p.id = :i");
		q.setParameter("i", Integer.parseInt(playlistid));
		logger.info("getPlaylistName: Executada Query Select Playlist");
		try{
			Playlist conta = (Playlist) q.getSingleResult();
			logger.info("getPlaylistName: Sucesso!");
			return conta;
		}catch(Exception e){
			logger.error("getPlaylistName: Falhou! "+e);
			return null;
		}
	}

	@Override
	public boolean addPlaylist(String userid, String playlist_name) {
		// As a	user, I	want to	create new playlists and assign	them a name
		logger.info("addPlaylist: Iniciada");
		try{
			logger.info("addPlaylist: Link para getUserByID");
			Users conta = getUserByID(userid);
			String sql = "FROM Playlist p WHERE p.playlist_name = :b AND p.user= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", playlist_name);
			queue.setParameter("c", conta);
			logger.info("addPlaylist: Executada Query Select Playlist");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("addPlaylist: Falhou!");
				return false;
			}else{
				userTransaction.begin();
				Playlist novaplaylist = new Playlist(conta, playlist_name, null);
				Cursor.persist(novaplaylist);
				logger.info("addPlaylist: Criada nova playlist");
				userTransaction.commit();
				logger.info("addPlaylist: Sucesso!");
				return true;
			}
		}catch(Exception e){
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			logger.error("addPlaylist: Falhou! "+e);
			return false;
		}
	}

	@Override
	public boolean editPlaylist(String userid, String playlistid, String playlist_name) {
		// As a	user, I	want to	edit the name of the playlists.
		logger.info("editPlaylist: Iniciado");
		try{
			userTransaction.begin();
			logger.info("editPlaylist: Link para getUserByID");
			Users conta = getUserByID(userid);
			String sql = "FROM Playlist p WHERE p.playlist_name = :b AND p.user= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", playlist_name);
			queue.setParameter("c", conta);
			logger.info("editPlaylist: Executada Query Select Playlist");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("editPlaylist: Falhou!");
				userTransaction.commit();
				return false;
			}
			sql = "UPDATE Playlist p SET p.playlist_name= :a WHERE p.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", playlist_name);
			queue.setParameter("b", Integer.parseInt(playlistid));
			logger.info("editPlaylist: Executada Query Update Playlist");
			queue.executeUpdate();
			userTransaction.commit();
			logger.info("editPlaylist: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("editPlaylist: Falhou! "+e);
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
		logger.info("deletePlaylist: Iniciado");
		try{
			if(iscommit == true)
			{
				logger.info("deletePlaylist: Pode fazer commit");
				userTransaction.begin();
			}
			String sql = "FROM Playlist p WHERE p.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			logger.info("deletePlaylist: Executada Query Select Playlist");
			playlist.setPlaylistSongs(null);
			Cursor.persist(playlist);
			logger.info("deletePlaylist: Persist Playlist");
			sql = "DELETE FROM Playlist p WHERE p.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(playlistid));
			queue.executeUpdate();
			logger.info("deletePlaylist: Executada Query Delete Playlist");
			if(iscommit == true)
			{
				userTransaction.commit();
			}
			logger.info("deletePlaylist: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("deleteProfile: Falhou! "+e);
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
		logger.info("listMyPlaylists: Iniciado");
		try{
			userTransaction.begin();
			logger.info("listMyPlaylists: Link para getUserByID");
			Users conta = getUserByID(userid);
			String sql;
			if(order.equals("ASC"))
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name ASC";
			else
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name DESC";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", conta);
			logger.info("listMyPlaylists: Executada Query Select Playlist Order By");
			@SuppressWarnings("unchecked")
			List<Playlist> list = queue.getResultList();
			logger.info("listMyPlaylists: Sucesso!");
			userTransaction.commit();
			return list;
		}catch(Exception e){
			logger.error("listMyPlaylist: Falhou! "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public List<Music> listMyMusicFiles(String userid, String order) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.
		logger.info("listMyMusicFiles: Iniciado");
		try{
			logger.info("listMyMusicFiles: Link para getUserByID");
			Users conta = getUserByID(userid);
			String sql = "FROM Music m WHERE m.user = :a ORDER BY m.title ASC";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", conta);
			logger.info("listMyMusicFiles: Executada Query Select Music");
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			logger.info("listMyMusicFiles: Sucesso!");
			return list;
		}catch(Exception e){
			logger.error("listMyPlaylist: Falhou! "+e);
			return null;
		}
	}

	@Override
	public List<Music> listMyMusicFilesByPlaylist(String userid, String playlistid) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.
		logger.info("listMyMusicFilesByPlaylist: Iniciado!");
		try{
			userTransaction.begin();
			String sql = "FROM Playlist p WHERE p.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			logger.info("listMyMusicFilesByPlaylist: Executada Query Select Playlist");
			List<Music> musica = playlist.getPlaylistSongs();
			logger.info("listMyMusicFilesByPlaylist: Sucesso!");
			userTransaction.commit();
			return musica;
		}catch(Exception e){
			logger.error("listMyMusicFilesByPlaylist: Falhou! "+e);
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}

			return null;
		}
	}

	@Override
	public boolean addMusicFileToPlaylist(String musicid, String playlistid) {
		// As a	user I want	to add music files to a playlist.
		logger.info("addMusicFileToPlaylist: Iniciado");
		try{
			userTransaction.begin();
			String sql = "FROM Music m WHERE m.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(musicid));
			Music musica = (Music) queue.getSingleResult();
			logger.info("addMusicFileToPlaylist: Executada Query Select Music");
			sql = "FROM Playlist p WHERE p.id= :a";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			logger.info("addMusicFileToPlaylist: Executada Query Select Playlist");
			List<Playlist> addplaylist = musica.getPlaylist();
			List<Music> addmusic = playlist.getPlaylistSongs();
			if(addmusic.indexOf(musica) != -1){
				logger.info("addMusicFileToPlaylist: Falhou! Musica ja existe");
				userTransaction.commit();
				return false;
			}
			addplaylist.add(playlist);
			addmusic.add(musica);
			musica.setPlaylist(addplaylist);
			playlist.setPlaylistSongs(addmusic);
			logger.info("addMusicFileToPlaylist: Registada associacao entre Musica e Playlist");
			Cursor.persist(musica);
			Cursor.persist(playlist);
			logger.info("addMusicFileToPlaylist: Persist Musica e Playlist");
			userTransaction.commit();
			logger.info("addMusicFileToPlaylist: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("addMusicFileToPlaylist: Falhou! "+e);
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
		logger.info("deleteMusicFileFromPlaylist: Iniciado");
		try{
			userTransaction.begin();
			String sql = "FROM Music m WHERE m.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(music_id));
			Music musica = (Music) queue.getSingleResult();
			logger.info("deleteMusicFileFromPlaylist: Executada Query Select Music");
			sql = "FROM Playlist p WHERE p.id= :a";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlist_id));
			Playlist playlist = (Playlist) queue.getSingleResult();
			logger.info("deleteMusicFileFromPlaylist: Executada Query Select Playlist");
			List<Music> listmusic = playlist.getPlaylistSongs();
			listmusic.remove(musica);
			logger.info("deleteMusicFileFromPlaylist: Removida musica da associacao");
			playlist.setPlaylistSongs(listmusic);
			Cursor.persist(playlist);
			logger.info("deleteMusicFileFromPlaylist: Persist Playlist");
			userTransaction.commit();
			logger.info("deleteMusicFileFromPlaylist: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("deleteMusicFileFromPlaylist: Falhou! "+e);
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
		logger.info("addMusicFile: Iniciado");
		try{
			logger.info("addMusicFile: Link para getUserByID");
			Users conta = getUserByID(userid);
			userTransaction.begin();
			String sql = "FROM Music m WHERE m.title = :b AND m.artist= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", title);
			queue.setParameter("c", artist);
			logger.info("addMusicFile: Executada Query Select Music");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("addMusicFile: Falhou! Ja existe musica igual");
				userTransaction.commit();
				return false;
			}
			Music novamusica = new Music(conta, title, artist, album, year, path);
			logger.info("addMusicFile: Criada nova musica");
			Cursor.persist(novamusica);
			userTransaction.commit();
			logger.info("addMusicFile: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("addMusicFile: Falhou! "+e);
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
		logger.info("getInfoMusicFile: Iniciado");
		javax.persistence.Query q = Cursor.createQuery("FROM Music m WHERE m.id = :t");
		q.setParameter("t", Integer.parseInt(musicid));
		logger.info("getInfoMusicFile: Executada Query Select Music");
		try{
			Music infomusic = (Music) q.getSingleResult();
			logger.info("getInfoMusicFile: Sucesso!");
			return infomusic;
		}catch(Exception e){
			logger.info("getInfoMusicFile: Falhou!");
			return null;
		}
	}

	@Override
	public boolean editMusicFile(String musicid, String title, String artist, String album, String year) {
		// As a	user, I	want to	edit the data of music I added to the application.
		logger.info("editMusicFile: Iniciado");
		try{
			userTransaction.begin();
			String sql = "FROM Music m WHERE m.title = :b AND m.artist= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", title);
			queue.setParameter("c", artist);
			logger.info("editMusicFile: Executada Query Select Music");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("editMusicFile: Falhou! Ja existe musica igual");
				userTransaction.commit();
				return false;
			}
			sql = "UPDATE Music m SET m.title= :a, m.artist= :c, m.album= :d, m.year= :e WHERE m.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", title);
			queue.setParameter("c", artist);
			queue.setParameter("d", album);
			queue.setParameter("e", year);
			queue.setParameter("b", Integer.parseInt(musicid));
			queue.executeUpdate();
			logger.info("editMusicFile: Executada Query Update Music");
			userTransaction.commit();
			logger.info("editMusicFile: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("editMusicFile: Falhou! "+e);
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
		logger.info("detachFromMusic: Iniciado");
		try{
			if(iscommit) {
				logger.info("detachFromMusic: iscommit true");
				userTransaction.begin();
			}
			String sql = "UPDATE Music m SET m.user= NULL WHERE m.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(musicid));
			queue.executeUpdate();
			logger.info("detachFromMusic: Executada Query Update Music");
			if(iscommit)
				userTransaction.commit();
			logger.info("detachFromMusic: Sucesso!");
			return true;
		}catch(Exception e){
			logger.error("detachFromMusic: Falhou! "+e);
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
		logger.info("listAllMusic: Iniciado");
		try{
			String sql = "FROM Music m";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			logger.info("listAllMusic: Executada Query Select Music");
			logger.info("listAllMusic: Sucesso!");
			return list;
		}catch(Exception e){
			logger.error("listAllMusic: Falhou! "+e);
			return null;
		}
	}

	@Override
	public List<Music> searchAndListMusic(int tipo, String title, String artist) {
		// As a	user, I	want to	list all music registered in the application that satisfies some search	criteria over the title	and/or artist.
		logger.info("searchAndListMusic: Iniciado");
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
			logger.info("searchAndListMusic: Executada Query Select Music");
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			logger.info("searchAndListMusic: Sucesso!");
			return list;
		}catch(Exception e){
			logger.error("searchAndListMusic: Falhou! "+e);
			return null;
		}
	}
}
