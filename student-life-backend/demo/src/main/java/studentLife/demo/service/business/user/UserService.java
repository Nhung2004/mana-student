package studentLife.demo.service.business.user;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import studentLife.demo.domain.file.FileEntity;
import studentLife.demo.domain.user.UserEntity;
import studentLife.demo.repository.file.FileRepository;
import studentLife.demo.repository.user.UserRepository;
import studentLife.demo.security.JwtUtil;
import studentLife.demo.service.ResponseDTO;
import studentLife.demo.service.base.BaseService;
import studentLife.demo.service.dto.file.FileDTO;
import studentLife.demo.service.dto.user.UserDTO;
import studentLife.demo.service.dto.user.crud.LoginDTO;
import studentLife.demo.service.dto.user.crud.LoginResponseDTO;
import studentLife.demo.service.dto.user.crud.RegisterDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public UserService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public ResponseDTO<UserDTO> registerUser(RegisterDTO registerDTO) {
        Optional<UserEntity> checkUser = userRepository.findByUserName(registerDTO.getUserName());
        if(checkUser.isPresent()){
            throw new ServiceException("Tên người dùng đã tồn tại " + registerDTO.getUserName());
        }
        if(!registerDTO.getPassword().equals(registerDTO.getRePassword())){
            throw new ServiceException("Xác nhận mật khẩu không đúng");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(registerDTO.getId());
        userEntity.setUserName(registerDTO.getUserName());
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setUniversity(registerDTO.getUniversity());
        userEntity.setMajor(registerDTO.getMajor());
        userEntity.setYearOfStudy(registerDTO.getYearOfStudy());
        userEntity.setPassword(registerDTO.getPassword());
        userEntity.setRePassword(registerDTO.getRePassword());

        UserEntity userSaved = userRepository.save(userEntity);

        ResponseDTO<UserDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus(String.valueOf(HttpStatus.OK));
        responseDTO.setData(UserDTO.toDTO(userSaved));

        return responseDTO;
    }

    @Transactional(readOnly = true)
    public ResponseDTO<LoginResponseDTO> loginUser(LoginDTO loginDTO) {
        UserEntity user = userRepository.findByUserName(loginDTO.getUserName())
                .orElseThrow(() -> new RuntimeException("Sai username hoặc password"));

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("Sai username hoặc password");
        }

        String token = JwtUtil.generateRefreshToken(user.getUserName());

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setUserDTO(UserDTO.toDTO(user));
        loginResponse.setToken(token);

        ResponseDTO<LoginResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus(String.valueOf(HttpStatus.OK.value()));
        responseDTO.setData(loginResponse);

        return responseDTO;
    }

    @Transactional(readOnly = true)
    public ResponseDTO<UserDTO> getUserProfile(String username) {
        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        ResponseDTO<UserDTO> response = new ResponseDTO<>();
        response.setStatus("200");
        response.setData(UserDTO.toDTO(user));
        return response;
    }
    @Transactional
    public ResponseDTO<UserDTO> updateProfile(String username, UserDTO userDTO) {
        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));


        user.setEmail(userDTO.getEmail());
        user.setUniversity(userDTO.getUniversity());
        user.setMajor(userDTO.getMajor());
        user.setYearOfStudy(userDTO.getYearOfStudy());

        UserEntity updatedUser = userRepository.save(user);

        ResponseDTO<UserDTO> response = new ResponseDTO<>();
        response.setStatus("200");
        response.setData(UserDTO.toDTO(updatedUser));
        return response;
    }

    @Transactional
    public ResponseDTO<String> changePassword(String username, String oldPassword, String newPassword, String reNewPassword) {
        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
        if (!newPassword.equals(reNewPassword)) {
            throw new RuntimeException("Xác nhận mật khẩu mới không khớp");
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        ResponseDTO<String> response = new ResponseDTO<>();
        response.setStatus("200");
        response.setData("Đổi mật khẩu thành công");
        return response;
    }

    // trả về url ( lộ hết file cấu trúc proj :)))))
//    @Transactional
//    public ResponseDTO<UserDTO> uploadAvatar(String username, MultipartFile file) {
//        UserEntity user = userRepository.findByUserName(username)
//                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
//
//        try {
//
//            String contentType = file.getContentType();
//            if (contentType == null || !contentType.startsWith("image")) {
//                throw new RuntimeException("File này không phải là file ảnh");
//            }
//
//
//            String uploadDir = "uploads/";
//
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//            Path dirPath = Paths.get(uploadDir);
//            Files.createDirectories(dirPath);
//
//            Path filePath = dirPath.resolve(fileName);
//
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            user.setAvataUrl(filePath.toString());
//            userRepository.save(user);
//
//            return ResponseDTO.<UserDTO>builder()
//                    .status("200")
//                    .message("Upload ảnh thành công")
//                    .data(UserDTO.toDTO(user))
//                    .build();
//
//        } catch (IOException e) {
//            throw new RuntimeException("Lỗi khi upload file: " + e.getMessage(), e);
//        }
//    }



    public ResponseDTO<FileDTO> uploadFile(String userName, MultipartFile file){
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        try {
            if(file.getContentType() == null || !file.getContentType().startsWith("image")){
                throw new RuntimeException("File này không phải là file ảnh");
            }

            String uploadDir = "upload/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFilePath(filePath.toString());
            fileEntity.setFileType(file.getContentType());
            fileEntity.setTargetId(user.getId());

            FileEntity fileSave = fileRepository.save(fileEntity);
            user.setAvataUrl(fileSave.getId());
            userRepository.save(user);

            FileDTO fileDTO = FileDTO.builder()
                    .id(fileSave.getId())
                    .fileName(fileSave.getFileName())
                    .filePath(fileSave.getFilePath())
                    .fileType(fileSave.getFileType())
                    .targetId(fileSave.getTargetId())
                    .build();

            return ResponseDTO.<FileDTO>builder()
                    .status("200")
                    .message("Upload file thành công")
                    .data(fileDTO)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload file: " + e.getMessage(), e);
        }
    }


}





