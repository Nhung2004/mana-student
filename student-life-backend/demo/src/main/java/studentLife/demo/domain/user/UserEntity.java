package studentLife.demo.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import studentLife.demo.domain.AbstractEntity;
import studentLife.demo.domain.course.CourseEntity;
import studentLife.demo.domain.expense.ExpenseEntity;
import studentLife.demo.domain.task.TaskEntity;


import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "user_name")
    private String userName;
    @Column(name= "email")
    private String email;
    @Column(name = "university")
    private String university;
    @Column(name = "major")
    private String major;
    @Column(name = "year_of_study")
    private Integer yearOfStudy;
    @Column(name = "password")
    private String password;
    @Column(name = "repassword")
    private String rePassword;
    @Column(name= "avata_url")
    private String avataUrl;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEntity> courses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseEntity> expenses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEntity> tasks;
}
