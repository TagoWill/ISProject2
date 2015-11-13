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

	
	private static Logger logger = Logger.getLogger(ActionsBean.class);
	/**
	 * Default constructor. 
	 */
	public ActionsBean() {

	}

	@Override
	public boolean registerUser(String nome, String user, String password, String email){

		try{
			userTransaction.begin();
			logger.info("Iniciar registo");
			Users novaconta = new Users(nome, user, password, email);
			logger.info("Criar Objecto User");
			Cursor.persist(novaconta);
			logger.info("Fazer persist");
			userTransaction.commit();
			logger.info("Registo concluido");
			return true;
		}catch(Exception e){
			logger.error("error registerUser: "+e);
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
			logger.info("Iniciar verificar se email ja exite");
			logger.info("Criar query");
			javax.persistence.Query q = Cursor.createQuery("from Users u where u.email = :t");
			q.setParameter("t", email);

			logger.info("Verificar resultado");
			@SuppressWarnings("all")
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());

			if(conta == null){
				logger.info("Nao exite nenhum email. Fim do processo");
				return true;
			}else{
				logger.info("Ja existe email. fim do processo");
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
			logger.info("Iniciar loginUser");
			logger.info("Criar Query");
			javax.persistence.Query q = Cursor.createQuery("from Users u where u.email = :t"
					+ " and password = :p");
			q.setParameter("t", email);
			q.setParameter("p", password);

			logger.info("Verificar resultado");
			@SuppressWarnings("all")
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			logger.info("Devolver user. Fim do processo");
			return conta;
		}catch(Exception e){
			logger.error("error loginUser. Nao pode devolver o user. Fim do processo");
			return null;
		}
	}

	@Override
	public Users devolverPorId(String id){
		logger.info("Iniciar devolverPorId");
		logger.info("Criar Query");
		javax.persistence.Query q = Cursor.createQuery("from Users u where u.id = :t");
		q.setParameter("t", Integer.parseInt(id));

		try{
			logger.info("Verificar resultado");
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			logger.info("Devolver User. Fim do processo");
			return conta;
		}catch(Exception e){
			logger.error("error devolverPorId. Nao pode devolver user. Fim do processo");
			return null;
		}
	}

	@Override
	public boolean editProfile(String userid, String name, String mail, String password) {
		// Editar perfil do Utilizador
		try{
			logger.info("Iniciar editProfile");
			userTransaction.begin();
			logger.info("Criar query");
			String sql = "UPDATE Users u SET u.nome= :a, u.email= :c, u.password= :d WHERE u.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", name);
			queue.setParameter("c", mail);
			queue.setParameter("d", password);
			queue.setParameter("b", Integer.parseInt(userid));
			queue.executeUpdate();
			logger.info("Executar update");
			userTransaction.commit();
			logger.info("Fim do processo");
			return true;
		}catch(Exception e){
			logger.error("Error editProfile "+e);

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

		logger.info("Iniciar deleteProfile");
		String sql;
		javax.persistence.Query queue;


		try{
			userTransaction.begin();
			logger.info("Buscar user");
			Users conta = devolverPorId(userid);

			logger.info("Pagar as suas Playlists");
			for(Playlist list : conta.getPlaylist()){
				deletePlaylist(Integer.toString(list.getId()), false);
			}

			logger.info("Desassociar as suas musicas");
			for(Music list : conta.getSong()){
				detachFromMusic(Integer.toString(list.getId()), false);
			}

			logger.info("Criar query");
			sql = "DELETE FROM Users u WHERE u.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("b", conta.getId());
			queue.executeUpdate();
			logger.info("Executar query apagar");
			userTransaction.commit();
			logger.info("Fim di processo");
			return true;
		}catch(Exception e){
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			logger.error("Erro deleteProfile: "+e);
			return false;
		}
	}

	@Override
	public Playlist getPlaylistName(String playlistid) {
		logger.info("Iniciar getPlaylistName");
		logger.info("Criar query");
		javax.persistence.Query q = Cursor.createQuery("FROM Playlist p WHERE p.id = :i");
		q.setParameter("i", Integer.parseInt(playlistid));
		try{
			logger.info("Verificar resultado");
			Playlist conta = (Playlist) q.getSingleResult();
			logger.info("Devolver playlist. Fim do precesso");
			return conta;
		}catch(Exception e){
			logger.error("error getPlaylistName"+e);
			return null;
		}
	}

	@Override
	public boolean addPlaylist(String userid, String playlist_name) {
		// As a	user, I	want to	create new playlists and assign	them a name
		logger.info("Iniciar addPlaylist");
		try{
			logger.info("Buscar user");
			Users conta = devolverPorId(userid);

			logger.info("Criar query");
			String sql = "FROM Playlist p WHERE p.playlist_name = :b AND p.user= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", playlist_name);
			queue.setParameter("c", conta);
			logger.info("Executar query");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("Ja existe playlist. Fim do processo");
				return false;
			}else{
				logger.info("Nao existe playlist");
				userTransaction.begin();
				logger.info("Criar objecto");
				Playlist novaplaylist = new Playlist(conta, playlist_name, null);
				Cursor.persist(novaplaylist);
				logger.info("Executar");
				userTransaction.commit();
				logger.info("Fim do processo");
				return true;
			}
		}catch(Exception e){
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			logger.error("Erro addPlaylist: "+e);
			return false;
		}
	}

	@Override
	public boolean editPlaylist(String userid, String playlistid, String playlist_name) {
		// As a	user, I	want to	edit the name of the playlists.
		logger.info("Iniciar editPlaylist");
		try{
			userTransaction.begin();
			Users conta = devolverPorId(userid);
			String sql = "FROM Playlist p WHERE p.playlist_name = :b AND p.user= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", playlist_name);
			queue.setParameter("c", conta);
			logger.info("Executar query");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("Ja existe playlist. Fim do processo");
				userTransaction.commit();
				return false;
			}
			
			logger.info("Criar query");
			sql = "UPDATE Playlist p SET p.playlist_name= :a WHERE p.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", playlist_name);
			queue.setParameter("b", Integer.parseInt(playlistid));
			queue.executeUpdate();
			logger.info("Executar query");
			userTransaction.commit();
			logger.info("Fim do processo");
			return true;
		}catch(Exception e){
			logger.error("Erro editPlaylist: "+e);
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
		logger.info("Iniciar deletePlaylist");
		try{
			if(iscommit == true)
			{
				userTransaction.begin();
			}
			logger.info("Buscar playlist");
			String sql = "FROM Playlist p WHERE p.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			
			logger.info("Remover musicas associadas");
			playlist.setPlaylistSongs(null);
			Cursor.persist(playlist);
			
			logger.info("Criar query delete");
			sql = "DELETE FROM Playlist p WHERE p.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(playlistid));
			queue.executeUpdate();
			logger.info("Executar delete");
			if(iscommit == true)
			{
				userTransaction.commit();
			}
			logger.info("Fim do processo");
			return true;
		}catch(Exception e){
			logger.error("Erro deleteProfile: "+e);
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
		logger.info("Iniciar listMyPlaylists");
		try{
			userTransaction.begin();
			logger.info("Buscar useres");
			Users conta = devolverPorId(userid);

			String sql;
			logger.info("Criar query com tipo de ordenamento");
			if(order.equals("ASC"))
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name ASC";
			else
				sql = "FROM Playlist p WHERE p.user = :a ORDER BY p.playlist_name DESC";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", conta);
			//queue.setParameter("b", order);
			logger.info("Executar query");
			@SuppressWarnings("unchecked")
			List<Playlist> list = queue.getResultList();
			logger.info("Devolver lista. Fim do processo");
			userTransaction.commit();
			return list;
		}catch(Exception e){
			try {
				userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("Erro listMyPlaylist: "+e);
			return null;
		}
	}

	@Override
	public List<Music> listMyMusicFiles(String userid, String order) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.
		logger.info("Iniciar listMyMusicFiles");
		try{
			logger.info("Buscar user");
			Users conta = devolverPorId(userid);

			logger.info("Criar query");
			String sql;
			sql = "FROM Music m WHERE m.user = :a ORDER BY m.title ASC";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", conta);
			//queue.setParameter("b", order);
			logger.info("Executar query");
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			logger.info("Devolver lista. Fim do processo");
			return list;
		}catch(Exception e){
			logger.error("Erro listMyPlaylist: "+e);
			return null;
		}
	}

	@Override
	public List<Music> listMyMusicFilesByPlaylist(String userid, String playlistid) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.
		logger.info("Iniciar listMyMusicFilesByPlaylist");
		try{
			userTransaction.begin();
			logger.info("Criar query de playlist");
			String sql = "from Playlist p where p.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			logger.info("Receber a playlist correspondente");
			
			logger.info("Buscar musicas associadas");
			List<Music> musica = playlist.getPlaylistSongs();
			logger.info("Devolver musicas. Fim do processo");
			userTransaction.commit();
			return musica;
		}catch(Exception e){
			logger.error("Erro listMyMusicFilesByPlaylist: "+e);
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
		logger.info("Iniciar addMusicFileToPlaylist");
		try{
			userTransaction.begin();
			logger.info("Criar query para a musica");
			String sql = "from Music m where m.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(musicid));
			Music musica = (Music) queue.getSingleResult();
			logger.info("Obter musica selecionada");
			logger.info("Criar query para playlist");
			sql = "from Playlist p where p.id= :a";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlistid));
			Playlist playlist = (Playlist) queue.getSingleResult();
			logger.info("Obter playlist selecionada");
			logger.info("Registar associacao");
			List<Playlist> addplaylist = musica.getPlaylist();
			List<Music> addmusic = playlist.getPlaylistSongs();
			
			if(addmusic.indexOf(musica) != -1){
				logger.info("Musica ja existe");
				userTransaction.commit();
				return false;
			}
			
			addplaylist.add(playlist);
			addmusic.add(musica);
			
			musica.setPlaylist(addplaylist);
			playlist.setPlaylistSongs(addmusic);
			
			
			Cursor.persist(musica);
			Cursor.persist(playlist);
			userTransaction.commit();
			logger.info("Fim do processo");
			return true;
		}catch(Exception e){
			logger.error("Erro listMyMusic: "+e);
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
		logger.info("Iniciar deleteMusicFileFromPlaylist");
		try{
			userTransaction.begin();
			logger.info("Criar query para musica");
			String sql = "from Music m where m.id= :a";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(music_id));
			Music musica = (Music) queue.getSingleResult();
			logger.info("Obter musica");
			logger.info("Criar query para playlist");
			sql = "from Playlist p where p.id= :a";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", Integer.parseInt(playlist_id));
			Playlist playlist = (Playlist) queue.getSingleResult();
			logger.info("Obter playlist");
			
			List<Music> listmusic = playlist.getPlaylistSongs();
			logger.info("Remover musica da associacao");
			listmusic.remove(musica);
			
			

			playlist.setPlaylistSongs(listmusic);

			Cursor.persist(playlist);
			userTransaction.commit();
			logger.info("Fim do processo");
			return true;
		}catch(Exception e){
			logger.error("Erro listMyMusic: "+e);
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
		logger.info("Iniciar addMusicFile");
		try{
			logger.info("Buscar user");
			Users conta = devolverPorId(userid);
			userTransaction.begin();
			
			String sql = "FROM Music m WHERE m.title = :b AND m.artist= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", title);
			queue.setParameter("c", artist);
			logger.info("Executar query");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("Ja existe musica igual. Fim do processo");
				userTransaction.commit();
				return false;
			}
			
			logger.info("Criar nova musica");
			Music novamusica = new Music(conta, title, artist, album, year, path);
			Cursor.persist(novamusica);
			userTransaction.commit();
			logger.info("Fim do processo");
			return true;
		}catch(Exception e){
			logger.error("Error addMusicFile: "+e);
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
		logger.info("Iniciar getInfoMusicFile");
		logger.info("Criar query para musica");
		javax.persistence.Query q = Cursor.createQuery("from Music m where m.id = :t");
		q.setParameter("t", Integer.parseInt(musicid));

		try{
			logger.info("Obter musica");
			Music infomusic = (Music) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			logger.info("Devolver musica. Fim do processo");
			return infomusic;
		}catch(Exception e){
			return null;
		}

	}


	@Override
	public boolean editMusicFile(String musicid, String title, String artist, String album, String year) {
		// As a	user, I	want to	edit the data of music I added to the application.
		logger.info("Iniciar editMusicFile");
		try{
			userTransaction.begin();
			
			String sql = "FROM Music m WHERE m.title = :b AND m.artist= :c";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", title);
			queue.setParameter("c", artist);
			logger.info("Executar query");
			@SuppressWarnings("rawtypes")
			List entities = queue.getResultList();
			if(entities.size()>0){
				logger.info("Ja existe musica igual. Fim do processo");
				userTransaction.commit();
				return false;
			}
			
			logger.info("Criar query para update");
			sql = "UPDATE Music m SET m.title= :a, m.artist= :c, m.album= :d, m.year= :e WHERE m.id = :b";
			queue = Cursor.createQuery(sql);
			queue.setParameter("a", title);
			queue.setParameter("c", artist);
			queue.setParameter("d", album);
			queue.setParameter("e", year);
			queue.setParameter("b", Integer.parseInt(musicid));
			queue.executeUpdate();
			logger.info("Executar query");
			userTransaction.commit();
			logger.info("Fim do processo");
			return true;
		}catch(Exception e){
			logger.error("Erro editProfile: "+e);

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
		logger.info("Iniciar detachFromMusic");
		try{
			if(iscommit)
				userTransaction.begin();
			logger.info("Criar query para musica");
			String sql = "UPDATE Music m SET m.user= NULL WHERE m.id = :b";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			queue.setParameter("b", Integer.parseInt(musicid));
			queue.executeUpdate();
			logger.info("Executar query. Fim do processo");
			if(iscommit)
				userTransaction.commit();
			return true;
		}catch(Exception e){
			logger.error("Erro editProfile: "+e);
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
		logger.info("Iniciar listAllMusic");
		try{
			logger.info("Criar query");
			String sql = "FROM Music m";
			javax.persistence.Query queue = Cursor.createQuery(sql);
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			logger.info("Devolver lista musicas. Fim do processo");
			return list;
		}catch(Exception e){
			logger.error("Erro listAllMusic: "+e);
			return null;
		}
	}

	@Override
	public List<Music> searchAndListMusic(int tipo, String title, String artist) {
		// As a	user, I	want to	list all music registered in the application that satisfies some search	criteria over the title	and/or artist.
		logger.info("Iniciar searchAndListMusic");
		try{
			String sql;
			javax.persistence.Query queue;
			logger.info("Criar query escolhendo o tipo de pesquisa");
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
			logger.info("Obter lista de musicas");
			@SuppressWarnings("unchecked")
			List<Music> list = queue.getResultList();
			logger.info("Devolver lista. Fim do processo");
			return list;
		}catch(Exception e){
			logger.error("Erro searchAndListMusic: "+e);
			return null;
		}
	}

}
