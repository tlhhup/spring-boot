###spring

主要对spring的注解开发已经新的注解的接受和使用

1. 表达式：spring中可以使用@value注解来注入表达式的值
	1. 注入字面值

			@Value("hello,world")
			private String normal;
	2. 注入操作系统的属性：注意采用的是**#号**

			@Value("#{systemProperties['os.name']}")
			private String osName;
	3. 注入表达式的运算结果

			@Value("#{T(java.util.Math).random()*100.0}")
			private double randomNumber;
	4. 注入其他bean的属性

			@Value("#{userService.name}")
			private String name;
	5. 注入文件内容

			@Value("classpath:jdbc.properties")
			private Resource resource;
	6. 注入网络内容

			@Value("http://www.baidu.com")
			private Resource testUrl;
	7. 注入属性文件

			@Autowired
			private Environment environment;
2. 采用**java配置类来配置spring容器**(将原来采用xml文件的方式换成java配置)
	1. 通过在java类身上添加@Configuration注解标识该类为spring的配置类
	2. 定义bean：使用@Bean注解可以定义spring容器中bean，但该方法必须加在方法上，并且将以方法的名称作为该bean的名称

			@Bean//定义在方法上，将使用方法的名称作为bean的名称
			public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
				return new PropertySourcesPlaceholderConfigurer();
			}
	2. 使用AnnotationConfigApplicationContext来实例化spring容器

			AnnotationConfigApplicationContext act =new AnnotationConfigApplicationContext();
			//注册配置类
			act.register(ElConfig.class);
			//刷新启动spring容器，必须调用，如果在创建act的时候就指定了配置了则可以省略
			act.refresh();
	3. 高级注解
		1. 异步：将一个方法标记为异步的方法
			1. 通过在配置类中添加@EnableAsync注解用于开启对异步任务的支持(相当于xml文件的task命名空间)
			2. 在执行的bean的方法使用@Async注解来表示该方法是一个异步方法
			3. 如果需要制定一执行器则配置类可以实现AsyncConfigurer接口去自定义异步的执行器和异常的处理器(其默认的执行器为SimpleAsyncTaskExecutor)
		1. 调度：
			1. 通过在配置类中添加@EnableScheduling注解来开启对调度任务的支持
			2. 在执行的bean的方法使用@Scheduled注解来标识该方法为调度的方法
			3. 如果需要定制执行器则配置类可以实现SchedulingConfigurer接口来自定义
		4. 条件注解：通过使用@Conditional注解可以根据满足一个特定条件的时候创建特定的bean
			1. 条件必须实现Condition接口
		2. **Enable注解**
			1. @EnableAspectJAutoProxy：开启对AspectJ自动代理的支持
			2. @EnableAsync：开启对异步任务的支持
			3. @EnableScheduling：开启对调度的支持
			4. @EnableCaching：开启注解式缓存
			5. @EnableWebMvc：开启spring mvc的配置支持
			6. @EnableJpaRepositories：开启对spring data Jpa repository的支持
			7. @EnableTransactionManagement：开启注解式事务的支持