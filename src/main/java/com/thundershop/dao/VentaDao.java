package com.thundershop.dao;

import com.thundershop.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaDao extends JpaRepository <Venta, Long>{
   
}
