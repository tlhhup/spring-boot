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
			3. **设置密码加密策略**
				1. 在Spring Security的配置类中定义passwordEncoder的bean对象

						@Bean
						PasswordEncoder passwordEncoder(){
							return new BCryptPasswordEncoder();
						}
				2. 通过AuthenticationManagerBuilder的userDetailsService方法注入自定义的用户数据源,并注入定义的passwordEncoder对象

						//配置用户认证信息
						@Override
						protected void configure(AuthenticationManagerBuilder auth) throws Exception {
							//设置自定义用户数据源及加密方式
							auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
						}
		3. 配置授权信息：Spring Security中通过**角色来确认用户的具有的权限**，定义访问何种资源应该如何来处理

			通过重写WebSecurityConfigureAdapter的configure(HttpSecurity http)方法进行配置
			
			1. 资源的匹配方式
				1. antMatchers：使用Ant风格的路径匹配
				2. regexMatchers：使用正则表达式匹配路径
				3. anyRequest：匹配所有的请求
			2. 请求资源相应的安全处理策略

				![](http://i.imgur.com/r27RQDl.jpg)
		4. 定义登录行为：及配置登录、退出请求的处理策略
			
			通过重写WebSecurityConfigureAdapter的configure(HttpSecurity http)方法进行配置
2. Spring Boot的集成
	1. 自动配置：Spring Boot通过SecurityAutoConfiguration类对Spring Security进行了自动的配置，通过对application.properties文件中前缀为security的属性来配置Spring security的信息。其自动配置了如下信息：
		1. 自动在内存中添加了一个账号为user的用户
		2. 忽略`/css/**、/js/**,/images/**和**/favicon.icon`等静态文件的拦截
		3. 自动配置securityFilterChainRegistration的Bean，通过SecurityFilterAutoConfiguration类进行自动配置
	2. 自定义配置：通过定义配置类并继承WebSecurityConfigurerAdapter即可自定义Spring Security的配置
##
###异步消息
异步消息主要目的是为了系统与系统之间的通信。所谓异步消息即消息发送者无须等待消息接受者的处理及返回，甚至无须关心消息是否发送成功。

在异步消息中有两个很重的概念，即消息代理(message broker)和目的地(destination)。当消息发送者发送消息后，消息将由消息代理接管，最终由消息代理保证消息传递到指定的目的地。

异步消息主要有两种形式的目的地：队列(queue)和主题(topic)。队列用于点对点的消息传递；主题用户发布/订阅的消息通信。

1. 通信方式
	1. 点对点：当消息发送者发送消息，消息代理获得消息后将消息放进一个队列(queue)中，当有消息接受者来接收消息的时候，消息将从队列里取出来传递给接收者，这时消息队列将删除该消息。该方式只能保证每一条小巷只有唯一的发送者和接收者。及一对一的关系
	2. 发布/订阅：消息发送者发送消息到主题，而多个消息接受者监听这个主题。此时消息发送者和消息接收者分别叫做发布者和订阅者。及一对多的关系 
3. 常用的消息代理
	1. JMS(java message service)：基于JVM的消息代理规范。ActiveMQ、HornetQ是一个JMS消息代理的实现。
	2. AMQP(advanced message queuing protocol)：一种消息代理的规范，兼容JMS，并且支持跨平台和语音。主要实现由RabbitMQ.
3. 使用
	1. Spring的支持
		1. Spring对JMS和AMQP的支持分别来自于spring-jms和Spring-rabbit
		2. 使用
			1. 通过ConnectionFactory的实现来连接消息代理，并分别通过JmsTemplate和RabbitTemplate来发送消息
			2. Spring为JMS、AMQP提供了@JmsListner、@RabbitListener方法注解来监听消息代理发布的消息。通过提供@EnableJms和@EnableRabbit来开启支持
	2. Sprint Boot的支持
		1. 对JMS的支持：
			1. 自动配置：通过org.springframework.boot.autoconfigure.jms来实现JMS的自动配置，自动配置的内容如下
				1. ActiveMQConnectionFactoryConfiguration中配置了ActiveMQConnectionFactory的Bean来作为链接
				2. JmsAutoConfiguration中配置好了jmsTemplate来发送消息
				2. 通过application.properties文件中前缀为spring.activemq的属性设置activemq的连接属性
				3. 自动开启注解式的监听支持
			4. 使用：
				1. 添加sprint-boot-starter-hornetq、spring-jms、activemq-client
				2. 通过实现MessageCreator接口来定义发送的消息
				3. Spring Boot中提供了CommandLineRunner来用户程序启动后执行代码。
				4. 通过在方法添加@JmsListner注解来接受消息
				4. 如果不安装ActiveMQ可以通过内嵌的方式处理，只需引入activemq-broker即可
		4. 对RabbitMQ的支持：
			1. 自动配置：通过org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration来实现AMQP的自动配置，自动配置的内容如下
				1. 通过内部类RabbitConnectionFactoryCreator来自动配置ConnectionFactory的Bean
				2. rabbitTemplate的自动配置
				3. 通过application.properties文件中前缀为spring.rabbitmq的属性设置rabbitmq的连接属性
			4. 使用
				1. 添加spring-boot-starter-amqp
				2. 定义目的地：在配置类中定义目的地的Bean对象
				3. 通过在方法添加@RabbitListener注解来接受消息