package com.miraclebank.configuration

import com.miraclebank.security.AuthProviderService
import com.miraclebank.security.filter.JwtAuthenticationTokenFilter
import com.miraclebank.security.handler.Http401UnauthorizedEntryPoint
import com.miraclebank.security.handler.LogoutSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    internal var authenticationSuccessHandler: AuthenticationSuccessHandler? = null
    @Autowired
    internal var authenticationFailureHandler: AuthenticationFailureHandler? = null
    @Autowired
    internal var logoutSuccessHandler: LogoutSuccessHandler? = null
    @Autowired
    internal var authenticationEntryPoint: Http401UnauthorizedEntryPoint? = null
    @Autowired
    internal var authProvider: AuthProviderService? = null
    @Autowired
    internal var security: SecurityProperties? = null
    @Autowired
    internal var jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter? = null

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(authProvider)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val permited = arrayOfNulls<String>(security!!.ignored.size)
        security!!.ignored.toTypedArray()
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/user").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/authentication")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

        http.addFilterBefore(jwtAuthenticationTokenFilter!!, UsernamePasswordAuthenticationFilter::class.java)
        http.headers().cacheControl()
    }

    @Bean
    fun sha(): ShaPasswordEncoder {
        val shaPasswordEncoder = ShaPasswordEncoder(256)
        return shaPasswordEncoder
    }
}