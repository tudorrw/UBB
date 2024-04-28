namespace festival.model;

public class Spectacol : AEntity
{
    public string artistName { get; set; }
    public string location { get; set; }
    public DateTime data { get; set; }
    public int availableSeats { get; set; }
    public int soldSeats { get; set; }

    public Spectacol(int id, string artistName, string location, DateTime data, int availableSeats, int soldSeats) : base(id)
    {
        this.artistName = artistName;
        this.location = location;
        this.data = data;
        this.availableSeats = availableSeats;
        this.soldSeats = soldSeats;
    }
    
    public override string ToString()
    {
        return $"Spectacol{{artist_name={artistName}, location='{location}', date={data}, availableSeats={availableSeats}, soldSeats={soldSeats}, id={id}}}";
    }

    public string getSpectacolBySelectedAttr()
    {
        return $"{{Artist:{artistName}, Location: {location}, Start: {data.Hour}:{data.Minute}, seats available: {availableSeats}}}";
    }
}