package edu.whu.ScrumPM.service.user;


import edu.whu.ScrumPM.dao.user.User;

import java.util.List;

public interface UserRestService {

     User saveUser(User user);

     void deleteUser(Long id);

     void updateUser(User user);

     User getUser(String userName);
     User getUserByUserId(Long userId);

     List<User> getAll();
}