#spring boot数据访问支持

###Spring Data Jpa
1. JDBC的自动配置
	1. spring-boot-starter-data-jpa依赖于spring-boot-starter-jdbc,而spring boot对jdbc做了一些自动配置，源码放在org.springframework.boot.autoconfigure.jdbc下。通过在application.properties文件中对前缀为spring.datasource的属性配置数据源，并且spring boot**自动开启了注解事务的支持**
	2. 数据的初始化(类路径下添加以下文件)
		1. scheme.sql：自动用来创建初始化表结构
		2. data.sql：自动用户填充表数据
3. 对JAP的自动配置
	1. spring boot对JPA的自动配置放置在org.springframework.boot.autoconfigure.orm.jpa下，并且spring boot默认JPA的实现者是Hibernate，通过在application.properties文件中队前缀为spring.jpa的属性进行配置
	2. 通过查看JpaBaseConfiguration的源码，spring boot默认提供了transactionManager、jpaVendorAdapter和entityManagerFactory等bean。JpaBaseConfiguration通过getPackagesToScan自动扫描添加了@Entity的实体类
	3. 在web项目中spring boot自动配置了OpenEntityManagerInViewInterceptor的bean并注册到spring mvc的拦截器中处理opensessioninview的问题
4. 对spring data jpa的自动配置
	1. spring boot对spring data jpa的自动配置放置在org.springframework.boot.autoconfigure.data.jpa下，spring boot自动开启了对spring data jpa的支持，即无须再配置类显示声明@EnableJpaRepositories
	2. 在spring boot下使用spring data jpa只需添加spring-boot-starter-data-jpa的依赖，并配置数据源、实体类和数据访问层即可,其**默认使用Hibernate的Jpa支持**，使用Hql查询
	3. CURD(注意都是**Hql**)
		1. 更新：通过@Modifying和@Query注解组合来实现更新查询

				@Modifying
				@Query("update User set userName=:userName,address=:address,sex=:sex where id=:id")
				int updateUser(User user);
		2. 查询：
			1. 通过属性名查询：通过组合一些关键字现实：findBy、Like、And等。其中findBy可以用find、read、readBy、query、queryBy、get、getBy来替换。
			
				![](http://i.imgur.com/QzWWJzu.jpg)
			2. 限制查询结果：通过top和first关键字来实现
			3. 在接口方法上添加@Query注解来实现查询
				1. 使用参数索引

						@Query("select * from User where name=?1")
						List<User> findByName(String name)
				2. 使用命名参数

						@Query("select * from User where name=:name")
						List<User> findByName(@Param("name") String name)
3. JPA相关注解说明
	1. @Entity：指明该实体类和数据库表的映射关系
	2. @ID：指明该属性映射数据库表的主键
	3. @GeneratedValue：默认使用注解生成方式为自增
	4. @Colum：表示普通属性，不添加该注解时hibernate会自动根据属性名生成数据表的字段名，多字母采用"_"隔开，如：属性为userName会自动映射成user_name。表名的映射规则也是如此
	5. @Table：实体类映射表名
6. 自定义Repository
	1. 定义Specification：用于构造准则查询Criteria
		1. 该为接口，只需定义方法返回Specification接口对象即可，在该接口中定义一个toPredicate方法用来构造查询条件
		2. toPredicate方法的方法参数中Root可以用来获取查询的属性，通过CriteriaBuilder来构造查询条件
	3. 自定义Repository
		
		Spring Data提供了和CrudRepository、PagingAndSortingRepository;Spring Data JapRepository。
		1. 定义自定义的Repository接口继承Spring Data提供的原始的Repository接口，并通过@NoRepositoryBean注解标识该接口非领域类的接口而为Repository的基础设施接口
		2. 定义接口的实现类并通过构造函数注入领域类所对应的Class对象和用于数据操作的EntityManager对象
		3. 自定义RepositoryFactoryBean。
			1. 自定义类继承JpaRepositoryFactoryBean，重写createRepositoryFactory方法用于注入自定义的RepositoryFactory对象，替代默认的RepositoryFactoryBean。
			2. 通过继承自JpaRepositoryFactory类用于自定义RepositoryFactory对象并重写其getTargetRepository将自定义的Repository注入Factory对象中。同时重写getRepositoryBaseClass方法指定自定义的Repository的实现类
		4. 开启自定义支持使用@EnableJpaRepositioning注解来指定自定义的RepositoryFactoryBean对象
##
###Spring Data Rest
1. Spring Data JPA是基于Spring Data的repository之上，可以将repository自动输出为REST资源(REST是设计风格而不是标准)，此时**REST的资源不能通过浏览器直接访问**。
2. Spring MVC中配置实用Spring Data Rest

	Spring Data REST的配置定义在RepositoryRestMvcConfiguration配置类中已经配置好了，使用时只需要继承此类或者通过@import注解导入该类的配置即可

		//方法一：继承RepositoryRestMvcConfiguration类
		public class MyRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration{

		}
		//方法二：使用@import导入
		@import(RepositoryRestMvcConfiguration.class)
		public class MyRepositoryRestMvcConfiguration{

		}
3. 在Spring Boot中集成Spring Data REST(自动配置)

	在Spring Boot中已经通过RepositoryRestMvcAutoConfiguration进行了自动的配置，如需修改只需要在application.properties文件通过前缀为spring.data.rest的属性进行配置即可。而是用也只需引入spring-boot-starter-data-rest依赖即可。
##
###声明式事务
Spring的事务机制提供了PlatformTransactionManger接口，不同的数据库访问计算的事务使用不同的接口实现。

![](http://i.imgur.com/Aq5Yaej.jpg)

1. @Transaction注解，加在类上表示该类中所有的public的方法都将在事务中运行 
##
###数据缓存
1. Spring缓存支持

	Spring定义了CacheManager和Cache接口来统一处理缓存技术，CacheManger是Spring提供的各种缓存技术的抽象接口，Cache接口处理各种缓存操作。
	1. Spring支持的CacheManager

		<table>
			<tr>
				<td>CacheManger</td>
				<td>描述</td>
			</tr>
			<tr>
				<td>SimpleCacheManager</td>
				<td>使用简单的Collection来存储缓存，主要用来测试</td>
			</tr>
			<tr>
				<td>ConcurrentMapCacheManager</td>
				<td>使用ConcurrentMap来存储缓存</td>
			</tr>
			<tr>
				<td>NoOpCacheManager</td>
				<td>仅测试用途，不会实际缓存技术</td>
			</tr>
			<tr>
				<td>EhCacheCacheManager</td>
				<td>使用EhCache作为缓存技术</td>
			</tr>
			<tr>
				<td>GuavaCacheManager</td>
				<td>使用Google Guava的GuavaCache作为缓存技术</td>
			</tr>
			<tr>
				<td>HazelcastCacheManager</td>
				<td>使用Hazelcast作为缓存技术</td>
			</tr>
			<tr>
				<td>JCacheCacheManager</td>
				<td>使用JCache(JSR-107)标准的实现作为缓存技术，如Apache Commons JCS</td>
			</tr>
			<tr>
				<td>RedisCacheManager</td>
				<td>使用Redis作为缓存技术</td>
			</tr>
		</table>
	2. 使用
		1. 在Spring中注册对应的CacheManager对象Bean，及相应的配置文件

				@Bean
				public EhCacheCacheManager cacheManager(CacheManager ehCacheCacheManager) {
					return new EhCacheCacheManager(ehCacheCacheManager);
				}
		2. 声明式缓存注解
			1. @Cacheable：在方法执行前Spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；若没有数据，调用方法并将方法的返回值放进缓存
			2. @CachePut：无论怎样，都会将方法的返回值放入缓存中。其属性和@Cacheable一致
			3. @CacheEvict：将一条或多条数据从缓存中删除
			4. @Caching：可以通过@Caching注解组合多个注解策略在一个方法上
		5. 开启声明式缓存支持：在spring的配置类添加@EnableCaching注解即可

				@Configuration
				@EnableCaching
				public class MyConfig{
				
				}
		
2. Sprint Boot

	在spring中使用缓存技术的关键是配置CacheManager，而Spring Boot已经自动配置了多个CacheManager。其自动配置主要通过autoconfigure.cache包来实现，通过在application.properties文件中的属性前缀为spring.cache的属性来配置缓存信息。
	1. 在不做任何额外配置的情况下，默认使用SimpleCacheConfiguration，即使用ConcurrentMapCacheManager来处理缓存
	2. 使用
		1. 导入相关缓存技术的依赖包，并映入相应的配置文件
		2. 在Sprint Boot的配置类添加@EnableCaching注解来开启缓存支持