package com.rabbit.productor.service;

import java.util.List;

import com.rabbit.productor.model.SignosVitales;

public interface ConsumidorService {
     void recibirSignosVitales(List<SignosVitales> signosVitales);
}
