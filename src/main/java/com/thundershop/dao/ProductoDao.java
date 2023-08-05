package com.thundershop.dao;

import com.thundershop.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoDao extends JpaRepository <Producto, Long>{
    //Ejemplo de un metodo que hace una consulta ampliada de JPA
    public List<Producto>findByPrecioBetweenOrderByDescripcion(double precioI, double precioS);
    
    //Ejemplo de un metodo que hace una consulta ampliada mediante JPQL
    @Query(value="SELECT a FROM Producto a WHERE a.precio BETWEEN :precioInf AND :precioSup ORDER BY a.descripcion ASC")
    public List<Producto>consultaConJPQL(double precioInf, double precioSup);
    
    //Ejemplo de un metodo que hace una consulta ampliada mediante SQL
    @Query(nativeQuery=true,value="SELECT * FROM producto a WHERE a.precio BETWEEN :precioInf AND :precioSup ORDER BY a.descripcion ASC")
    public List<Producto>consultaConSQL(double precioInf, double precioSup);
}
