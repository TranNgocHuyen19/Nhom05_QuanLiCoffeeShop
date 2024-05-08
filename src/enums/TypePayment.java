package enums;

public enum TypePayment {
    CREDIT_CARD("Ngân Hàng"),
    CASH("Tiền Mặt"),
    E_WALLET("Ví Điện Tử");
    
    private String type;

    private TypePayment(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }  
}
