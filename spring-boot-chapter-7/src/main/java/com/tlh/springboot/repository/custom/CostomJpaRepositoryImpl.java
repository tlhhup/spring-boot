package com.tlh.springboot.repository.custom;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import static com.tlh.springboot.repository.custom.CustomerSpecs.byAuto;

public class CostomJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements CustomJpaRepsitory<T, ID> {

	private EntityManager entityManager;
	
	public CostomJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager=em;
	}

	@Override
	public Page<T> findByAuto(T entity, Pageable pageable) {
		return findAll(byAuto(entityManager, entity), pageable);
	}

}
