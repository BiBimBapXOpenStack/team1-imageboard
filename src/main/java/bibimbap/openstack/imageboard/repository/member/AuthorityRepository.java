package bibimbap.openstack.imageboard.repository.member;

import bibimbap.openstack.imageboard.domain.member.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityName(String name);
}
