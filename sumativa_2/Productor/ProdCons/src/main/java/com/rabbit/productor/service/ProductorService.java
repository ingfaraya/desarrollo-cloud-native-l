package com.rabbit.productor.service;

import java.util.List;

import com.rabbit.productor.model.SignosVitales;

public interface ProductorService {
    void enviarSignosVitales(List<SignosVitales> signosVitales);
}
