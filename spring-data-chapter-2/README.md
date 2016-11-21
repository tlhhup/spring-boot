###Repository(存储)
1. 使用
	1. 定义实体类的存储接口继承至Repository或者它的子接口

			public interface CustomerDao extends CrudRepository<Customer, Long> {

			}
	2. 需要存储的实体类采用JPA的注解

			@Entity
			public class Customer {
			
				@Id
				@GeneratedValue(strategy=GenerationType.AUTO)
				private Long id;
				@Column
				private String name;
				@Column
				private String address;
			}
	3. Spring的配置类
		1. 使用@EnableJpaRepositories注解标识启用JPA的存储方式
		2. 使用@EnableTransactionManagement采用注解事务
		3. 组件定义
			1. 数据源

					//配置数据源
					@Bean
					public DataSource dataSource(){
						EmbeddedDatabaseBuilder builder=new EmbeddedDatabaseBuilder();
						//使用内嵌的HSQL数据库
						return builder.setType(EmbeddedDatabaseType.HSQL).build();
					}
			2. 配置JPA的实体管理工程

					@Bean
					public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
						//创建hibernate的jpa的适配器
						HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
						vendorAdapter.setDatabase(Database.HSQL);
						//生成ddl
						vendorAdapter.setGenerateDdl(true);
				
						LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
						//设置jpa的实现适配器
						factory.setJpaVendorAdapter(vendorAdapter);
						//设置扫描的包及jpa的包(使用了jpa注解的包)
						factory.setPackagesToScan("com.tlh.spring.entity");
						//设置数据源
						factory.setDataSource(dataSource());
				
						return factory;
					}
			3. 配置事务管理器(采用JAP的事务管理器)

					//配置事务管理器
					@Bean
					public PlatformTransactionManager transactionManager(){
						JpaTransactionManager txManager = new JpaTransactionManager();
						txManager.setEntityManagerFactory(entityManagerFactory().getObject());
						return txManager;
					}
				