namespace festival.model;

public class Festival
{
    public int id { get; set; }
    public string name { get; set; }
    public string location { get; set; }
    public int edition { get; set; }
    public DateTime startDate { get; set; }
    public DateTime endDate { get; set; }

    public string emailContact { get; set; }


    public Festival(int id, string name, string location, int edition, DateTime startDate, DateTime endDate, string emailContact)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.edition = edition;
        this.startDate = startDate.Date;
        this.endDate = endDate.Date;
        this.emailContact = emailContact;
    }

    public Festival(int id, string name, string location, int edition, string startDate, string endDate, string emailContact)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.edition = edition;
        this.startDate = DateTime.Parse(startDate).Date; 
        this.endDate = DateTime.Parse(endDate).Date;
        this.emailContact = emailContact;
    }

    public Festival(string name, string location, int edition, string startDate, string endDate, string emailContact)
    {
        this.name = name;
        this.location = location;
        this.edition = edition;
        this.startDate = DateTime.Parse(startDate).Date;
        this.endDate = DateTime.Parse(endDate).Date;
        this.emailContact = emailContact;
    }

    public override string ToString()
    {
        return $"Festival{{name={name}, location={location}, edition={edition}, startDate={startDate}, endDate={endDate}, emailContact={emailContact}}}" ;
    }
}
