package com.iessanvicente.rest.services;

import org.springframework.stereotype.Service;

import com.iessanvicente.rest.models.Categoria;
import com.iessanvicente.rest.repos.CategoriaRepositorio;
import com.iessanvicente.rest.services.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService extends BaseService<Categoria, Long, CategoriaRepositorio> {

}
