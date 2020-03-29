package com.technicaltest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.technicaltest.objects.ClientAccount;

public interface ClientAccountRepository extends CrudRepository<ClientAccount, Long> {

}
