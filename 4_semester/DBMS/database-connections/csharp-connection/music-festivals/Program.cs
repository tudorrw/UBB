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
        // Console.WriteLine("Configuration Settings for festival {0}",GetConnectionStringByName("festival"));
        // IDictionary<String, string> props = new SortedList<String, String>();
        // props.Add("ConnectionString", GetConnectionStringByName("festivalDB"));
        //
        // testRepoForSpectacole(props);
        // testRepoForOffice(props);
        // testRepoForBilet(props);
        
        Console.WriteLine("Configuration Settings for festival with mySQL{0}",GetConnectionStringByName("music-festivals"));
        IDictionary<String, string> props = new SortedList<String, String>();
        props.Add("ConnectionString", GetConnectionStringByName("music-festivals"));
        testRepoForFestivals(props);
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
        festivalRepository.save(f1);
        // festivalRepository.save(f2);
        // Console.WriteLine(festivalRepository.findOne(3));
        // festivalRepository.delete(5);
    }
    
    
    
    
    static void testRepoForSpectacole(IDictionary<String, string> props)
    {
        SpectacolDbRepository spectacolRepo = new SpectacolDbRepository(props);
        Spectacol s1 = new Spectacol(1, "Lana del Ray", "Cluj-Napoca", new DateTime(2023, 10, 6, 18, 30, 0), 20000, 15670);
        Spectacol s2 = new Spectacol(2, "Metallica", "Bucharest", new DateTime(2019, 8, 8, 20, 15, 0), 45000, 42000);
        Spectacol s3 = new Spectacol(3, "Metallica", "London", new DateTime(2019, 8, 8, 20, 15, 0), 30000, 8900);

        // spectacolRepo.save(s1);
        // spectacolRepo.save(s2);
        spectacolRepo.save(s3);
        Console.WriteLine(spectacolRepo.findOne(1));
        Console.WriteLine("toate spectacolele");
        foreach (Spectacol s in spectacolRepo.findAll())
        {
            Console.WriteLine(s);
        }
        Console.WriteLine("toate spectacolele cu o anumita data");

        foreach (Spectacol s in spectacolRepo.findByDay(new DateTime(2023, 10, 6)))
        {
            Console.WriteLine(s.getSpectacolBySelectedAttr());
        }
        
    }

    static void testRepoForOffice(IDictionary<String, string> props)
    {
        OfficeDbRepository officeRepo = new OfficeDbRepository(props);
        Office o1 = new Office(1, "username1", "123");
        Office o2 = new Office( 2, "username2", "345");
        Office o3 = new Office(3, "username3", "567");
        // officeRepo.save(o1);
        // officeRepo.save(o2);
        // officeRepo.save(o3);
        // officeRepo.delete(0);
        Console.WriteLine(officeRepo.findOne(1));
        foreach (Office o in officeRepo.findAll())
        {
            Console.WriteLine(o);                
        }

    }

    static void testRepoForBilet(IDictionary<String, string> props)
    {
        Spectacol s1 = new Spectacol(1, "Lana del Ray", "Cluj-Napoca", new DateTime(2023, 10, 6, 18, 30, 0), 20000, 15670);
        Spectacol s2 = new Spectacol(2, "Metallica", "Bucharest", new DateTime(2019, 8, 8, 20, 15, 0), 45000, 42000);
        
        BiletDbRepository biletRepo = new BiletDbRepository(props);
        Bilet b1 = new Bilet(1, "cumparator1", 3, s1);
        Bilet b2 = new Bilet(2, "cumparator2", 3, s1);
        // biletRepo.save(b1);
        // biletRepo.save(b2);
        foreach (Bilet b in biletRepo.findAll())
        {
            Console.WriteLine(b);                
        }
    }
}
