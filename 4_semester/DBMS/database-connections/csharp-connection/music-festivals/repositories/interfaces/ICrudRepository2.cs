namespace festival.repository;


public class RepositoryException2:ApplicationException{
    public RepositoryException2() { }
    public RepositoryException2(String mess) : base(mess){}
    public RepositoryException2(String mess, Exception e) : base(mess, e) { }
}
public interface ICrudRepository2<E> where E : model.AEntity
{
    E findOne(int id);

    IEnumerable<E> findAll();

    void save(E entity);

    void delete(int id);
}

