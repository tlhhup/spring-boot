#spring boot数据访问支持

###Spring Data Jpa
1. JDBC的自动配置
	1. spring-boot-starter-data-jpa依赖于spring-boot-starter-jdbc,而spring boot对jdbc做了一些自动配置，源码放在org.springframework.boot.autoconfigure.jdbc下。通过在application.properties文件中对前缀为spring.datasource的属性配置数据源，并且spring boot**自动开启了注解事务的支持**
	2. 数据的初始化(类路径下添加以下文件)
		1. scheme.sql：自动用来创建初始化表结构
		2. data.sql：自动用户填充表数据
3. 对JAP的自动配置
	1. spring boot对JPA的自动配置放置在org.springframework.boot.autoconfigure.orm.jpa下，并且spring boot默认JPA的实现者是Hibernate，通过在application.properties文件中队前缀为spring.jpa的属性进行配置
	2. 通过查看JpaBaseConfiguration的源码，spring boot默认提供了transactionManager、jpaVendorAdapter和entityManagerFactory等bean。
	3. 在web项目中spring boot自动配置了OpenEntityManagerInViewInterceptor的bean并注册到spring mvc的拦截器中处理opensessioninview的问题
4. 对spring data jpa的自动配置
	1. spring boot对spring data jpa的自动配置放置在org.springframework.boot.autoconfigure.data.jpa下，spring boot自动开启了对spring data jpa的支持，即无须再配置类显示声明@EnableJpaRepositories
	2. 在spring boot下使用spring data jpa只需添加spring-boot-starter-data-jpa的依赖，并配置数据源、实体类和数据访问层即可
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
		3. 自定义RepositoryFactoryBean。自定义类继承JpaRepositoryFactoryBean，重写createRepositoryFactory方法用于注入自定义的RepositoryFactory对象，替代默认的RepositoryFactoryBean。通过继承自JpaRepositoryFactory类用于自定义RepositoryFactory对象并重写其getTargetRepository将自定义的Repository注入Factory对象中。
		4. 开启自定义支持使用@EnableJpaRepositioning注解来指定自定义的RepositoryFactoryBean对象
##
###Spring Data Rest