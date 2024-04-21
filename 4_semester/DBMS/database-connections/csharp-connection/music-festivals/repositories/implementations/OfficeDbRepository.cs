using System.Data;
using festival.database;
using festival.model;

using log4net;

namespace festival.repository;

public class OfficeDbRepository : ICrudRepository2<Office>
{
    private static readonly ILog log = LogManager.GetLogger("OfficeDBRepository");
    private IDictionary<String, string> props;

    public OfficeDbRepository(IDictionary<string, string> props)
    {
        log.Info("Creating OfficeDBRepository");
        this.props = props;
    }

    public Office findOne(int id)
    {
        IDbConnection con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, username, password from office where id=@id";
            IDbDataParameter paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = id;
            comm.Parameters.Add(paramId);
            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    int idV = dataR.GetInt32(0);
                    string username = dataR.GetString(1);
                    string password = dataR.GetString(2);

                    Office office = new Office(idV, username, password);
                    log.InfoFormat("Exiting findOne with value {0}", office);
                    return office;

                }
            }
        }
        log.InfoFormat("Exiting findOne with value {0}", null);
        return null;
    }

    public IEnumerable<Office> findAll()
    {
        IDbConnection con = DBUtils.getConnection(props);
        IList<Office> offices = new List<Office>();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, username, password from office";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    int id = dataR.GetInt32(0);
                    string username = dataR.GetString(1);
                    string password = dataR.GetString(2);
                    Office office = new Office(id, username, password);
                    offices.Add(office);
                }
            }
        }
        return offices;
    }

    public void save(Office entity)
    {
        var con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "insert into office values (@id, @username, @password)";
            var paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = entity.id;
            comm.Parameters.Add(paramId);

            var paramUsername = comm.CreateParameter();
            paramUsername.ParameterName = "@username";
            paramUsername.Value = entity.username;
            comm.Parameters.Add(paramUsername);
            
            var paramPasswd = comm.CreateParameter();
            paramPasswd.ParameterName = "@password";
            paramPasswd.Value = entity.password;
            comm.Parameters.Add(paramPasswd);

            var result = comm.ExecuteNonQuery();
            if (result == 0)
            {
                throw new RepositoryException2("No office added!");
            }
        }
    }

    public void delete(int id)
    {
        var con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "delete from office where id=@id";
            IDbDataParameter paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = id;
            comm.Parameters.Add(paramId);
            var dataR = comm.ExecuteNonQuery();
            if (dataR == 0)
                throw new RepositoryException2("No office detected");
        }
    }
}