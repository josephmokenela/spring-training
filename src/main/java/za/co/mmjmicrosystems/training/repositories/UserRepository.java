package za.co.mmjmicrosystems.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;



import za.co.mmjmicrosystems.training.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

	User findByForgotPasswordCode(String forgotPasswordCode);

}
