package enums;

public enum RoleAccount {
    MANAGER("Quản Lí"),
    EMPLOYEE("Nhân Viên");
    
    private String role;

    private RoleAccount(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    } 
}
