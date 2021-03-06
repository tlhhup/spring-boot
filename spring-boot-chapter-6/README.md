#spring boot的web开发

##
###1.Thymeleaf

Thymeleaf是一个Java库。它是XML、XHTML、HTML5等格式的模板引擎，可以用于Web项目和非Web项目。Thymeleaf很适合作为Web应用的视图的业务逻辑层，还可以在离线环境下处理XML文件。

1. 引入Thymeleaf

		<!DOCTYPE html>
		
		<html xmlns="http://www.w3.org/1999/xhtml"
		      xmlns:th="http://www.thymeleaf.org">//命名空间
		</html>
2. 标准表达式语法
	1. `${...}` : Variable expressions.
		1. 支持OGNL表达式或spring的表达式，获取model中的属性
	1. `*{...}`: Selection expressions.
		1. 获取选择元素的指定的属性的数据

				<div th:object="${book}">
				  ...//显示book对象的title属性
				  <span th:text="*{title}">...</span>
				  ...
				</div>
	1. `#{...}`: Message (i18n) expressions.
	1. `@{...}`: Link (URL) expressions.
		1. 对字符串进行URL地址解析并自动添加上下文路径和session信息

				<a th:href="@{/order/list}">...</a>//如果部署在/myapp中则解析为
				<a href="/myapp/order/list">...</a>
				//携带数据
				<a th:href="@{/order/details(id=${orderId},type=${orderType})}">...</a>
		2. 引入静态资源(**static文件夹**)

				//引入src/main/resources目录下的static文件夹中的资源
				<link th:href="@{bootstrap/css/bootstrap.min.css}" rel="stylesheet"/>
				<script type="text/javascript" th:src="@{jquery-1.11.3.min.js}"></script>
	1. `~{...}`: Fragment expressions.
