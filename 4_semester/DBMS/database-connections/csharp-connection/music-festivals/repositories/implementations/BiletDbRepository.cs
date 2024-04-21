using System.Data;
using festival.database;
using festival.model;
using log4net;

namespace festival.repository;

public class BiletDbRepository : ICrudRepository2<Bilet>
{
    private static readonly ILog log = LogManager.GetLogger("BiletDBRepository");
    private IDictionary<String, string> props;

    public BiletDbRepository(IDictionary<string, string> props)
    {
        log.Info("Creating BiletDBRepository");
        this.props = props;
    }

    public Bilet findOne(int id)
    {
        throw new NotImplementedException();
    }

    public IEnumerable<Bilet> findAll()
    {
        IDbConnection con = DBUtils.getConnection(props);
        IList<Bilet> bilete = new List<Bilet>();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, nume_cumparator, cantitate, id_spectacol from bilet";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    int id = dataR.GetInt32(0);
                    string numeCumparator = dataR.GetString(1);
                    int cantitate = dataR.GetInt32(2);
                    int spectacolId = dataR.GetInt32(3);
                    Spectacol spectacol = fetchSpectacolById(spectacolId);
                    Bilet bilet = new Bilet(id, numeCumparator, cantitate, spectacol);
                    bilete.Add(bilet);
                }
            }
        }
        return bilete;    
    }


    public void save(Bilet entity)
    {
        var con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "insert into bilet values (@id, @nume_cumparator, @cantitate, @id_spectacol)";
            var paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = entity.id;
            comm.Parameters.Add(paramId);

            var paramCumparator = comm.CreateParameter();
            paramCumparator.ParameterName = "@nume_cumparator";
            paramCumparator.Value = entity.numeCumparator;
            comm.Parameters.Add(paramCumparator);
            
            var paramCantitate = comm.CreateParameter();
            paramCantitate.ParameterName = "@cantitate";
            paramCantitate.Value = entity.cantitate;
            comm.Parameters.Add(paramCantitate);
            
            var paramIdSpec = comm.CreateParameter();
            paramIdSpec.ParameterName = "@id_spectacol";
            paramIdSpec.Value = entity.spectacol.id;
            comm.Parameters.Add(paramIdSpec);

            var result = comm.ExecuteNonQuery();
            if (result == 0)
            {
                throw new RepositoryException2("No office added!");
            }
        }
    }
    
    public void delete(int id)
    {
        throw new NotImplementedException();
    }
    
    
    private Spectacol fetchSpectacolById(int id)
    {
        IDbConnection con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, artist_name, location, data, available_seats, sold_seats from spectacol where id=@id";
            IDbDataParameter paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = id;
            comm.Parameters.Add(paramId);

            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    int idV = dataR.GetInt32(0);
                    string artistName = dataR.GetString(1);
                    string location = dataR.GetString(2);
                    DateTime data = dataR.GetDateTime(3);
                    int availableSeats = dataR.GetInt32(4);
                    int soldSeats = dataR.GetInt32(5);

                    Spectacol spectacol = new Spectacol(idV, artistName, location, data, availableSeats, soldSeats);
                    log.InfoFormat("Exiting findOne with value {0}", spectacol);
                    return spectacol;
                }
            }
        }
        log.InfoFormat("Exiting findOne with value {0}", null);
        return null;
    }

}