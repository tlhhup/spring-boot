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
1. Spring Data JPA是基于Spring Data的repository之上，可以将repository自动输出为REST资源(REST是设计风格而不是标准)。
2. Spring MVC中配置实用Spring Data Rest

	Spring Data REST的配置定义在RepositoryRestMvcConfiguration配置类中已经配置好了，使用时只需要继承此类或者通过@import注解导入该类的配置即可

		//方法一：继承RepositoryRestMvcConfiguration类
		public class MyRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration{

		}
		//方法二：使用@import导入
		@import(RepositoryRestMvcConfiguration.class)
		public class MyRepositoryRestMvcConfiguration{

		}
3. 在Spring Boot中集成Spring Data REST

	在Spring Boot中已经通过RepositoryRestMvcAutoConfiguration进行了自动的配置，如需修改只需要在application.properties文件通过前缀为spring.data.rest的属性进行配置即可。而是用也只需引入spring-boot-starter-data-rest依赖即可。