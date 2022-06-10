package com.airheads.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "orders")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="orderProducts")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy") private LocalDate dateCreated;

    private String status;

    @OneToMany(mappedBy = "pk.order")
    @Valid
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<OrderProduct> orderProducts = getOrderProducts();
        for (OrderProduct op : orderProducts) {
            sum += op.getTotalPrice();
        }

        return sum;
    }

    @Transient
    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }
}
