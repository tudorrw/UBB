namespace festival.model;

public class FestivalTicketPrice
{
    public Festival festival { get; set; }
    public TicketType ticketType { get; set; }
    public int price { get; set; }

    public FestivalTicketPrice(Festival festival, TicketType ticketType, int price)
    {
        this.festival = festival;
        this.ticketType = ticketType;
        this.price = price;
    }

    public override string ToString()
    {
        return $"TicketPrice: festivalId:{festival.id}, ticketTypeId: {ticketType.id}, price: {price}";
    }
}