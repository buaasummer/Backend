package com.summer.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.summer.demo.Entity.Application;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    public Application findByApplicationId(int appid);
}
