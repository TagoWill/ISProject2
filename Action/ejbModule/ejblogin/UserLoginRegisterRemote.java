package ejblogin;

import javax.ejb.Remote;

import ligacao.Users;

@Remote
public interface UserLoginRegisterRemote {
	public boolean registerUser(String nome, String user, String password, String email);
	public boolean verifyRegister(String email);
	public Users loginUser(String email, String password);
	public Users devolverPorId(String id);
}
