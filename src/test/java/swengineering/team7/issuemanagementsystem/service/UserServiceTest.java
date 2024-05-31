package swengineering.team7.issuemanagementsystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.Admin;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown(){}

    @Test
    void testGetAllUser (){
        Admin admin = new Admin("id","admin","password","contract");
        User user1 = new User();
        user1.setId("1");

        User user2 = new User();
        user2.setId("2");

        User user3 = new User();
        user3.setId("3");

        User user4 = new User();
        user4.setId("4");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        List<UserInformationDTO> ans = new ArrayList<>();
        ans.add(UserInformationDTO.from(user1));
        ans.add(UserInformationDTO.from(user2));
        ans.add(UserInformationDTO.from(user3));
        ans.add(UserInformationDTO.from(user4));

        when(userRepository.findById("id")).thenReturn(Optional.of(admin));
        when(userRepository.findAll()).thenReturn(users);

        List<UserInformationDTO> get = userService.getAllUser(admin.getId());
        for(int i=0;i<4;i++)
        {
            assertEquals(ans.get(i).getId(), get.get(i).getId());
        }
    }

    @Test
    void testLogin(){
        UserInformationDTO test_1 = new UserInformationDTO();
        test_1.setId("test1");
        test_1.setPassword("password1");

        User t1 = new User();
        t1.setId("test1");
        t1.setPassword("password1");

        when(userRepository.findById("test1")).thenReturn(Optional.of(t1));

        assertTrue(userService.login(test_1));
    }

    @Test
    void testSearchName(){
        User u1 = new User();
        u1.setUsername("targetAAABBBCCC");

        User u2 = new User();
        u2.setUsername("targetDDDEEEGGG");

        List<User> ans = Arrays.asList(u1,u2);

        when(userRepository.findByUsernameContainingOrderByUsernameAsc("target")).thenReturn(ans);
        List<UserInformationDTO> tmp = userService.searchByUsername("target");
        assertEquals(2,ans.size());
        for(int i=0;i<2;i++){
            assertEquals(ans.get(i).getUsername(),tmp.get(i).getName());
        }

    }
}
