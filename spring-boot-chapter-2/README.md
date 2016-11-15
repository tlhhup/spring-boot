###spring
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
	1. 定义bean：使用@Bean注解可以定义spring容器中bean，但该方法必须加在方法上，并且将以方法的名称作为该bean的名称

			@Bean//定义在方法上，将使用方法的名称作为bean的名称
			public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
				return new PropertySourcesPlaceholderConfigurer();
			}
	2. 使用AnnotationConfigApplicationContext来实例化spring容器

			AnnotationConfigApplicationContext act =new AnnotationConfigApplicationContext();
			//注册配置类
			act.register(ElConfig.class);
			//刷新启动spring容器，必须调用
			act.refresh();