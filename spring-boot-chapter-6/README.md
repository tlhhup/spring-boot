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
spring boot通过org.springframework.boot.autoconfigure.thymeleaf包对thymeleaf进行自动配置，ThymeleafProperties用来配置Thymeleaf

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
4. 配置SSL
			