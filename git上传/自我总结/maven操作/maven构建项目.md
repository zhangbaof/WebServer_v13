###一、创建maven目录结构

因为是通过maven构建项目，所以项目必须根据maven的规范来操作:

1.项目文件夹，比如HelloWorld项目，新建文件夹HelloWorld

2.主要代码的目录--src/main/java

在这个目录下创建java package，并写入java代码

3.测试代码的目录--src/test/java

在这个目录下创建java 测试代码，通过导入junit jar包进行测试代码的编写

4.在项目的根目录下,创建pom.xml文件

pom.xml头部编写，固定格式

①

	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http:maven.apache.org/POM/4.0.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
		http://maven.apache.org/maven-v4_0_0.xsd">

			<modelVersion>4.0.0</modelVersion>
						
	</project>


因为到最后要编写成一个可执行的测试项目jar包，所以在这里加入maven坐标，maven版本号等

②

	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http:maven.apache.org/POM/4.0.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
		http://maven.apache.org/maven-v4_0_0.xsd">

		...................
		<groupId>com.juvenxu.mvnbook</groupId>
		<artifactId>hello-world</artifactId>
		<version>1.0-SNAPSHOT</version>
		<name>Maven Hello World Project</name>
		...................
	</project>

之前在编写测试代码时引入junit jar包，所以必须在pom.xml文件中添加其maven坐标以及版本号等等

③

	<?xml version="1.0" encoding="UTF-8"?>
	<project xmlns="http:maven.apache.org/POM/4.0.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
		http://maven.apache.org/maven-v4_0_0.xsd">	

		.................
		<dependencies>
			<dependency>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
				<version>4.7</version>
				<scope>test</scope>
			</dependency>
		</dependencies>
		................

	</project>

最后要成为一个可执行的jar包，必须加入外部插件才能实现，maven-shade-plugin插件

④

		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http:maven.apache.org/POM/4.0.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
		http://maven.apache.org/maven-v4_0_0.xsd">	

		.................
		<build>
			<plugins>
				<plugin>  
					<groupId>org.apache.maven.plugins</groupId>  
					<artifactId>maven-shade-plugin</artifactId>  
					<version>3.1.1</version>  		
					<executions>  
						 <execution> 
						 
							<goals>  
								<goal>shade</goal>  
							</goals>  
							<configuration>  
								<transformers>  
									<transformer implementation = "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">   
										<manifestEntries>
											<Main-Class>com.juvenxu.mvnbook.helloworld.HelloWorld</Main-Class>
											<X-Compile-Source-JDK>1.8</X-Compile-Source-JDK>
											<X-Compile-Target-JDK>1.8</X-Compile-Target-JDK>
										</manifestEntries>
									</transformer>
								</transformers>  
						 </execution>  
					</executions>  
			  </plugin> 
			</plugins>
		</build>
		.................
	</project>


至此，一个可执行的测试项目jar包编写完毕，可通过maven命令打包、运行、安装，便可看到最终结果。

	1.mvn clean compile 对项目进行编译
		在window下通过doc命令窗口，进入项目根目录下，键入命令执行编译

	2.mvn clean test 对项目进行测试

	3.mvn clean package 对项目进行打包

	4.mvn clean install 对项目进行安装，将项目输出的jar安装到maven本地仓库中

	5.java -jar target\${以上步骤2中，artifactId中的值}-${version中的值}.jar
		执行该命令，若在控制台输出Hello Maven，则这个测试算是成功


注意：因为是通过查看资料编写，资料中的插件版本号太低,在执行mvn clean package一直出错：

![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/git%E4%B8%8A%E4%BC%A0/20180704153709.png)

最后通过查阅[官方文档](http://maven.apache.org/plugins/maven-shade-plugin/examples/resource-transformers.html)解决该问题




二、通过maven命令创建maven项目，或者创建spring-boot项目等

可通过mvn archetype:generate命令来创建项目，若INFO则会让你选择一个项目的模板，键入数字，以及项目的maven坐标，最后完成项目的创建


三、maven常用命令：
	
	mvn dependency:list---
		显示所有已解析依赖，同时，每个依赖的范围也得以明确标示	
	mvn dependency:tree---
		帮助我们详细了解项目中所有依赖的具体信息
	mvn dependency:analyze---
		可以帮助分析当前项目的依赖



四、nexus安装

1.[下载安装包](https://www.sonatype.com/download-oss-sonatype)

2.nexus需要安装JDK1.8以上的版本

3.解压缩nexus安装包到C:\software(路径随意)

4.以管理员身份运行cmd窗口，切换到上面的目录C:\software\nexus-3.5.1-02\bin运行以下命令：

nexus /run

5.打开网址输入：http://localhost:8081/,可以使用默认的用户名和密码来登陆(admin/admin123)



	