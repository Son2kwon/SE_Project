package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Tester")
public class Tester extends User{
        public Tester () {
            setRole("tester");
        }
}