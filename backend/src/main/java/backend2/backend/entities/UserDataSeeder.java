//package backend2.backend.entities;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Service
//public class UserDataSeeder {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
//
//    public void Seed() {
//        if (roleRepository.findByName("Admin") == null) {
//            addRole("Admin");
//        }
//        if (roleRepository.findByName("Customer") == null) {
//            addRole("Customer");
//        }
//        if (userRepository.getUserByUsername("andreas.roos.music@gmail.com") == null) {
//            addUser("andreas.roos.music@gmail.com", "Admin");
//
//        }
//    }
//
//    private void AddUser(String mail, String group) {
//        ArrayList<Role> roles = new ArrayList<>();
//        roles.add(roleRepository.findByName(group));
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String hash = encoder.encode("password123");
//        User user = new User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
//        userRepository.save(user);
//    }
//
//    private void addRole(String name) {
//        Role role = new Role();
//        roleRepository.save(Role.builder().name(name).build());
//    }
//}
//
