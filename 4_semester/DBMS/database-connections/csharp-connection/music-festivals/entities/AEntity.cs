namespace festival.model;
using System.Xml.Serialization;

public abstract class AEntity
{
    [XmlAttribute]
    public int id { get; set; }

    public AEntity()
    {
    }

    public AEntity(int id)
    {
        this.id = id;
    }

}