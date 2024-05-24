package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin")
@Inheritance(strategy = InheritanceType.JOINED)
public class Admin extends User{
        public Admin() {
            setRole("admin");
        }
}