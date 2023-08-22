package com.thundershop.dao;

import com.thundershop.domain.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaDao extends JpaRepository <Pregunta, Long>{
    
}
