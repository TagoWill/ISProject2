package ejblogin;

import javax.ejb.Remote;

import ligacao.Users;

@Remote
public interface UserLoginRegisterRemote {
	public boolean registerUser(String nome, String user, String password, String email);
	public boolean verifyRegister(String user);
	public Users loginUser(String user, String password);
	public Users devolverPorId(String id);
}
