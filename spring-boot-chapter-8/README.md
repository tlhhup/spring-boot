#Spring Boot 企业级开发

###Spring Security
Spring Security是专门针对基于Spring项目的安全框架，充分利用依赖注入和AOP来实现安全的功能。<br>
在安全框架中有两个重要的概念，即认证(Authentication)和授权(Authorities)。认证即确认用户可以访问当前系统；授权即确定用户在当前系统下所拥有的功能权限

1. Spring Security的配置
	1. 注册DelegatingFilterProxy
		1. 第一种方式：定义WebInitializer实现WebApplicationInitializer接口，并将DelegatingFilterProxy注入到WebApplicationInitializer中，即Servlet容器中。
		2. 第二种方式：在Spring Security中提供了一个抽象类AbstractSecurityWebApplicationInitializer,在该抽象类中已经注册好了DelegatingFilterProxy，所以WebInitializer只需继承该类即可
	3. 配置Spring Security：认证和授权信息
		1. 在配置类添加@EnableWebSecurity注解，并且该配置类继承至WebSecurityConfigureAdapter类来定制授权和认证信息
		2. 配置认证信息：定义用户的数据来源，提供用户信息及该用户具有的角色信息

			通过重写WebSecurityConfigureAdapter的configure(AuthenticationManagerBuilder auth)进行配置
			1. 内存中的用户
				1. 通过AuthenticationManagerBuilder的inMemoryAuthentication方法在内存中添加用户及该用户具有的角色信息
			2. JDBC中的用户
				1. 通过AuthenticationManagerBuilder的jdbcAuthentication方法来设置基于数据源的用户及角色信息
			3. 自定义用户数据来源
				1. 通过实现UserDetailsService接口来提供用户的数据源
				2. 配置自定义的UserDetailsService的Bean对象
				2. 通过AuthenticationManagerBuilder的userDetailsService方法注入自定义的用户数据源
		3. 配置授权信息：Spring Security中通过**角色来确认用户的具有的权限**，定义访问何种资源应该如何来处理

			通过重写WebSecurityConfigureAdapter的configure(HttpSecurity http)方法进行配置
			
			1. 资源的匹配方式
				1. antMatchers：使用Ant风格的路径匹配
				2. regexMatchers：使用正则表达式匹配路径
				3. anyRequest：匹配所有的请求
			2. 请求资源相应的安全处理策略

				
		4. 定义登录行为：及配置登录、退出请求的处理策略
			
			通过重写WebSecurityConfigureAdapter的configure(HttpSecurity http)方法进行配置
2. Spring Boot的集成
	1. 自动配置：Spring Boot通过SecurityAutoConfiguration类对Spring Security进行了自动的配置，通过对application.properties文件中前缀为security的属性来配置Spring security的信息。其自动配置了如下信息：
		1. 自动在内存中添加了一个账号为user的用户
		2. 忽略`/css/**、/js/**,/images/**和**/favicon.icon`等静态文件的拦截
		3. 自动配置securityFilterChainRegistration的Bean，通过SecurityFilterAutoConfiguration类进行自动配置
	2. 自定义配置：通过定义配置类并继承WebSecurityConfigurerAdapter即可自定义Spring Security的配置 