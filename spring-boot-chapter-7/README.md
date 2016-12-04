spring boot数据访问支持

1. JDBC的自动配置
	1. spring-boot-starter-data-jpa依赖于spring-boot-starter-jdbc,而spring boot对jdbc做了一些自动配置，源码放在org.springframework.boot.autoconfigure.jdbc下。通过在application.properties文件中对前缀为spring.datasource的属性配置数据源，并且spring boot**自动开启了注解事务的支持**
	2. 数据的初始化(类路径下添加以下文件)
		1. scheme.sql：自动用来创建初始化表结构
		2. data.sql：自动用户填充表数据
3. 对JAP的自动配置
	1. spring boot对JPA的自动配置放置在org.springframework.boot.autoconfigure.orm.jpa下，并且spring boot默认JPA的实现者是Hibernate，通过在application.properties文件中队前缀为spring.jpa的属性进行配置
	2. 通过查看JpaBaseConfiguration的源码，spring boot默认提供了transactionManager、jpaVendorAdapter和entityManagerFactory等bean。
	3. 在web项目中spring boot自动配置了OpenEntityManagerInViewInterceptor的bean并注册到spring mvc的拦截器中
4. 对spring data jpa的自动配置
	1. spring boot对spring data jpa的自动配置放置在org.springframework.boot.autoconfigure.data.jpa下，spring boot自动开启了对spring data jpa的支持，即无须再配置类显示声明@EnableJpaRepositories
	2. 在spring boot下使用spring data jpa只需添加spring-boot-starter-data-jpa的依赖，并配置数据源、实体类和数据访问层即可
3. JPA相关注解说明
	1. @Entity：指明该实体类和数据库表的映射关系
	2. @ID：指明该属性映射数据库表的主键
	3. @GeneratedValue：默认使用注解生成方式为自增
	4. @Colum：表示普通属性，不添加该注解时hibernate会自动根据属性名生成数据表的字段名，多字母采用"_"隔开，如：属性为userName会自动映射成user_name。表名的映射规则也是如此
	5. @Table：实体类映射表名