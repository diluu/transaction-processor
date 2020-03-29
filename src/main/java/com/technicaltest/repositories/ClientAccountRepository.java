package com.technicaltest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.technicaltest.objects.Client;
import com.technicaltest.objects.ClientAccount;

public interface ClientAccountRepository extends CrudRepository<ClientAccount, Long> {

	List<ClientAccount> findByClientAndAccountNumberAndSubAccountNumber(Client client, String accountNumber,
			String subAccountNumber);

}
