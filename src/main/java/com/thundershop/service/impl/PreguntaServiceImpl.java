package com.thundershop.service.impl;

import com.thundershop.dao.PreguntaDao;
import com.thundershop.domain.Pregunta;
import com.thundershop.service.PreguntaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreguntaServiceImpl implements PreguntaService{

    @Autowired
    private PreguntaDao preguntaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> getPreguntas(boolean activos) {
        var lista = preguntaDao.findAll();
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Pregunta getPregunta(Pregunta pregunta) {
        return preguntaDao.findById(pregunta.getIdPregunta()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Pregunta pregunta) {
        preguntaDao.save(pregunta);
    }

    @Override
    @Transactional
    public void delete(Pregunta pregunta) {
        preguntaDao.delete(pregunta);
    }
    
}
