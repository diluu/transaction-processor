package com.technicaltest.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * Holds the details of each client account
 *
 */
@Entity
@Table(name="client_account")
public class ClientAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_ACC_SEQ")
	@SequenceGenerator(sequenceName = "client_account_id_seq", allocationSize = 1, name="CLIENT_ACC_SEQ")
	private Long id;
	
	@Column(name="account_number")
	@Size(min = 4, max = 4)
	private String accountNumber;
	
	@Column(name="sub_account_number")
	@Size(min = 4, max = 4)
	private String subAccountNumber;
	
	@ManyToOne
	@JoinColumn(name="client_id", nullable = false)
	private Client client;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSubAccountNumber() {
		return subAccountNumber;
	}

	public void setSubAccountNumber(String subAccountNumber) {
		this.subAccountNumber = subAccountNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Long getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (client == null ? 0 : client.hashCode());
		hash = 31 * hash + (accountNumber == null ? 0 : accountNumber.hashCode());
		hash = 31 * hash + (subAccountNumber == null ? 0 : subAccountNumber.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass())
			return false;
		ClientAccount ca = (ClientAccount) obj;
		return client.equals(ca.client) && accountNumber.equals(ca.accountNumber) && subAccountNumber.equals(ca.subAccountNumber);
	}

	@Override
	public String toString() {
		return client + "account:" +accountNumber+ "-" + subAccountNumber;
	}
	
	


}