2. 数据操作
	1. 访问model中的数据

			<span th:text="${book.author.name}">
	2. model中数据的迭代

			<li th:each="book : ${books}">
	3. 数据判断

			${not #lists.isEmpty(people)}//判断people是否为空
	4. js中访问model中的数据
		1. 通过th:inline="javascript"添加到script标签，这样js代码既可以访问model中的数据
		2. 通过"[[${}]]"访问model中的数

				<script th:inline="javascript">
					var value=[[${user.name}]]
				</script>
3. 注意事项
	1. 界面上如果具有空标记则必须使用"/"结束

			<input type="text" id="name"/>	正确
			<input type="text" id="name">	错误
###2.Thymeleaf整合springmvc

	@Configuration
	@EnableWebMvc
	public class SpringMvcConfig extends WebMvcConfigurerAdapter{
	
		@Bean
		public ThymeleafViewResolver viewResolver(){
			ThymeleafViewResolver resolver=new ThymeleafViewResolver();
			resolver.setTemplateEngine(templateEngine());
			resolver.setCharacterEncoding("UTF-8");
			return resolver;
		}
		
		@Bean
		public SpringTemplateEngine templateEngine(){
			SpringTemplateEngine templateEngine=new SpringTemplateEngine();
			templateEngine.setTemplateResolver(templateResolver());
			return templateEngine;
		}
		
		@Bean
		public TemplateResolver templateResolver(){
			TemplateResolver resolver=new ServletContextTemplateResolver();
			resolver.setPrefix("/WEB-INF/templates");
			resolver.setSuffix(".html");
			resolver.setTemplateMode("HTML5");
			return resolver;
		}
		
	}
###3.Thymeleaf整合spring boot
spring boot通过org.springframework.boot.autoconfigure.thymeleaf包**对thymeleaf进行自动配置**，ThymeleafProperties用来配置Thymeleaf

	//设置默认编码
	private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

	//模板的媒体类型设置
	private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");

	//模板前缀，默认设置
	public static final String DEFAULT_PREFIX = "classpath:/templates/";

	//后缀设置，默认设置
	public static final String DEFAULT_SUFFIX = ".html";
1. 通过对application.properties文件中前缀为spring.thymeleaf的属性进行Thymeleaf的配置
2. **spring boot的spring mvc自动配置**
	1. 通过org.springframework.boot.autoconfigure.web包中的WebMvcAutoConfiguration和WebMvcProperties实现对spring mvc的自动配置
		1. 静态资源映射：对以下的资源映射为/**

				{
				"classpath:/META-INF/resources/", "classpath:/resources/",
				"classpath:/static/", "classpath:/public/" };
		2. 可以在application.properties文件中通过前缀为spring.mvc的属性进行springmvc的配置修改
	2. **接管Spring Boot对spring mvc的自动配置**
		1. 通过为添加@configuration注解的配置类添加@EnableWebMvc注解标识为该配置为spring mvc的配置信息
		2. 通过继承WebMvcConfigurerAdapter的配置类(添加了@configuration注解)表示该配置为spring mvc的配置信息，则可以不同添加@EnableWebMvc注解
3. 注册Filter、Servlet、Listener
	1. 通过直接定义Filter、Servlet、Listener对应的bean进行注册
	2. 通过ServletRegistrationBean、FilterRegistrationBean、ListenerRegistrationBean进行注册
3. 切换Servlet容器
	1. Spring Boot默认使用Tomcat作为Servlet容器，可以通过修改pom文件进行配置
		2. 切换为Jetty

				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-thymeleaf</artifactId>
					<exclusions>
						<!-- 移除tomcat的依赖 -->
						<exclusion>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-starter-tomcat</artifactId>
						</exclusion>
					</exclusions>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-jetty</artifactId>
				</dependency>
		3. 切换为undertow

				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-thymeleaf</artifactId>
					<exclusions>
						<!-- 移除tomcat的依赖 -->
						<exclusion>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-starter-tomcat</artifactId>
						</exclusion>
					</exclusions>
				</dependency>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-undertow</artifactId>
				</dependency>
4. **配置SSL**
	1. SSL(Secure Sockets Layer，安全套接层)是为通信提供安全及数据完整性的一种安全协议，SSL在网络传输层对网络连接进行加密。SSL协议位于TCP/IP协议与各种应用层协议之间，为数据通讯提供安全支持。SSL协议可分为两层： SSL记录协议（SSL Record Protocol）：它建立在可靠的传输协议（如TCP）之上，为高层协议提供数据封装、压缩、加密等基本功能的支持。 SSL握手协议（SSL Handshake Protocol）：它建立在SSL记录协议之上，用于在实际的数据传输开始前，通讯双方进行身份认证、协商加密算法、交换加密密钥等
	2. 使用
		1. 配置安装证书
			1. SSL认证中心获取
			2. 自授权证书(使用JDK处理)-->针对tomcat容器
				1. 在jdk的bin目录使用keytool工具生成授权证书
				
						keytool -genkey -alias tomcat
		2. 将用户目录生成的.keystore文件复制到项目根目录
		3. 配置ssl

				server.ssl.key-alias=tomcat		#servlet容器
				server.ssl.key-password=111111	#密钥
				server.ssl.key-store=.keystore	#证书地址
				server.ssl.key-store-type=JKS   #类型
		4. http转向https:需要通过配置TomcatEmbeddedServletContainerFactory，并添加connector来实现，在Spring Boot的入口类中添加以下代码

				@Bean
				public TomcatEmbeddedServletContainerFactory servletContainerFactory(){
					TomcatEmbeddedServletContainerFactory factory=new TomcatEmbeddedServletContainerFactory(){
						
						@Override
						protected void postProcessContext(Context context) {
							//设置安全认证方式
							SecurityConstraint constraint=new SecurityConstraint();
							//访问受保护的资源时使用任何传输层保护
							constraint.setUserConstraint("CONFIDENTIAL");
							//创建受保护的资源
							SecurityCollection collection=new SecurityCollection();
							collection.addPattern("/*");
							constraint.addCollection(collection);
							context.addConstraint(constraint);
						}
						
					};
					//实现http-->https协议的转换
					factory.addAdditionalTomcatConnectors(connector());
					return factory;
				}
			
				@Bean
				public Connector connector() {
					//设置http协议时的信息
					Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
					connector.setScheme("http");
					//使用http联系是使用的端口
					connector.setPort(8080);
					connector.setSecure(false);
					//重定向到https使用的端口及此时server.port的值
					connector.setRedirectPort(8089);
					return connector;
				}
5. 设置favicon:将自己的favicon.icon文件放置在以下目录任意即可

		{
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };
6. **WebSocket**

    WebSocket protocol 是HTML5一种新的协议。它实现了浏览器与服务器全双工通信(full-duplex)。一开始的握手需要借助HTTP请求完成。

     WebScoket是通过一个socket来实现双工异步通信的能力的。但是直接使用WebSocket(获取SockJs：WebScoket协议的模拟，增加了当浏览器不支持WebSocket的时候的兼容支持)协议开发程序显得比较繁琐，但是使用其子协议STOMP比较方便，它是一个更高级别的协议，STOMP协议是一个基于帧的格式来定义消息，与HTTP的request和response类似(具有类似于@RequestMapping和@MessageMapping注解)

	1. 集成
		1. 引入spring-boot-start-websocket的支持 
		2. spring boot的自动配置：通过springboot的autoconfigure.websocket包中的WebSocketAutoConfiguration对实现自动配置
	2.  使用
		1.  广播式：客户端对服务器端消息进行订阅，服务端有消息就直接发送到订阅的客户端中
			1. 配置WebSocket,在配置类上添加@EnableWebSocketMessageBroker注解并继承AbstractWebSocketMessageBrokerConfigurer类对endpoint进行注册和MessageBroker进行配置
			2. 定义消息发送器，通过使用@SendTo注解定义订阅消息的订阅号

					@SendTo("/topic/boardcast")//订阅地址：服务器--->客户端
					@MessageMapping("/welcome")//请求的地址：客户端-->服务器
					public WebSocketMessage message(WebSocketMessage message){
						WebSocketMessage result=new WebSocketMessage();
						result.setName("Welcome，"+message.getName());
						
						return result;
					}
			3. 客户端通过STOMP链接服务端或发送消息