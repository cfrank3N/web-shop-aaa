package backend2.backend.dtos;

import java.util.List;

public class OrderDTO {

    private final int appUserId;
    private final List<Integer> orderedProductIds;

    public OrderDTO(int appUserId, List<Integer> orderedProductIds) {
        this.appUserId = appUserId;
        this.orderedProductIds = orderedProductIds;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public List<Integer> getOrderedProductIds() {
        return orderedProductIds;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "appUserId=" + appUserId +
                ", orderedProductIds=" + orderedProductIds +
                '}';
    }
}
