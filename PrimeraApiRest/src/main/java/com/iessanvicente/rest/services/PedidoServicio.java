package com.iessanvicente.rest.services;

import org.springframework.stereotype.Service;

import com.iessanvicente.rest.models.Pedido;
import com.iessanvicente.rest.repos.PedidoRepositorio;
import com.iessanvicente.rest.services.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServicio extends BaseService<Pedido, Long, PedidoRepositorio> {

}
