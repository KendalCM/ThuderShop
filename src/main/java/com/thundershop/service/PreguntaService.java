package com.thundershop.service;

import com.thundershop.domain.Pregunta;
import java.util.List;

public interface PreguntaService {
    
    // Se obtiene un listado de preguntas en un List
    public List<Pregunta> getPreguntas(boolean activos);
    
   // Se obtiene un Pregunta, a partir del id de un pregunta
    public Pregunta getPregunta(Pregunta pregunta);
    
    // Se inserta un nuevo pregunta si el id del pregunta esta vacío
    // Se actualiza un pregunta si el id del pregunta NO esta vacío
    public void save(Pregunta pregunta);
    
    // Se elimina el pregunta que tiene el id pasado por parámetro
    public void delete(Pregunta pregunta);
}
