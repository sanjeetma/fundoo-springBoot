package com.bridgelabz.springboot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bridgelabz.fundo.dto.UserDTO;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.service.UserServiceImplementation;
import com.bridgelabz.fundo.util.JMSUtility;
import com.bridgelabz.fundo.util.JWTUtility;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FunAndDoApplicationTests {

		
	
	@InjectMocks
	private UserServiceImplementation userServiceImple;
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private ModelMapper modelMapper;

	@Autowired
	private JWTUtility jwtToken;

	@Mock
	private JMSUtility mail;

	@Mock
	private PasswordEncoder encryptedPassword;
	
	@Mock
	private RedisTemplate<Object, Object> redisTemplate;
	
	
	
	
	@Test
	public void contextLoads() throws Exception {
		String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjJ9.6I7rsOWYblDrC2OreB_9GyL6C1AowY5nA-fw2Il6_4Kgmnzf3TVrW0uqv_s8BtuwSGTyCtRgOUHCqkDve8ZsHg";
		
		userServiceImple.verification(token);
//		w
//		
//		assertEquals(expected, actual);
	}

}
