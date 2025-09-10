package backend2.backend.dtos;

import java.util.Map;


public class OrderDTO {

    private final Map<Integer, Integer> productIdAndQty;

    public OrderDTO(Map<Integer, Integer> productIdAndQty) {
        this.productIdAndQty = productIdAndQty;
    }

    public Map<Integer, Integer> getProductIdAndQty() {
        return productIdAndQty;
    }
}
