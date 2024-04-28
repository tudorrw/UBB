using System;
namespace festival.model;

public class Office : AEntity
{
    public string username { get; set; }
    public string password { get; set; }
    
    public Office(int id, string username, string password) : base(id)
    {
        this.username = username;
        this.password = password;
    }
    public Office(string username, string password)
    {
        this.username = username;
        this.password = password;
    }
    
    public bool Login(string usr, string pass)
    {
        return usr == this.username && pass == this.password;
    }

    public void Logout()
    {
        Environment.Exit(0);
    }

    public override string ToString()
    {
        return $"Office{{usermame={username}, password={password}, id={id}}}";
    }
}