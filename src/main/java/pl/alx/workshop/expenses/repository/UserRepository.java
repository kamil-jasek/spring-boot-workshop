package pl.alx.workshop.expenses.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.alx.workshop.expenses.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	List<User> findByName(String name);
}
