package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u JOIN RespondedApplicant ra ON u.id = ra.resumeId WHERE ra.vacancyId = :vacancyId")
    List<User> findUsersRespondedToVacancy(@Param("vacancyId") Integer vacancyId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.id = :userId")
    void updateAvatar(@Param("userId") Integer userId, @Param("avatar") String avatar);
}
