package com.thundershop.controller;

import com.thundershop.service.ReporteService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reportes")
public class ReporteController {
   
    @Autowired
    ReporteService reporteService;
    
    @GetMapping("/principal")
    public String listado(Model model){
        
        return "/reportes/principal";
    }
    @GetMapping("/usuariosp")
    public ResponseEntity<Resource> ReporteUsuarios(@RequestParam String tipo) throws IOException{
        var reporte="usuariosp";
        return reporteService.generaReporte(reporte,null,tipo);
    }
    @GetMapping("/ventasp")
    public ResponseEntity<Resource> ReporteVentas(@RequestParam String tipo) throws IOException{
        var reporte="ventasp";
        return reporteService.generaReporte(reporte,null,tipo);
    }
    
    @GetMapping("/Productos")
    public ResponseEntity<Resource> ReporteProductos(@RequestParam String tipo) throws IOException{
        var reporte="Productos";
        return reporteService.generaReporte(reporte,null,tipo);
    }
}
