package com.technicaltest.objects;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * Holds the details about each transaction
 *
 */
@Entity
@Table(name="transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANS_SEQ")
	@SequenceGenerator(sequenceName = "transaction_id_seq", allocationSize = 1, name="TRANS_SEQ")
	private Long id;
	
	@Column(name="transacton_date")
	private LocalDate transactionDate;
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="client_id", nullable = false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="client_account_id", nullable = false)
	private ClientAccount clientAccount;
	
	@Column(name="amount")
	private Long transactionAmount;
	
	

	public Long getId() {
		return id;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	

	public Long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public ClientAccount getClientAccount() {
		return clientAccount;
	}

	public void setClientAccount(ClientAccount clientAccount) {
		this.clientAccount = clientAccount;
	}
	
	

}
