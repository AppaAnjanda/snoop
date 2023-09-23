package appaanjanda.snooping.domain.card.service.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AddMyCardRequest {

	private final List<String> myCard = new ArrayList<>();
}
