package com.iessanvicente.rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iessanvicente.rest.models.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

}
