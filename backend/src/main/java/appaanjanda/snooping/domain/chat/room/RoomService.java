package appaanjanda.snooping.domain.chat.room;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomService {

	private final RoomRepository roomRepository;

	public void save(){

		String[] category = {"디지털가전", "가구", "생활용품", "식품"};

		for(int i=1; i<4 + 1; i++) {
			Room room = Room.builder()
				.roomId(i)
				.category(category[i-1])
				.build();

			roomRepository.save(room);
		}
	}
}
