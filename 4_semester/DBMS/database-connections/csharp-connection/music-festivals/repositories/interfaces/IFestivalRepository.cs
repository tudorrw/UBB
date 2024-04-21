using festival.model;

namespace festival.repository;

public interface IFestivalRepository : ICrudRepository<Festival>
{
    Boolean findByNameAndEdition(Festival entity);
}