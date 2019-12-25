//package com.bridgelabz.springboot;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import com.bridgelabz.fundo.model.NoteModel;
//import com.bridgelabz.fundo.model.UserModel;
//import com.bridgelabz.fundo.repository.NoteRepository;
//import com.bridgelabz.fundo.repository.UserRepository;
//import com.bridgelabz.fundo.service.NoteServiceImplementation;
//import com.bridgelabz.fundo.service.UserServiceImplementation;
//
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class LoginTest {
//@InjectMocks
//private NoteServiceImplementation noteservice;
//
//@Mock
//private NoteRepository noteRepository;
//@Mock
//private UserRepository userRepository;
//@Mock
//private UserServiceImplementation userservie;
//
//@Mock
//private NoteServiceImplementation colabService;
//


//@Test
//public void retriveUserFromDatabaseTest() {
//String token="uioi";
//noteservice.retrieveAllNode(token);
//
//
//List<NoteModel> details =new ArrayList<NoteModel>();
//NoteDetails notedetails= new NoteDetails();
//notedetails.setDescription("mi");
//details.add(notedetails);
//
//when(noteRepository.fetchNotesByUserId(utils.parseToken(token))).thenReturn(details);
//when(colabService.getCollaboratedNoteList(utils.parseToken(token))).thenReturn(details);
//
//    assertEquals(2, noteservice.fetchAllNotes(token).size());
//}


//@Test
//public void loginTest() {
//
//String email="test@gmail.com";
//Optional<UserModel> listofusers=new ArrayList<UserModel>();
//UserModel user =new UserModel();
//user.setEmail("test@gmail.com");
//listofusers.add(user);
//when(userRepository.findByEmail(email)).thenReturn(listofusers);
////when repository line will come in service layer,
////then mockito will return its own objects
//List<UserModel> excepteddata=userRepository.findByEmail(email);
//assertEquals(excepteddata,listofusers);
//assertEquals(1, excepteddata.size());
//}
//
//}
