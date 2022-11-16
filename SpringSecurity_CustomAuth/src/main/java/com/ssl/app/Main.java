package com.ssl.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}

/*
# ---------------------------------------------------------------------
# General Info
# ---------------------------------------------------------------------

#Title
Spring Security Fundamental - Lesson 3 - CCustom authentication

Source Tutorial
https://www.youtube.com/watch?v=asAUMpEcdXw&list=PLEocw3gLFc8X_a8hGWGaBnSkPFJmbb8QP&index=3

https://github.com/lspil/youtubechannel/tree/master/ss_2022_c3_e1/src/main/java/com/example/ss_2022_c3_e1

# Dependencies
- Spring Web
- Spring Security
- Lombok

# Postman Test
GET | http://localhost:8080/demo | Authorization | Type: No Auth
Return Status 403 Forbidden
Which means the "/demo" Endpoint is authenticated

GET | http://localhost:8080/demo | Headers | Key : key, Value : secret
Return Status 200 OK
Which means you have access to the "/demo" Endpoint

# ---------------------------------------------------------------------
# Application Goal
# ---------------------------------------------------------------------

Implement an authentication mechanism here the request contains a header
key with a secret value.

The tutorial shows the full design of Spring Security authentication
mechanisms. It shows you how to build authentication from scratch.
We're going to customize components like "filter",
"authentication manager" and "authentication provider" in an
authentication mechanism.



# ---------------------------------------------------------------------
# Application Flow
# ---------------------------------------------------------------------

# Global Flow

request  ---> filter 1  ---> filter 2  ---> filter 3 ---> controller
response <--- filter 1  <--- filter 2  <--- filter 3 <--- controller


# Filter Flow

filter 1 ---> authentication manager ---> authentication provider('s)
filter 1 <--- authentication manager <--- authentication provider('s)


# Step by Step

00.		Request
		- The request contains a "Header Key" with a "Secret Value"

		- The request will go through the filter chain

		- and the response will somehow go back



00. 	Custom Authentication Filter
		config ---> SecurityConfig.class ---> SecurityFilterChain()

		- Parameter			We input HttpSecurity, which is used to build a filter

		- .addFilterAt()	"custom filter" to be registered in the same position as
							"UsernamePasswordAuthenticationFilter"
		- .authorizeRequests().anyRequest().authenticated()
							secure all endpoints, require authentication
		- .and()			concatenate multiple configure of Spring Security

		- .build()			Create the custom filter

		- return http;		return the custom filter



00.		Custom Authentication Filter details
		config ---> security ---> filters ---> CustomAuthenticationFilter.class ---> doFilterInternal()

		- @Component 						automatically detect our custom beans

		- CustomAuthenticationManager 		inject into this class

		- implement Filter 					(deprecated) Get Filter Interface (Contract)
		 									override abstract methods
		- extends OncePerRequestFilter		(recommended) This filter is only called once

		- HttpServletRequest request		accessing parameters of a request

		- HttpServletResponse response		accessing information about the data it will send back

		- FilterChain, filterChain			Is an Interface (contract) with an abstract method: doFilter()

		- String key						get Key from request Header

		1. create an authentication object which is not yet authenticated (36:00)

		- CustomAuthentication	ca			Instance class contractor, set authentication true, and input the key String

		- var a								instance authenticate() method

		- if(a.isAuthenticated())			in case authentication is correct...

		- .setAuthentication(a)				store authentication, the authorization mechanism
											can check out who is authenticated
		- .doFilter							Does nothing right now?

        2. delegate the authentication object to the manager
        3. get back the authentication from the manager
        4. if the object is authenticated then send request to the next filter in the chain



00.		Custom Authentication Manager
		config ---> security ---> mangers ---> CustomAuthenticationManager.class

		- @Component 						Automatically detect our custom beans

		- implements AuthenticationManger	Interface (contact) with abstract method called authenticate()

		- provider							Inject CustomAuthenticationProvider

		- authenticate()					Take care of authentication (41:00)

		- if(provider.support())			if its does support...
											NOTE: if you had mre provide you would iterator through them all
		- provider							authenticate authentication

		- return;							return authentication


00.		Custom Authentication
		config ---> security ---> authentication ---> CustomAuthentication.class

		implements Authentication			Interface (contract) with a number of abstract methods
											NOTE: Most methods wound be implicated


00.		Custom Provider Manager
		config ---> security ---> providers ---> CustomAuthenticationProvider

		- @Value()							Get Secret Key from application.properties
		- @Component 						Automatically detect our custom beans

		- implements AuthenticationProvider	Interface (contact) with abstract method called
											authenticate, supports()
		- String headerKey					Get "Header Key"
		- if								Compare "Secret Key" with "Header Key"
		- CustomAuthentication result		update authentication to true and remove key from storage
		- return result;					return updated authentication



00.		Get Static Key
		resource ---> application.properties ---> secret.key=secret
		- NOTE: this key shout be in a Vault



00. 	Endpoint Method
		controller ---> DemoController.class ---> demo "endpoint method"
		- this method returns the protected resource
		- Use key to get authenticated
		- Get 200 OK response



# ---------------------------------------------------------------------
#  Component Explanation
# ---------------------------------------------------------------------

# Request

# filter chain
- SecurityFilterChain that contains a series of filter chains,
  and normally our configuration is based around building SecurityFilterChain.

# filter

# controller

# authentication manager

# authentication provider('s)

# user Details
- Retrieve user details

# password encoder

# http basic
- authentication style

# open id connect
- authentication style

# endpoint

# HttpSecurity
- HttpSecurity is used to build a filter

# Spring Security
- Spring Security is a filter based framework

 */