/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

public enum ShiftEmployee {
    MORNING("Ca Sáng"),
    AFTERNOON("Ca Chiêu"),
    EVENING("Ca Tối");
    
    private String shift;

    private ShiftEmployee(String shift) {
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }
    
}
