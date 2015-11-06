package ejblogin;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class ActionsBean
 */
@Stateless
@LocalBean
public class ActionsBean implements ActionsBeanRemote {

    /**
     * Default constructor. 
     */
    public ActionsBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addPlaylist(String user, String playlist_name) {
		// As a	user, I	want to	create new playlists and assign	them a name
		
	}

	@Override
	public void editPlaylist(String user, String playlist_name) {
		// As a	user, I	want to	edit the name of the playlists.
		
	}

	@Override
	public void deletePlaylist(String user, String playlist_name) {
		// As a	user, I	want to	be able	to delete a	playlist. Deleting a playlist should not delete	the	associated music.
		
	}

	@Override
	public void listMyPlaylists(String user, String order) {
		// As a user, I	want to	list my	playlists in ascending or descending order.
		
	}

	@Override
	public void listMyMusicFiles(String user, String playlist_name) {
		// As a	user, I	want to	list music files associated	to each playlist. The user might have to select the playlist for that.
		
	}

	@Override
	public void addMusicFileToPlaylist(String user, String playlist_name) {
		// As a	user I want	to add music files to a playlist.
		
	}

	@Override
	public void deleteMusicFileFromPlaylist(String user, String playlist_name) {
		// As a	user I want	to delete music files from a playlist.
		
	}

	@Override
	public void addMusicFile(String user, String title, String artist, String album, String year, String path) {
		// As a	user, I	want to	add	new	music to the application, identifying the title, artist, album, year and path to the file to upload	to the server.
		
	}

	@Override
	public void editMusicFile(String user, String title, String artist, String album, String year, String path) {
		// As a	user, I	want to	edit the data of music I added to the application.
		
	}

	@Override
	public void detachFromMusic(String user, String title) {
		// As a	user, I	want to	detach myself from music I uploaded. This should neither delete	music from the server, nor from	playlists.
		
	}

	@Override
	public void listAllMusic(String user) {
		// As a	user, I	want to	list all the music registered in the application by	all	other users.
		
	}

	@Override
	public void searchAndListMusic(String user, String title, String artist) {
		// As a	user, I	want to	list all music registered in the application that satisfies some search	criteria over the title	and/or artist.
		
	}

}