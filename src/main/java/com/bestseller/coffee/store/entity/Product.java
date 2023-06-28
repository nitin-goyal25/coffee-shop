package com.bestseller.coffee.store.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String cost;

	@Builder.Default
	@Column(name = "created_by")
	private String createdBy = "admin";

	@Builder.Default
	@Column(name = "created_on")
	private Date createdOn = new Date();

	@Builder.Default
	@Column(name = "modified_by")
	private String modifiedBy = "admin";

	@Builder.Default
	@Column(name = "modified_on")
	private Date modifiedOn = new Date();

	@Builder.Default
	@Column(name = "is_deleted")
	private String isDeleted = "N";

}
