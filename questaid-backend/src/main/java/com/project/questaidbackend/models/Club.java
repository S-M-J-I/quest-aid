package com.project.questaidbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "club")
@Table(name = "club")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NonNull
    @Column(name = "club_name", unique = true, nullable = false)
    private String clubName;

    @NonNull
    @Column(name = "club_logo_path")
    private String clubLogoPath;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(unique = true, nullable = false)
    private String phone;

    // * a one-to-many mapping to club members class
//    @JsonIgnore
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, targetEntity = ClubMember.class)
    private List<ClubMember> clubMembers;

    // * a one-to-many mapping to club departments class
//    @JsonIgnore
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, targetEntity = ClubDepartment.class)
    private List<ClubDepartment> clubDepartments;

    // * a one-to-many mapping to task class
    @JsonIgnore
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, targetEntity = Task.class)
    private List<Task> memberTasks;

    // * a one-to-many mapping to transaction class
    @JsonIgnore
    @OneToMany(mappedBy = "payedTo", cascade = CascadeType.PERSIST, targetEntity = Transaction.class)
    private List<Transaction> transactions;

    // * a one-to-many mapping to event class
    @JsonIgnore
    @OneToOne(mappedBy = "organizer", cascade = CascadeType.PERSIST, targetEntity = Event.class)
    private List<Event> events;

}
