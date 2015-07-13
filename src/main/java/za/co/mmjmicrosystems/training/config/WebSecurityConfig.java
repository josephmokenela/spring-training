package za.co.mmjmicrosystems.training.config;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Log log = LogFactory.getLog(WebSecurityConfig.class);
	
	@Resource
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("Creating a password encoder");
		return new BCryptPasswordEncoder();
		
	}
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .antMatchers("/",
	                		"/home",
	                		"/error",
	                		"/signup",
	                		"/forgot-password",
	                		"/reset-password/*",
	                		"/public/**").permitAll()
	                .anyRequest().authenticated();
	        http
	            .formLogin()
	                .loginPage("/login")
	                .permitAll().and()
	            .logout()
	                .permitAll();
	    }
	 
	 
	 
	    @Override
	    @Autowired
	    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
	        authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }

}
