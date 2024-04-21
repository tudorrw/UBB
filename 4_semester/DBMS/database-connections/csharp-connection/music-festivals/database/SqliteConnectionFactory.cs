using System;
using System.Data;
using Mono.Data.Sqlite;
using System.Collections.Generic;

namespace festival.database;

public static class SqliteConnectionFactory
{
    public static IDbConnection createConnection(IDictionary<string, string> props)
    {
        //Mono Sqlite Connection
        String connectionString = props["ConnectionString"];
        Console.WriteLine("SQLite ---Se deschide o conexiune la  ... {0}", connectionString);
        return new SqliteConnection(connectionString);
    }
}