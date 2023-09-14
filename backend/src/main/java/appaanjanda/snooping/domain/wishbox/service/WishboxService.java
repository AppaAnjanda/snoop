package appaanjanda.snooping.domain.wishbox.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import appaanjanda.snooping.domain.wishbox.service.dto.SaveItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WishboxService {

	private final WishboxRepository wishboxRepository;

	public void save(Long id, SaveItemRequest request){

	}

}
