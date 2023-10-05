package appaanjanda.snooping.domain.card.service.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UpdateMyCardRequest {

	private final List<String> myCard = new ArrayList<>();
}
