package dh.meli.projeto_integrador.enumClass;

/**
 * Class of type Enum for set the cart status as "OPEN" or "FINISHED"
 * @author Gabriela Azevedo
 * @version 0.0.1
 * @see java.lang.Object
 */
public enum PurchaseOrderStatusEnum {
    OPEN("Open"), FINISHED("Finished"); // enums são constantes

    String statusCart;

    /**
     * Constructor that receives the String status to set the statusCart attribute
     * @param status String
     */
    PurchaseOrderStatusEnum(String status) {
        this.statusCart = status;
    }
}
