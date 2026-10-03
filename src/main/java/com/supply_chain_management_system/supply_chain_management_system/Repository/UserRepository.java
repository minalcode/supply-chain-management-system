
package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name,
            String email,
            Pageable pageable
    );

    Optional<User> findByEmail(String email);
}