namespace festival.model;

public class Song
{
    public int id { get; set; }
    public Band band { get; set; }
    public string name { get; set; }
    public int duration { get; set; }
    public DateTime releaseDate { get; set; }
    public Genre genre { get; set; }
}

