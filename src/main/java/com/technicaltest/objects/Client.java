package com.technicaltest.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Holds the details of each client
 *
 */
@Entity
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQ")
	@SequenceGenerator(sequenceName = "client_id_seq", allocationSize = 1, name = "CLIENT_SEQ")
	private Long id;

	@Column(name = "client_type")
	@Size(max = 4)
	private String clientType;

	@Column(name = "client_number")
	@Size(min = 4, max = 4)
	private String clientNumber;

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Long getId() {
		return id;
	}

	public String getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	/**
	 * 
	 * Overridden hashcode method to use client type and client number
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (clientType == null ? 0 : clientType.hashCode());
		hash = 31 * hash + (clientNumber == null ? 0 : clientNumber.hashCode());
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
		Client client = (Client) obj;
		return clientType.equals(client.clientType) && clientNumber.equals(client.clientNumber);
	}

	@Override
	public String toString() {
		return clientNumber + "(" + clientType + ")";
	}

}
