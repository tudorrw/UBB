namespace festival.model;

public class Bilet : AEntity
{
    public string numeCumparator { get; set; }
    public int cantitate  { get; set; }
    public Spectacol spectacol { get; set; }
    
    public Bilet(int id, string numeCumparator, int cantitate, Spectacol spectacol) : base(id)
    {
        this.numeCumparator = numeCumparator;
        this.cantitate = cantitate;
        this.spectacol = spectacol;
    }

    public override string ToString()
    {
        return $"Bilet{{nume cumparator={numeCumparator}, cantitate={cantitate}, spectacol={spectacol}, id={id}}}";
    }
}