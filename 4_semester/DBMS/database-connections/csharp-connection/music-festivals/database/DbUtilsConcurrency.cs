using System.Data;
using System.Collections.Generic;

namespace festival.database;

public static class DBUtilsConcurrency
{

    public static IDbConnection getConnection(IDictionary<string, string> props)
    {
        return getNewConnection(props);
    }

    private static IDbConnection getNewConnection(IDictionary<string,string> props)
    {
        return MySqlConnectionFactory.createConnection(props);
    }
    
}