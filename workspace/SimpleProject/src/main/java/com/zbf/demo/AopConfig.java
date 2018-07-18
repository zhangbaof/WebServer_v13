package com.zbf.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.zbf.demo")
@EnableAspectJAutoProxy
public class AopConfig {	
}
