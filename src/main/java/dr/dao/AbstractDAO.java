package dr.dao;


import java.io.Serializable;
import java.util.List;

/**
 * Interface mãe de todas as interfaces do repositório. Esta
 * @author André Ricardo Gnhoato.
 * 
 * @param <T> alguma classe do modelo de negócio.
 */
public interface AbstractDAO<T> {
	
	T save(T object) throws Exception;
	
	T update(T object) throws Exception;
	
	T findById(Serializable id) throws Exception;
	
	boolean remove(T object)throws Exception;
	
	List<T> findAll() throws Exception;

}