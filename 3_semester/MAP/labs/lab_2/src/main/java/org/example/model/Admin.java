package org.example.model;

public class Admin extends User{
    private String adminRole;

    public Admin(int id, String username, String password, String adminRole) {
        super(id, username, password);
        this.adminRole = adminRole;
    }


    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

}
