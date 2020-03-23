package cn.translation.collaborative.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${security.rememberme.tokenValidityTime}")
    private String tokenTime;

    /**
     * security提供的，快速配置filter过滤器的配置方法
     * security认证成功后，跳转到的结果请求是一个post请求，且默认跳转路径地址/login
     *      loginProcessingUrl() - 设置用于处理登录的请求路径，此路径与登录页面的表单请求页面一致，此转发请求为POST请求，所以不能直接跳转到静态资源（html页面）
     *      successForwardUrl() - 设置登录成功后进入的路径地址。
     * @param http security提供的工具对象，快速配置HTTP协议相关的filter过滤器
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login");

        http.logout().logoutSuccessUrl("/");

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/Vocabulary/getListOfVocabulary").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/css/**","/img/**","/js/**","/layui/**").permitAll()//静态资源放行
                .anyRequest().authenticated();

        /**
         * 配置rememberme
         * tokenRepository 设置rememberme数据存储的持久化对象
         * userDetailsService 设置认证流程实现对象
         */
        int time = Integer.valueOf(tokenTime);
        http.rememberMe()
                .tokenRepository(tokenRepository)
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(time);

        http.csrf().disable();

        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 生成一个rememberme的数据库持久化对象
     * @return
     */
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
