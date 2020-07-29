package com.mohamed.orders.repositories;

import com.mohamed.orders.entities.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface OrdersRepository extends CrudRepository<Orders,Long> {
  Optional<Orders>findByCreateOrder(Date date);
}
