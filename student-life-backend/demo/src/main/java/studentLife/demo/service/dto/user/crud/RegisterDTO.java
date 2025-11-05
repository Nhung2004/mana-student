package studentLife.demo.service.dto.user.crud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import studentLife.demo.domain.user.UserEntity;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO {
    private String id;
    private String userName;
    private String email;
    private String university;
    private String major;
    private Integer yearOfStudy;
    private String password;
    private String rePassword;

    public static UserEntity toEntity(RegisterDTO registerDTO) {
        if(registerDTO == null) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(registerDTO.getId());
        userEntity.setUserName(registerDTO.getUserName());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setUniversity(registerDTO.getUniversity());
        userEntity.setMajor(registerDTO.getMajor());
        userEntity.setYearOfStudy(registerDTO.getYearOfStudy());
        userEntity.setPassword(registerDTO.getPassword());
        userEntity.setRePassword(registerDTO.getRePassword());
        return userEntity;
    }
}
