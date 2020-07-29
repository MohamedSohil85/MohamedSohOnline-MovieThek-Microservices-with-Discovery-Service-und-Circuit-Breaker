package com.mohamed.orders.controllers;

import com.mohamed.orders.entities.Orders;
import com.mohamed.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
public class OrdersController {
    final OrdersRepository ordersRepository;

    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @RequestMapping(value = "/Orders-Service/{movieId}/Order/{userId}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity createOrder(@PathVariable("movieId")Long movieId, @PathVariable("userId")Long userId, @RequestBody Orders orders){
        orders.setMovieId(movieId);
        orders.setUserId(userId);
        orders.setCreateOrder(new Date());
        orders.setTotalprice(orders.getQuantity()*orders.getPrice());
        return new ResponseEntity(ordersRepository.save(orders), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/getOrdersByreleaseDate",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    ResponseEntity getOrdersByDate(@RequestParam("OrderDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Date orderDate){
        Optional<Orders>optionalOrders=ordersRepository.findByCreateOrder(orderDate);
        if(!optionalOrders.isPresent()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(optionalOrders.get(),HttpStatus.OK);
    }
}
