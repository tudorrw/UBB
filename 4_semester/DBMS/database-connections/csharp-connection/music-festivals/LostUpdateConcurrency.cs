using System.Data;
using System.Data.Common;
using System.Threading;
using festival.database;
using MySqlConnector;

namespace festival;

public class LostUpdateConcurrency
{
    private IDictionary<String, string> props;

    public LostUpdateConcurrency(IDictionary<String, string> props)
    {
        Console.WriteLine("conexiuneeeeee");
        this.props = props;
    }

    private void Transaction1()
    {
        using (IDbConnection con = DBUtilsConcurrency.getConnection(props))
        {
            con.Open();
            using (var transaction = con.BeginTransaction())
            {
                try
                {
                    Console.WriteLine("begin transaction 1");
                    Console.WriteLine("update transaction 1");
                    UpdatePrice((DbTransaction)transaction, 1, 1, 200);
                    using (var command = con.CreateCommand())
                    {
                        command.CommandText = "SELECT * FROM festivalsTicketsPricesConcurrency WHERE festivalId = 1 AND ticketTypeId = 1";
                        command.Transaction = transaction;
                        using (var reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                Console.WriteLine($"Transaction 1 - Select result: FestivalId={reader.GetInt32(0)}, TicketTypeId={reader.GetInt32(1)}, Price={reader.GetInt32(2)}");
                            }
                        }
                    }
                                        
                    Console.WriteLine("commit transaction 1");
                    transaction.Commit();
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Transaction 1 failed: {ex.Message}");
                    transaction.Rollback();
                }
            }
        }
    }

    private void Transaction2()
    {
        using (IDbConnection con = DBUtilsConcurrency.getConnection(props))
        {
            con.Open();
            using (var transaction = con.BeginTransaction())
            {
                try
                {
                    Console.WriteLine("begin transaction 2");
                    Console.WriteLine("sleep transaction 2 for 5 seconds");
                    Thread.Sleep(5000);
                    Console.WriteLine("update transacrion 2");
                    UpdatePrice((DbTransaction)transaction, 1, 1, 75);
                    using (var command = con.CreateCommand())
                    {
                        command.CommandText = "SELECT * FROM festivalsTicketsPricesConcurrency WHERE festivalId = 1 AND ticketTypeId = 1";
                        command.Transaction = transaction;
                        using (var reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                Console.WriteLine($"Transaction 2 - Select result: FestivalId={reader.GetInt32(0)}, TicketTypeId={reader.GetInt32(1)}, Price={reader.GetInt32(2)}");
                            }
                        }
                    }
                    Console.WriteLine("commit transaction 2");
                    transaction.Commit();
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Transaction 2 failed: {ex.Message}");
                    transaction.Rollback();
                }
            }
        }
    }

    private void UpdatePrice(DbTransaction transaction, int festivalId, int ticketTypeId, int amount)
    {
        string updateQuery = $"UPDATE festivalsTicketsPricesConcurrency SET price = {amount} WHERE festivalId = {festivalId} AND ticketTypeId = {ticketTypeId}";
        using (var command =  CreateCommand(updateQuery, transaction.Connection, transaction))
        {
            command.ExecuteNonQuery();
        }
    }
    private DbCommand CreateCommand(string commandText, DbConnection connection, DbTransaction transaction)
    {
        var command = connection.CreateCommand();
        command.CommandText = commandText;
        command.Transaction = transaction;
        return command;
    }

    public void run()
    {
        var thread1 = new Thread(Transaction1);
        var thread2 = new Thread(Transaction2);
        thread1.Start();
        thread2.Start();

        thread1.Join();
        thread2.Join();
        
        Console.WriteLine("Both transactions completed.");

        Console.ReadLine();
    }
    
}