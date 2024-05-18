package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.service.UserService;

import java.util.Set;

@Entity
@Table(name = "User")
public class Admin extends User{

}