###spring boot

spring boot它使用“习惯优于配置”的理念让你的项目快速运行起来，使用spring boot很容易创建一个独立运行(运行jar，内嵌servlet容器)、准生产级别的基于spring框架的项目。

1. 核心功能
	1. 独立运行的spring项目，可以以jar包的形式独立运行
	2. 内嵌servlet容器
	3. 提供starter简化maven配置，通过指定的starter来自动引入依赖
	4. 自动配置spring
	5. 准生产的应用监控：提供基于http、ssh、telnet对运行时的项目进行监控
	6. 无代码生成和xml配置
	7. 开发热部署：在不重启 Java 虚拟机的前提下，能自动侦测到 class 文件的变化，更新运行时 class 的行为。
8. **环境搭建**(使用maven构建)
	1. 添加Spring Boot的父级依赖
	
			<!-- 标记该项目为Spring Boot项目 -->
			<parent>
				<groupId>org.springframework.boot</groupId>
			    <artifactId>spring-boot-starter-parent</artifactId>
			    <version>1.3.5.RELEASE</version>
			</parent>
	2. 添加Web支持的starter pom-->添加依赖

			<dependency>
			    <groupId>org.springframework.boot</groupId>
			    <artifactId>spring-boot-starter-web</artifactId>
			    <version>1.3.5.RELEASE</version>
			</dependency>
	2. 添加Spring Boot的编译插件

			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
	3. 指定jdk的版本：Spring Boot最低使用jdk1.6
	3. 添加控制器类
		1. 通过在普通类中添加@SpringBootApplication注解标识开启自动配置

				//@ResponseBody标识将返回的数据直接放入responseBody中
				@RestController//使用组合注解,该注解组合@Controller和@ResponseBody
				@SpringBootApplication
				public class FirstSpringBootApplication {
				
					@RequestMapping("/index")
					public String index(){
						return "Hello Spring Boot";
					}
					
				}
		2. 使用SpringApplication.run指定配置类:启动Spring Boot应用

				public static void main(String[] args){
					SpringApplication.run(FirstSpringBootApplication.class, args);
				}
		3. 启动项目
			1. 使用命令：`mvn spring-boot:run`
			2. 选中项目执行以java应用的方式运行
			3. 浏览器访问：主机+url

					http://localhost:8080/index
3. Spring Boot的**核心**
	1. 入口类和@SpringBootApplication注解
		1. 入口类为SpringBoot引用的入口函数，其定义和普通java主函数定义一致，不过需要使用SpringApplication的run方法来启动Spring Boot应用项目。建议将入口类放在groupId+artifactId组合的包名下
		2. @SpringBootApplication注解：该注解为组合注解

				@Target(ElementType.TYPE)
				@Retention(RetentionPolicy.RUNTIME)
				@Documented
				@Inherited
				@Configuration
				@EnableAutoConfiguration
				@ComponentScan
				public @interface SpringBootApplication {
				}
			1. Spring Boot会**自动扫描**使用了@SpringBootApplication注解类所在的同级包以及子包中的Bean(若为JPA项目还可以扫描标注@Entity的实体类)
			2. 通过exclude属性可以指定排除扫描的Bean
	3. 全局配置文件：Spring Boot的配置文件在src/main/resources目录(application.properties)或者类路径的/config(application.yaml)下，支持properties和yaml类型的配置文件
		1. properties文件配置
		
				# 修改端口号
				server.port=8090
				# 修改上下文路径	
				server.contextPath=/hellBoot
	3. 附加注解
		1. @ImportResource：可以引入外部的xml配置文件
		2. @ConfigurationProperties：可以将properties属性和一个Bean的属性关联(将属性文件中的数据绑定到Bean的属性身上)
	3. 运行原理(自动配置)：基于Condition条件的判断
		1. 其Sprint Boot的核心配置在spring-boot-autoconfigure-1.3.5.RELEASE.jar包中的/META-INF/spring.factories文件，定义了所有的自动配置

					