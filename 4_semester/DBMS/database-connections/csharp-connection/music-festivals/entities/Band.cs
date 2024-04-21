namespace festival.model;

public class Band
{
    public int id { get; set; }
    public string name { get; set; }
    public string country { get; set; }
    public int formingYear { get; set; }

    public Band(string name, string country, int formingYear)
    {
        this.name = name;
        this.country = country;
        this.formingYear = formingYear;
    }
}