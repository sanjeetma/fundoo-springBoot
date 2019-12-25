package com.bridgelabz.fundo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.NoteDTO;
import com.bridgelabz.fundo.dto.ResetPasswordDTO;
import com.bridgelabz.fundo.dto.UserDTO;
import com.bridgelabz.fundo.dto.UserLoginDTO;
import com.bridgelabz.fundo.exception.CustomException;
import com.bridgelabz.fundo.exception.ForgotException;
import com.bridgelabz.fundo.exception.InvalidCredentialException;
import com.bridgelabz.fundo.exception.ResetException;
import com.bridgelabz.fundo.model.LabelModel;
import com.bridgelabz.fundo.model.NoteModel;
import com.bridgelabz.fundo.model.RabbitMessage;
import com.bridgelabz.fundo.model.UserModel;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.util.JMSUtility;
import com.bridgelabz.fundo.util.JWTUtility;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JWTUtility jwtToken;

	@Autowired
	private JMSUtility mail;

	@Autowired
	private PasswordEncoder encryptedPassword;
	
//	@Autowired
//	private RedisTemplate<Object, Object> redisTemplate;
	
	private static Logger logger = Logger.getLogger(UserServiceImplementation.class);
	
	
	
	@Override
	public UserModel userRegistration(UserDTO userDto) {//same
		logger.info("kuchh bhi");
		Optional<UserModel> userModel = userRepository.findByEmail(userDto.getEmail());
		UserModel model = modelMapper.map(userDto, UserModel.class);
		logger.info("kuchh bhi");
		if (!userModel.isPresent()) {
			model.setCurrentTime(LocalDateTime.now());
			model.setUpdateTime(LocalDateTime.now());
			model.setPassword(encryptedPassword.encode(model.getPassword()));
			model = userRepository.save(model);
			String url="click to verification "+"http://localhost:8080/user/verification/";
			
			RabbitMessage messageRabbit=new RabbitMessage();
			messageRabbit.setEmail(userDto.getEmail());
			messageRabbit.setLink(url);
			messageRabbit.setToken(jwtToken.generateToken(model.getUserId()));
			logger.info(jwtToken.generateToken(model.getUserId()));
			mail.sendRabbit(messageRabbit);
			//mail.sendMail(userDto.getEmail(),url, jwtToken.generateToken(model.getUserId()));
		} else {
			throw new CustomException(101, "email already exit");
		}
		return model;
	}

	@Override
	public String verification(String token) {
		Long userId = jwtToken.verify(token);
		String message = null;
		Optional<UserModel> user = userRepository.findById(userId);
		if (user.isPresent()) {
			if (!user.get().isVerify()) {
				user.get().setVerify(true);
				userRepository.save(user.get());
				message = "Token verified Successfully...!!";
			} else {
				throw new CustomException(102, "user is not verify");
			}
		} else {
			throw new CustomException(103, "user is not present");
		}
		return message;
	}

	@Override
	public String login(UserLoginDTO userLoginDto) {
		Optional<UserModel> userModel = userRepository.findByEmail(userLoginDto.getEmail());
		String token = null;
		if (userModel.get().isVerify()) {

			if (encryptedPassword.matches(userLoginDto.getPassword(), userModel.get().getPassword())) {
				token = jwtToken.generateToken(userModel.get().getUserId());
//				redisTemplate.opsForValue().set(token, userLoginDto.getEmail());
//				System.out.println("getting from redis cache : "+ redisTemplate.opsForValue().get(token));
			} else {
				throw new InvalidCredentialException(102, "Invalid Password");
			}
		} else {
			mail.sendMail(userLoginDto.getEmail(), "Click on token", jwtToken.generateToken(userModel.get().getUserId()));
			throw new InvalidCredentialException(102, "Check your email id, verification is required...");
		}
		return token;
	}

	@Override
	public String forgetPassword(String email) {
		Optional<UserModel> userModel = userRepository.findByEmail(email);
		String massege = null;
		if (userModel.isPresent()) {
			if (userModel.get().isVerify()) {
				String url="click to verification "+"http://localhost:4200/reset/";
				String token = jwtToken.generateToken(userModel.get().getUserId());
				mail.sendMail(email,url, token);
				massege = token;
			} else {
				mail.sendMail(userModel.get().getEmail(), "Click on token",
						jwtToken.generateToken(userModel.get().getUserId()));
				massege = "first register ,check your email id";
			}
		} else {
			throw new ForgotException(104, "user is not present");
		}
		return massege;
	}

	public String resetPassword(ResetPasswordDTO resetPasswordDto, String token) {
		System.out.println("under reset password controller");
		Long userId = jwtToken.verify(token);
		System.err.println(userId);
		String message = "";
		if (userId != null && (resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword()))) {
			System.out.println("user is exit");
			UserModel userModel = userRepository.getOne(userId);
			if (userModel.isVerify() == true) {
				System.out.println("verify user");
				userModel.setPassword(resetPasswordDto.getPassword());
				userModel.setPassword(encryptedPassword.encode(userModel.getPassword()));
				userRepository.save(userModel);
				message = "successfully password updated";
			}
		} else {
			throw new ResetException(105, "Password Mismatch");
		}
		return message;
	}

	@Override
	public NoteModel createNote(String token, NoteDTO noteDto) {
		// TODO Auto-generated method stub
		return null;
	}
public Optional<UserModel> getDetailsEmail(String email){
	Optional<UserModel> user=userRepository.findByEmail(email);
	if(user!=null) {
		return user;
	}
	else {
		return null;
	}
}

}
