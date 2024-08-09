package likelion.eight.mock;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    private final AtomicLong id = new AtomicLong(0);
    private final List<User> data = new ArrayList<>();


    @Override
    public User save(User user) {
        if(user.getId() == null || user.getId() == 0){
            User newUser = User.builder()
                    .id(id.incrementAndGet())
                    .name(user.getName())
                    .address(user.getAddress())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .phoneNumber(user.getPhoneNumber())
                    .certificationCode(user.getCertificationCode())
                    .userStatus(user.getUserStatus())
                    .roleType(user.getRoleType())
                    .image(user.getImage())
                    .build();
            data.add(newUser);
            return newUser;
        }else{
            data.removeIf(u -> Objects.equals(u.getId(), user.getId()));
            data.add(user);
            return user;
        }
    }

    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public Optional<User> findById(long id) {
        return data.stream().filter(u -> u.getId().equals(id)).findAny();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return data.stream().filter(u -> u.getEmail().equals(email) &&
                u.getPassword().equals(password)).findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return data.stream().filter(u -> u.getEmail().equals(email)).findAny();
    }
}
