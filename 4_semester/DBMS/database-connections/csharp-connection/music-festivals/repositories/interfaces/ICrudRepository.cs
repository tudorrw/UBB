namespace festival.repository;

public class RepositoryException:ApplicationException{
    public RepositoryException() { }
    public RepositoryException(String mess) : base(mess){}
    public RepositoryException(String mess, Exception e) : base(mess, e) { }
}
public interface ICrudRepository<E> 
{
    E findOne(int id);

    IEnumerable<E> findAll();

    void save(E entity);

    void delete(int id);
}