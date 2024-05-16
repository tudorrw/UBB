using System;
using System.Collections.Generic;
using System.Configuration;
using festival.model;
using festival.database;
using festival.repository;
using log4net.Config;

namespace festival;

class MainClass
{
    static string GetConnectionStringByName(string name)
    {
        // Assume failure.
        string returnValue = null;

        // Look for the name in the connectionStrings section.
        ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

        // If found, return the connection string.
        if (settings != null)
            returnValue = settings.ConnectionString;

        return returnValue;
    }

    
    public static void Main(string[] args)
    {
        XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
        Console.WriteLine("Configuration Settings for festival with mySQL{0}",GetConnectionStringByName("music-festivals"));
        IDictionary<String, string> props = new SortedList<String, String>();
        props.Add("ConnectionString", GetConnectionStringByName("music-festivals"));
        testRepoForFestivals(props);
        var concurrency = new LostUpdateConcurrency(props);
        concurrency.run();
    }


    
    
    static void testRepoForFestivals(IDictionary<String, string> props)
    {
        FestivalRepository festivalRepository = new FestivalRepository(props);
        Console.WriteLine("toate spectacolele");
        foreach (Festival f in festivalRepository.findAll())
        {
            Console.WriteLine(f);
        }

        Festival f1 = new Festival("Bloodstock", "UK", 25, "2023-08-10", "2023-08-13", "info@bloodstock.uk");
        Festival f2 = new Festival(6, "Download", "United Kingdom", 21, "2023-06-09", "2023-06-11", "info@downloadfestival.co.uk");
        // festivalRepository.save(f1);
        // festivalRepository.save(f2);
        // Console.WriteLine(festivalRepository.findOne(3));
        // festivalRepository.delete(5);
    }
    
}
