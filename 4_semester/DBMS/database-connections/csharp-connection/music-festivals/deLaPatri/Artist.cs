namespace festival.model;

public class Artist : AEntity
{
    private int nume { get; set; }
    
    public Artist(int id, int nume) : base(id)
    {
        this.nume = nume;
    }
}