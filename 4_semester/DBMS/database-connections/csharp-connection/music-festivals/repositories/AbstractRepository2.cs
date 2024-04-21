namespace festival.repository;

public abstract class AbstractRepository2<E> : ICrudRepository2<E> where E : model.AEntity
{
    private IDictionary<int, E> items;

    public AbstractRepository2()
    {
        items = new Dictionary<int, E>();
    }
    
    public E findOne(int id)
    {
			
        if (items.ContainsKey(id))
            return items[id];
        else
            return default(E);
    }
    
    public IEnumerable<E> findAll()
    {
        return items.Values;
    }
    
    public virtual void save(E entity)
    {
        if (!items.ContainsKey(entity.id))
            items.Add(entity.id, entity);
        else throw new RepositoryException2("Duplicate entity " + entity);
    }
    
    public virtual void delete(int id)
    {
        items.Remove(id);
    }
}