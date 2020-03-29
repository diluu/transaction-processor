package com.technicaltest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.technicaltest.objects.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
	
	List<Client> findByClientTypeAndClientNumber(String clientType, String clientNumber);

}
