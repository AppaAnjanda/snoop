package appaanjanda.snooping.domain.wishbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import appaanjanda.snooping.domain.wishbox.entity.Wishbox;

public interface WishboxRepository extends JpaRepository<Wishbox, Long> {
}
