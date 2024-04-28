using System;
using festival.model;
using festival.database;
using System.Collections.Generic;
using System.Data;
using log4net;
namespace festival.repository;

public class SpectacolDbRepository :ISpectacolRepository
{
    private static readonly ILog log = LogManager.GetLogger("SpectacolDBRepository");

    private IDictionary<String, string> props;
    
    public SpectacolDbRepository(IDictionary<String, string> props)
    {
        log.Info("Creating SpectacolDBRepository ");
        this.props = props;
    }

    public Spectacol findOne(int id)
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

    public IEnumerable<Spectacol> findAll()
    {
        IDbConnection con = DBUtils.getConnection(props);
        IList<Spectacol> spectacole = new List<Spectacol>();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, artist_name, location, data, available_seats, sold_seats from spectacol";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    int id = dataR.GetInt32(0);
                    string artistName = dataR.GetString(1);
                    string location = dataR.GetString(2);
                    DateTime data = dataR.GetDateTime(3);
                    int availableSeats = dataR.GetInt32(4);
                    int soldSeats = dataR.GetInt32(5);
                    Spectacol spectacol = new Spectacol(id, artistName, location, data, availableSeats, soldSeats);
                    spectacole.Add(spectacol);
                }
            }
        }

        return spectacole;
    }

    public Boolean findByArtistAndData(Spectacol entity)
    {
        var con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select count(*) from spectacol where artist_name = @artist_name and data = @data";
            var paramArtistName = comm.CreateParameter();
            paramArtistName.ParameterName = "@artist_name";
            paramArtistName.Value = entity.artistName;
            comm.Parameters.Add(paramArtistName);

            var paramData = comm.CreateParameter();
            paramData.ParameterName = "@data";
            paramData.Value = entity.data;
            comm.Parameters.Add(paramData);
            Console.WriteLine("ddd");
            int count = Convert.ToInt32(comm.ExecuteScalar());
            return count > 0;
        }
    }

    
    public void save(Spectacol entity)
    {
        if (!findByArtistAndData(entity))
        {
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "insert into spectacol values (@id, @artist_name, @location, @data, @available_seats, @sold_seats)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.id;
                comm.Parameters.Add(paramId);

                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@artist_name";
                paramNume.Value = entity.artistName;
                comm.Parameters.Add(paramNume);

                var paramLocation = comm.CreateParameter();
                paramLocation.ParameterName = "@location";
                paramLocation.Value = entity.location;
                comm.Parameters.Add(paramLocation);

                var paramData = comm.CreateParameter();
                paramData.ParameterName = "@data";
                paramData.Value = entity.data;
                comm.Parameters.Add(paramData);

                var paramAvailable = comm.CreateParameter();
                paramAvailable.ParameterName = "@available_seats";
                paramAvailable.Value = entity.availableSeats;
                comm.Parameters.Add(paramAvailable);

                var paramSold = comm.CreateParameter();
                paramSold.ParameterName = "@sold_seats";
                paramSold.Value = entity.soldSeats;
                comm.Parameters.Add(paramSold);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException2("No spectacol added!");
            }
        }
    }


    public void delete(int id)
    {
        IDbConnection con = DBUtils.getConnection(props);
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "delete from spectacol where id=@id";
            IDbDataParameter paramId = comm.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = id;
            comm.Parameters.Add(paramId);
            var dataR = comm.ExecuteNonQuery();
            if (dataR == 0)
                throw new RepositoryException2("No spectacol detected");
        }
    }

    public IEnumerable<Spectacol> findByDay(DateTime searchDate)
    {
        IDbConnection con = DBUtils.getConnection(props);
        IList<Spectacol> spectacole = new List<Spectacol>();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select id, artist_name, location, data, available_seats, sold_seats from spectacol";
            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    int id = dataR.GetInt32(0);
                    string artistName = dataR.GetString(1);
                    string location = dataR.GetString(2);
                    DateTime data = dataR.GetDateTime(3);
                    int availableSeats = dataR.GetInt32(4);
                    int soldSeats = dataR.GetInt32(5);
                    if (searchDate.Year == data.Year && searchDate.Month == data.Month &&
                        searchDate.Day == data.Day)
                    {
                        Spectacol spectacol = new Spectacol(id, artistName, location, data, availableSeats, soldSeats);
                        spectacole.Add(spectacol);
                    }

                }
            }
        }
        return spectacole;
    }
}