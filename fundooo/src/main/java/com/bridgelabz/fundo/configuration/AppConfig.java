package com.bridgelabz.fundo.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
	@Bean
	public PasswordEncoder getEncryptedPassword() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		final RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}
}
