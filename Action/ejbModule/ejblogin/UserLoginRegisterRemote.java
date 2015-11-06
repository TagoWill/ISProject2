package ejblogin;

import javax.ejb.Remote;

import ligacao.Users;

@Remote
public interface UserLoginRegisterRemote {
	public boolean registerUser(String nome, String user, String password, String email);
	public Users loginUser(String user, String password);
	public Users devolverTeste(String user);
}
