package com.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Promotion {
    private String nomeItem;
    private int quantidadeNecessaria;
    private int descontoEmCentavos;
}
