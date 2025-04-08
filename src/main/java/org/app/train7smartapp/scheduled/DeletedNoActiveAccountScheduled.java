package org.app.train7smartapp.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DeletedNoActiveAccountScheduled {

    private final UserRepository userRepository;

    public DeletedNoActiveAccountScheduled(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Вече съществуващия код...

    // Планирана задача, която ще се изпълнява всяка нощ (например, всеки ден в 2:00 ч.)
    @Scheduled(cron = "0 0 2 * * ?")  // Изпълнява се всеки ден в 2:00 ч. сутринта
    public void deleteInactiveUsers() {
        // Търси потребители с неактивен статус
        List<User> inactiveUsers = userRepository.findByIsActive(false);

        // Изтрива ги от базата данни
        for (User user : inactiveUsers) {
            log.info("Deleting inactive user with id: {}", user.getId());
            userRepository.delete(user);
        }
    }
}
