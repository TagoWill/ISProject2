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
    	System.out.println("Cheguei aqui");
    	try{
    		System.out.println("Cheguei aqui2");
    		cursor.persist(novaconta);
    		return true;
    	}catch(Exception e){
    		System.out.println("ERROR: "+e);
    		return false;
    	}
    }
    
    public Users loginUser(String user, String password){
    	javax.persistence.Query q = cursor.createQuery("from Users u where u.user = :t"
    			+ " and password = :p");
    	q.setParameter("t", user);
    	q.setParameter("p", password);
    	@SuppressWarnings("all")
    	Users conta = (Users) q.getSingleResult();
    	//System.out.println("TESTE: "+teste.getUser());
    	return conta;
    }

    public Users devolverTeste(String user){
    	javax.persistence.Query q = cursor.createQuery("from Users u where u.user = :t");
    	q.setParameter("t", user);
    	@SuppressWarnings("all")
    	Users conta = (Users) q.getSingleResult();
    	//System.out.println("TESTE: "+teste.getUser());
    	return conta;
    }
    
}
