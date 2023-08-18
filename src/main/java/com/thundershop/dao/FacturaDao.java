package com.thundershop.dao;

import com.thundershop.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaDao extends JpaRepository <Factura, Long>{
   
}
