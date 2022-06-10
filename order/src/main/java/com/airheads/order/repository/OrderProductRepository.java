package com.airheads.order.repository;

import com.airheads.order.entity.OrderProduct;
import com.airheads.order.entity.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
