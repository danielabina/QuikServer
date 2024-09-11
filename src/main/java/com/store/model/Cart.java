package com.store.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Cart {
    private String id = UUID.randomUUID().toString();
    private List<Item> itens = new ArrayList<>();

}
