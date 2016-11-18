###
1. 设置banner
	1. 图片的位置:
	
			banner.image.location # 在application.properties文件中声明
	2. 文本文件的位置：该文本文件在classpath下的banner.txt
	
			banner.location	# 在application.properties文件中声明，否则使用classpath下面的banner.txt文件
2. Spring Boot 的配置属性信息
	1. 存在于spring-boot-autoconfigure-1.4.2.RELEASE.jar中的/META-INF/spring-configuration-metadata.json
	2. spring-boot-1.4.2.RELEASE.jar中的/META-INF/spring-configuration-metadata.json
3. 在@RequestMapping的注解中的**produces**属性用于指定将数据放入responseBody,设置其数据类型和字符集，其数据类型在**MimeTypeUtils**中存在定义
4. 控制日志的级别：

		logging.level.root=info	# 在application.properties文件中声明
5. @SpringBootApplication注解
	1. scanBasePackages：指定扫描的包，默认扫描使用@SpringBootApplication注解的类所在的包的子包和统计的包
	2. exclude：去除特定的类，可以通过注解进行标识

			// 扫描com.tlh.spring的包及其子包去除使用Repository注解的Bean
			@SpringBootApplication(scanBasePackages="com.tlh.spring",exclude={Repository.class})
	3. 初始化流程

			//打印Banner
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			analyzers = new FailureAnalyzers(context);
			//初始化环境设置active profile
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
			//刷新Spring容器
			refreshContext(context);
			afterRefresh(context, applicationArguments);
6. **Profile使用**：不同环境读取不同的配置
	1. 配置文件：命名

			application-{xxx}.properties
	2. 指定运行的环境：在application全局配置文件中

			spring.profiles.active=prod # 指定使用的配置文件
	3. 程序读取的时候优先读取application.properties中选中的profile的配置，若读不到才会从application.properties去读
			