package com.tlh.springboot.repository.custom;

import static com.google.common.collect.Iterables.toArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;

public class CustomerSpecs {

	@SuppressWarnings("unchecked")
	public static <T> Specification<T> byAuto(EntityManager entityManager, T entity) {
		Class<T> clazz = (Class<T>) entity.getClass();

		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				EntityType<T> type = entityManager.getMetamodel().entity(clazz);
				// 获取该实体中声明的所有属性
				for (Attribute<T, ?> attribute : type.getDeclaredAttributes()) {
					// 通过反射得到该属性的值
					Object value = ReflectionUtils.getField((Field) attribute.getJavaMember(), entity);
					if (value != null) {
						// 判断该属性是否是字符串类型的数据
						if (attribute.getJavaType() == String.class) {
							predicates.add(cb.like(
									root.get(type.getDeclaredSingularAttribute(attribute.getName(), String.class)),
									"%" + value + "%"));
						} else {
							predicates.add(cb.equal(root.get(
									type.getDeclaredSingularAttribute(attribute.getName(), attribute.getJavaType())),
									value));
						}
					}
				}
				return predicates.isEmpty()?cb.conjunction():cb.and(toArray(predicates, Predicate.class));
			}

		};
	}

}
