package edu.whu.ScrumPM.service.user;



import edu.whu.ScrumPM.dao.user.User;
import edu.whu.ScrumPM.dao.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserRestJPAServiceImpl implements UserRestService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {

        userRepository.save(user);

        return  user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);

        return user.get();
    }

    @Override
    public User getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        return user.get();
    }
}
