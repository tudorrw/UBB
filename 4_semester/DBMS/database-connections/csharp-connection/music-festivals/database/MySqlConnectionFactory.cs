using System;
using System.Data;
using System.Collections.Generic;
using MySqlConnector;

namespace festival.database;

public static class MySqlConnectionFactory
{
    public static IDbConnection createConnection(IDictionary<string,string> props)
    {
        // MySql Connection
        // String connectionString = "Database=mpp;" +
        // 							"Data Source=localhost;" +
        // 							"User id=test;" +
        // 							"Password=passtest;";
        String connectionString = props["ConnectionString"];
        Console.WriteLine("MySql ---se deschide o conexiune la  ... {0}", connectionString);
			
        return new MySqlConnection(connectionString);

	
    }
}