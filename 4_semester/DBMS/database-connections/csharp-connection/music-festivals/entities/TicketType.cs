namespace festival.model;

public class TicketType
{
    public int id { get; set; }
    public string name { get; set; }

    public TicketType(int id, string name)
    {
        this.id = id;
        this.name = name;
    }
}