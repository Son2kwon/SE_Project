package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Tester")
@Inheritance(strategy = InheritanceType.JOINED)
public class Tester extends User{
        public Tester () {
            setRole("tester");
        }
}