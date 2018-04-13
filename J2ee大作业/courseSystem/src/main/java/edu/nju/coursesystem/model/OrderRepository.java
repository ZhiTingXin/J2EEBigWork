package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByOrderid(Integer orderId);
    List<Order> findByCourseName(String courseName);
    List<Order> findByUserid(String mail);
    List<Order> findByUseridAndOrderState(String mail,String orderState);
    List<Order> findByOrderState(String orderState);
    List<Order> findByOrderStateAndCourseName(String orderState,String courseName);
}
