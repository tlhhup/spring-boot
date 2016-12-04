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
	3. **初始化流程**，springboot的程序入口为SpringApplication的run方法

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
4. 运作原理-->主要通过Condition实现
	1. 通过查看@SpringBootApplication注解的源码可以看出其使用了@EnableAutoConfiguration注解用于实现自动的配置

			@Target(ElementType.TYPE)
			@Retention(RetentionPolicy.RUNTIME)
			@Documented
			@Inherited
			@AutoConfigurationPackage
			@Import(EnableAutoConfigurationImportSelector.class)
			public @interface EnableAutoConfiguration {
	其中最为关键的是使用了@Import注解导入配置功能，EnableAutoConfigurationImportSelector使用SpringFactoriesLoader.loadFactoryNames方法来扫描具有META-INF/spring.factories我文件的jar包。
	2. 核心注解
		1. @ConditionalOnBean：当容器中具有指定的Bean的条件下
		2. @ConditionalOnClass：当类路径下具有指定的类的条件下
		3. @ConditionalOnExpression：基于SpEL表达式作为判断条件
		4. @ConditionalOnJava：基于JVM版本作为判断
		5. @ConditionalOnJndi：在JNDI存在的条件下查找指定的位置
		6. @ConditionalOnMissingBean：当容器中没有指定的Bean的添加下
		7. @ConditionalOnMissingClass：当类路径下没有指定的类的条件下
		8. @ConditionalOnNotWebApplication：当前项目不是Web项目的条件下
		9. @ConditionalOnProperty：指定的属性是否具有指定的值
		10. @ConditionalOnResource：类路径是否有指定的资源
		11. @ConditionalOnSingleCandidate：当指定的Bean在容器中只有一个，或者虽然有多个但是指定首选的Bean
		12. @ConditionalOnWebApplication：当前项目是Web项目的条件下
13. springboot的初始化流程
	1. 入口函数：springApplication的run方法
		
			public ConfigurableApplicationContext run(String... args) {
				...
				//创建监听器
				SpringApplicationRunListeners listeners = getRunListeners(args);
				listeners.started();
				try {
					ApplicationArguments applicationArguments = new DefaultApplicationArguments(
							args);
					//初始化spring容器
					context = createAndRefreshContext(listeners, applicationArguments);
					//后续刷新
					afterRefresh(context, applicationArguments);
					listeners.finished(context, null);
					....
			}
	2. 初始化容器

			private ConfigurableApplicationContext createAndRefreshContext(
				SpringApplicationRunListeners listeners,
				ApplicationArguments applicationArguments) {
				ConfigurableApplicationContext context;
				// 判断所属的环境创建不同的环境(web和标准环境)
				ConfigurableEnvironment environment = getOrCreateEnvironment();
				configureEnvironment(environment, applicationArguments.getSourceArgs());
				//打印Banner		
				if (this.bannerMode != Banner.Mode.OFF) {
					printBanner(environment);
				}
		
				// 创建上下文，该方法为核心
				context = createApplicationContext();
				context.setEnvironment(environment);
				postProcessApplicationContext(context);
				applyInitializers(context);
				listeners.contextPrepared(context);
				if (this.logStartupInfo) {
					logStartupInfo(context.getParent() == null);
					logStartupProfileInfo(context);
				}
		
				// Add boot specific singleton beans
				context.getBeanFactory().registerSingleton("springApplicationArguments",
						applicationArguments);
		
				// Load the sources
				Set<Object> sources = getSources();
				Assert.notEmpty(sources, "Sources must not be empty");
				load(context, sources.toArray(new Object[sources.size()]));
				listeners.contextLoaded(context);
		
				// Refresh the context
				refresh(context);
				if (this.registerShutdownHook) {
					try {
						context.registerShutdownHook();
					}
					catch (AccessControlException ex) {
						// Not allowed in some environments.
					}
				}
				return context;
			}
	3. 获取上下文对象

			protected ConfigurableApplicationContext createApplicationContext() {
				Class<?> contextClass = this.applicationContextClass;
				if (contextClass == null) {
					try {
						//通过不同的环境得到不同的注解配置类
						contextClass = Class.forName(this.webEnvironment
								? DEFAULT_WEB_CONTEXT_CLASS : DEFAULT_CONTEXT_CLASS);
					}
					catch (ClassNotFoundException ex) {
						throw new IllegalStateException(
								"Unable create a default ApplicationContext, "
										+ "please specify an ApplicationContextClass",
								ex);
					}
				}
				//初始化上下文
				return (ConfigurableApplicationContext) BeanUtils.instantiate(contextClass);
			}