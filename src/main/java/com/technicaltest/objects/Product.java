package com.technicaltest.objects;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Holds the details about each product
 *
 */
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ")
	@SequenceGenerator(sequenceName = "product_id_seq", allocationSize = 1, name = "PRODUCT_SEQ")
	private Long id;

	@Column(name = "exchange_code")
	@Size(max = 4)
	private String exchangeCode;

	@Column(name = "product_group_code")
	@Size(max = 2)
	private String productGroupCode;

	@Column(name = "symbol")
	@Size(max = 6)
	private String symbol;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	public Long getId() {
		return id;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (exchangeCode == null ? 0 : exchangeCode.hashCode());
		hash = 31 * hash + (productGroupCode == null ? 0 : productGroupCode.hashCode());
		hash = 31 * hash + (symbol == null ? 0 : symbol.hashCode());
		hash = 31 * hash + (expirationDate == null ? 0 : expirationDate.hashCode());
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
		Product product = (Product) obj;
		return exchangeCode.equals(product.exchangeCode) && productGroupCode.equals(product.productGroupCode)
				&& symbol.equals(product.symbol) && expirationDate.equals(product.expirationDate);
	}

	@Override
	public String toString() {
		return exchangeCode + "-" + productGroupCode + "-" + symbol + "(expiry: " + expirationDate + ")";
	}
	
	

}
