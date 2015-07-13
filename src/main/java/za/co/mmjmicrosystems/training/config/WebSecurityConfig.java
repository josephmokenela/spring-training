package za.co.mmjmicrosystems.training.config;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Log log = LogFactory.getLog(WebSecurityConfig.class);
	
	@Value("${rememberMe.privateKey}")
	private String rememberMeKey;
	
	
	@Resource
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("Creating a password encoder");
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
    public RememberMeServices rememberMeServices() {
    	
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(rememberMeKey, userDetailsService);
        return rememberMeServices;
        
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
	                .rememberMe().key(rememberMeKey).rememberMeServices(rememberMeServices()).and()
	            .logout()
	                .permitAll();
	    }
	 
	 
	 
	    @Override
	    @Autowired
	    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
	        authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }

}
