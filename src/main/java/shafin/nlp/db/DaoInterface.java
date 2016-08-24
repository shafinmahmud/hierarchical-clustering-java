package shafin.nlp.db;

import java.util.Collection;

public interface DaoInterface<T> {

	public T findOne(int id);

	public Collection<T> findAll();

	public boolean insertOne(T object);

	public boolean updateOne(T object);

	public boolean deleteOne(T object);

}
