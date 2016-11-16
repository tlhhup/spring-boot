###spring mvc

主要学习spring mvc的零配置注解开发和测试

1. spring-boot的web项目的结构
	1. 所有的网页资源文件都可以放置在src/main/resource下，但是在使用视图解析器设置前缀的时候需要设置为

			//这个是src/main/resource进行编译部署之后的路径
			viewResolver.setPrefix("/WEB-INF/classes/views/");
	2. 使用maven进行构建的时候编译创建需要添加

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.6</version>
				<configuration>
					<!-- 去掉web.xml文件的检测 -->
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
			</plugin>
2. 基于配置类的springmvc应用搭建
	1. 在配置类中添加@EnableWebMvc注解，标识启动mvc的配置支持

			@Configuration
			@EnableWebMvc//开启webmvc的配置支持
			@ComponentScan(basePackages={"com.tlh.springmvc.**"},includeFilters={@Filter(classes={Controller.class,Service.class,Repository.class})})
			public class SpringMvcConfig {
				//...
			}
	2. 配置视图解析器

			@Bean
			public InternalResourceViewResolver viewResolver(){
				InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
				//这个是src/main/resource进行编译之后的路径
				viewResolver.setPrefix("/WEB-INF/classes/views/");
				viewResolver.setSuffix(".jsp");
				viewResolver.setViewClass(JstlView.class);
				
				return viewResolver;
			}
	3. **整合web容器**：通过实现**WebApplicationInitializer**接口将spring和web容器整合

			public class WebInitializer implements WebApplicationInitializer {

				@Override
				public void onStartup(ServletContext servletContext) throws ServletException {
					AnnotationConfigWebApplicationContext act=new AnnotationConfigWebApplicationContext();
					act.register(SpringMvcConfig.class);
					//和servlet整合
					act.setServletContext(servletContext);
					
					//配置springmvc的控制器
					Dynamic dynamic = servletContext.addServlet("springmvc", new DispatcherServlet(act));
					//设置后缀
					dynamic.addMapping("/");
					dynamic.setLoadOnStartup(1);
				}
			
			}
	4. 说明
		1. AnnotationConfigWebApplicationContext是spring提供的针对web应用的上下文
		2. WebApplicationInitializer是spring提供用来配置Servlet3.0+配置的接口，从而实现替代web.xml的位置
3. springmvc中的注解
	1. @Controller：标识控制器
	2. @RequestMapping：设置请求地址映射
		1. consumes：表示接受的媒体类型和字符集
		2. produces：返回的response的媒体类型和字符集
	3. @ResponseBody：将返回值放在response体内
	4. @RequestBody：将request体中的数据绑定到方法参数上
	5. @PathVariable：用来接收路径参数
	6. @RestController：组合注解，组合了@Controller 和 @ResponseBody，因此同时具有他们的功能
	7. @ModelAttribute：将数据通过键值对的方式添加到request域中
7. **定制springmvc**：通过将配置类实现**WebMvcConfigurerAdapter**接口来定义springmvc的配置信息
	1. 静态资源映射：通过重写addResourceHandlers方法处理(相当于配置文件中的`<mvc:resources location="/resources/upload/" mapping="/resources/upload/**"/>`)
	2. 拦截器配置：通过重写addInterceptors方法处理
	3. 全局异常解析器：通过重写configureHandlerExceptionResolvers方法处理
	4. 静态控制器：通过重写addViewControllers方法可以添加静态的控制器(相当于配置文件中的`<mvc:view-controller path="/" view-name="redirect:/UserAction/list"/>`)
	5. 文件上传：通过配置bean的方式进行配置，只是方法名必须为multipartResolver

			@Bean
			public MultipartResolver multipartResolver(){
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
				return multipartResolver;
			}
	6. 自定义HttpMessageConverter(处理request和response中的数据)
		1. 继承AbstractHttpMessageConverter类
			1. supports：标识转换的数据类型格式
			2. readInternal：处理请求的数据
			3. writeInternal：处理如何输出数据到response
		4. 重写extendMessageConverters方法添加自定义的HttpMessageConverter
5. **springmvc的测试**

	需要模拟MockMVC、MockHttpServletRequest、MockHttpServletResponse、MockHttpSession

	1. 通过使用junit和Spring TestContext Framework进行处理，在pom文件中添加

			<!-- 测试 -->
			<dependency>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
				<version>4.12</version>
				<!-- 只存活在test周期，发布时不需要 -->
				<scope>test</scope>
			</dependency>
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-test</artifactId>
				<version>${spring.framework}</version>
			</dependency>
	2. 通过**@WebAppConfiguration**注解来指定所有的页面资源和加载WebApplicationContext对象

			@RunWith(SpringJUnit4ClassRunner.class)
			@ContextConfiguration(classes={SpringMvcConfig.class})//指定springmvc的配置文件
			@WebAppConfiguration("src/main/resource/views")//指定页面资源
			public class SpringMvcTestConfig {
			
				private MockMvc mockMvc;
				
				@Autowired
				private WebApplicationContext act;
				
				@Autowired
				private MockHttpSession session;
				
				@Autowired
				private MockHttpServletRequest request;
				
				@Before
				public void steup(){
					this.mockMvc=MockMvcBuilders.webAppContextSetup(act).build();
				}
				
				@Test
				public void tesIndex() throws Exception{
					//模拟action的执行流程
					mockMvc.perform(get("/index"))//发送get请求
							.andExpect(status().isOk())//预期状态码
							.andExpect(view().name("index"))//预期的视图名
							.andExpect(forwardedUrl("/WEB-INF/classes/views/index.jsp"))//预期页面转向的真正路径
							.andExpect(model().attribute("user", "hello"));//预期model里的数据
				}
				
			}
		1. **注意事项**：get、status、view等所有静态导入的方法

				//方法的静态导入
				import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
				import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;