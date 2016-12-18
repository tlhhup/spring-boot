package com.tlh.springboot.repository.custom;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class CustomJpaRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>
		extends JpaRepositoryFactoryBean<T, S, ID> {

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		//返回自定义的RepositoryFactoryBean对象
		return new CustomJpaRepositoryBean(entityManager);
	}
	
	//自定义CustomJpaRepositoryBean
	private static class CustomJpaRepositoryBean extends JpaRepositoryFactory{

		public CustomJpaRepositoryBean(EntityManager entityManager) {
			super(entityManager);
		}
		
		@Override
		protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
				RepositoryInformation information, EntityManager entityManager) {
			//将自定义的Repository对象注入到Factory对象中
			return new CostomJpaRepositoryImpl<>(information.getDomainType(), entityManager);
		}
		
	}
	
}
