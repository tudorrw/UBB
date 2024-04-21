namespace festival.model;

public class FestivalSponsor
{
    public Festival Festival { get; set; }
    public Sponsor Sponsor { get; set; }
    public long amountInvested { get; set; }
}