package backend2.backend.dtos;

import java.util.Map;

public class OrderDTO {

    private final int appUserId;
    private final Map<Integer, Integer> productIdAndQty;

    public OrderDTO(int appUserId, Map<Integer, Integer> productIdAndQty) {
        this.appUserId = appUserId;
        this.productIdAndQty = productIdAndQty;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public Map<Integer, Integer> getProductIdAndQty() {
        return productIdAndQty;
    }
}
