using festival.model;
using festival.database;
using System.Data;
namespace festival.repository;

public class FestivalRepository:IFestivalRepository
{
    private IDictionary<String, string> props;

    public FestivalRepository(IDictionary<string, string> props)
    {
        this.props = props;
    }

    public Festival findOne(int id)
    {
        IDbConnection con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, name, location, edition, startDate, endDate, emailContact from festivals";
            IDbDataParameter paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = id;
            comm.Parameters.Add(paramId);
            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    int idV = dataR.GetInt32(0);
                    string name = dataR.GetString(1);
                    string location = dataR.GetString(2);
                    int edition = dataR.GetInt32(3);
                    DateTime startDate = dataR.GetDateTime(4);
                    DateTime endDate = dataR.GetDateTime(5);
                    string emailContact = dataR.GetString(6);

                    Festival festival = new Festival(id, name, location, edition, startDate, endDate, emailContact);
                    return festival;
                }
            }
        }

        return null;
    }

    public IEnumerable<Festival> findAll()
    {
        IDbConnection con = DBUtils.getConnection(props);
        IList<Festival> festivals = new List<Festival>();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, name, location, edition, startDate, endDate, emailContact from festivals";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    int id = dataR.GetInt32(0);
                    string name = dataR.GetString(1);
                    string location = dataR.GetString(2);
                    int edition = dataR.GetInt32(3);
                    DateTime startDate = dataR.GetDateTime(4);
                    DateTime endDate = dataR.GetDateTime(5);
                    string emailContact = dataR.GetString(6);

                    Festival festival = new Festival(id, name, location, edition, startDate, endDate, emailContact);
                    festivals.Add(festival);
                }
            }
        }
        return festivals;
    }

    public void save(Festival entity)
    {
        if (!findByNameAndEdition(entity))
        {
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "insert into festivals values (@id, @name, @location, @edition, @startDate, @endDate, @emailContact)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.id;
                comm.Parameters.Add(paramId);

                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@name";
                paramNume.Value = entity.name;
                comm.Parameters.Add(paramNume);

                var paramLocation = comm.CreateParameter();
                paramLocation.ParameterName = "@location";
                paramLocation.Value = entity.location;
                comm.Parameters.Add(paramLocation);

                var paramEdition = comm.CreateParameter();
                paramEdition.ParameterName = "@edition";
                paramEdition.Value = entity.edition;
                comm.Parameters.Add(paramEdition);
            
                var paramStartDate = comm.CreateParameter();
                paramStartDate.ParameterName = "@startDate";
                paramStartDate.Value = entity.startDate;
                comm.Parameters.Add(paramStartDate);

                var paramEndDate = comm.CreateParameter();
                paramEndDate.ParameterName = "@endDate";
                paramEndDate.Value = entity.endDate;
                comm.Parameters.Add(paramEndDate);

                var paramEmailContact = comm.CreateParameter();
                paramEmailContact.ParameterName = "@emailContact";
                paramEmailContact.Value = entity.emailContact;
                comm.Parameters.Add(paramEmailContact);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No festival added!");
            } 
        }
        else
        {
            Console.WriteLine($"Festival with name {entity.name} and edition {entity.edition} already exists");
        }
    }

    public Boolean findByNameAndEdition(Festival entity)
    { 
        var con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select count(*) from festivals where name = @name and edition = @edition";
            var paramName = comm.CreateParameter();
            paramName.ParameterName = "@name";
            paramName.Value = entity.name;
            comm.Parameters.Add(paramName);

            var paramEdition = comm.CreateParameter();
            paramEdition.ParameterName = "@edition";
            paramEdition.Value = entity.edition;
            comm.Parameters.Add(paramEdition);
            int count = Convert.ToInt32(comm.ExecuteScalar());
            return count > 0;
        }
    }


    public void delete(int id)
    {
        IDbConnection con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "delete from festivals where id=@id";
            IDbDataParameter paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = id;
            comm.Parameters.Add(paramId);
            var dataR = comm.ExecuteNonQuery();
            if (dataR == 0)
                throw new RepositoryException("No festival detected");
        }
    }
}