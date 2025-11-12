package studentLife.demo.service.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import studentLife.demo.domain.user.UserEntity;
import studentLife.demo.service.AbstractDTO;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends AbstractDTO<String> {

    private String id;
    private String userName;
    private String email;
    private String university;
    private String major;
    private Integer yearOfStudy;
    private String password;
    private String rePassword;
    private String avataUrl;

    public static UserDTO toDTO(UserEntity userEntity) {
        if(userEntity == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setUniversity(userEntity.getUniversity());
        userDTO.setMajor(userEntity.getMajor());
        userDTO.setYearOfStudy(userEntity.getYearOfStudy());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setRePassword(userEntity.getRePassword());
        userDTO.setCreatedBy(userEntity.getCreatedBy());
        userDTO.setCreatedDate(userEntity.getCreatedDate());
        userDTO.setLastModifiedBy(userEntity.getLastModifiedBy());
        userDTO.setLastModifiedDate(userEntity.getLastModifiedDate());
        userDTO.setAvataUrl(userEntity.getAvataUrl());
        return userDTO;
    }

}
