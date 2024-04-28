using System.Data;
using festival.model;

namespace festival.repository;

public interface ISpectacolRepository : ICrudRepository2<Spectacol>
{
     IEnumerable<Spectacol> findByDay(DateTime searchDate);
     Boolean findByArtistAndData(Spectacol entity);

}