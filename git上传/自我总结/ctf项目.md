##SpringBoot以及Restful的使用：
###一、项目的划分：
	1.api_server项目：用于数据库的连接
		(1)controller:使用@Controller注解注入，用于响应web_server的请求，以json的格式返回。
			有参数请求路径格式@RequestMapping("/v1/ctf/competitionType/findCompetitionTypeById/{id}") 通过@PathVariable("id")Integer id 注入参数
			无参数请求路径格式@RequestMapping("/v1/ctf/competitionType/findAllCompetitionType")
		   都通过@ResponseBody注解返回
		(2)service接口和service实现类：使用@Service注入，通过Mapper和Mapper.xml调用数据库获取数据
			例如：@Service("competitionRecordService") 实现类CompetitionRecordService
		(3)Mapper接口和Mapper.xml：Mapper接口通过@Repository注解注入
			例如：@Repository("CompetitionMapper") 接口CompetitionMapper 
			Mapper.xml编写sql语句获取数据
		(4)含有main方法的类：@SpringBootApplication注解注入
			通过@MapperScan("路径.mapper*")配置扫描Mapper包
		(5)配置数据库的连接：
			<?xml version="1.0" encoding="UTF-8"?>
			<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
			<generatorConfiguration>
			   <context id="DB2Tables" targetRuntime="MyBatis3">
			    <jdbcConnection connectionURL="jdbc:mysql://localhost/ctftest" driverClass="com.mysql.jdbc.Driver" password="root" userId="root" />
			    <javaModelGenerator targetPackage="com.magicbird.api.dao.test.bean" targetProject="api" />
			    <sqlMapGenerator targetPackage="com.magicbird.api.dao.test.mapper" targetProject="api" />
			    <javaClientGenerator targetPackage="com.magicbird.api.dao.test.exam" targetProject="api" type="XMLMAPPER" />
			    <table schema="???" tableName="os_user">
			      <columnOverride column="???" property="true" />
			    </table>
			  </context>
			</generatorConfiguration>	


-

	2.web_server项目：用于调用api_server和web的交互,将数据展现给客户端
		(1)controller:和springMvc书写无异
		(2)service:每个Service都要添加RestTemplate属性(通过@Autowired注入)和apiServerAddress(通过@Value("${apiserver.address}")注入)，
		   在application.properties中配置apiserver.address: http://127.0.0.1:8090
			例如：
				public String addGroups(Groups groups) throws URISyntaxException, MalformedURLException {
					String apigroups = "/v1/ctf/groups/addGroups/{groups}";
					URI uri = new URI(apiServerAddr);
					JSONObject groupsJson = JSONObject.fromObject(groups);
					String path = apigroups.replaceAll("\\{groups\\}", groupsJson.toString());
					URL url = new URL(uri.toURL(), path);
					String resp = restTemplate.getForObject(url.toString(), String.class, groupsJson.toString());
					return resp;
				}
		(3)含有main方法的类：
		   ➀：@SpringBootApplication注解注入，因为在service中用到RestTemplate，所以启动的时候需要实例化该类的一个实例，
		   使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例 
			如下所示：  
					@Bean
					public RestTemplate restTemplate(RestTemplateBuilder builder) {
						return builder.build();
					} 
		   ➁：注入WebMvcConfigurer
					@Bean
				    public WebMvcConfigurer corsConfigurer() {
				        return new WebMvcConfigurerAdapter() {
				            @Override
				            public void addCorsMappings(CorsRegistry registry) {
				                registry.addMapping("/exam/fill-in-the-blank").allowedOrigins("http://localhost:9000");
				            }
				        };
				    }
		(3)配置类Config类：使用@EnableWebMvc和@Configuration注解组合注入，继承自(extends) WebMvcConfigurerAdapter
			常规写法：无任何逻辑的请求
					@RequestMapping("/toview")
					public String view(){
					   return "view";
					}
			可通过重写addViewControllers统一配置
					@Override
				    public void addViewControllers(ViewControllerRegistry registry) {
				       super.addViewControllers(registry);
				       registry.addViewController("/toview").setViewName("view");
				    }
			   @Bean
			   public ViewResolver viewResolver() {
			      InternalResourceViewResolver bean = new InternalResourceViewResolver();
			      bean.setViewClass(JstlView.class);
			      bean.setPrefix("/WEB-INF/jsp/");
			      bean.setSuffix(".jsp");
			      return bean;
			   }
			
		
[RestTemplate详解](https://blog.csdn.net/itguangit/article/details/78825505)

[WebMvcConfigurerAdapter详解](https://www.jianshu.com/p/52f39b799fbb)

	3.core项目：主项目，作为上面两个项目的父级项目
		dao和两个项目公共类的编写，dao通过@EntityScan和@JsonIgnoreProperties(ignoreUnknown = true)组合注解注入





[redis缓存详解](http://www.voidcn.com/article/p-mptztkih-yz.html)
