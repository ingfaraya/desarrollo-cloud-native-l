package com.rabbit.productor.service;

import com.rabbit.productor.model.AlertaDTO;

public interface ProductorService {
    void sendMessage(AlertaDTO alerta);
}
