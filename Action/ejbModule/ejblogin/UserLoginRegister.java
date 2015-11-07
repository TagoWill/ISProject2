package ejblogin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ligacao.Users;

/**
 * Session Bean implementation class UserLoginRegister
 */
@Stateless
public class UserLoginRegister implements UserLoginRegisterRemote {

	@PersistenceContext(name="Users")
	EntityManager cursor;


	public UserLoginRegister() {
		// TODO Auto-generated constructor stub
	}

	public boolean registerUser(String nome, String user, String password, String email){
		Users novaconta = new Users(nome, user, password, email);
		try{
			cursor.persist(novaconta);
			return true;
		}catch(Exception e){
			//System.out.println("ERROR: "+e);
			return false;
		}

	}

	public boolean verifyRegister(String user){
		try{
			javax.persistence.Query q = cursor.createQuery("from Users u where u.username = :t");
			q.setParameter("t", user);

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

	public Users loginUser(String user, String password){
		try{
			javax.persistence.Query q = cursor.createQuery("from Users u where u.username = :t"
					+ " and password = :p");
			q.setParameter("t", user);
			q.setParameter("p", password);

			@SuppressWarnings("all")
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			return conta;
		}catch(Exception e){
			return null;
		}
	}

	public Users devolverPorId(String id){
		javax.persistence.Query q = cursor.createQuery("from Users u where u.id = :t");
		q.setParameter("t", Integer.parseInt(id));

		try{
			Users conta = (Users) q.getSingleResult();
			//System.out.println("TESTE: "+teste.getUser());
			return conta;
		}catch(Exception e){
			return null;
		}
	}

}
