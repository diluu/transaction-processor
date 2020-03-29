package com.technicaltest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.technicaltest.objects.Client;

public interface ClientRepository extends CrudRepository<Client, Long>{

}
