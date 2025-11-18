package backend.api.items;

import java.util.UUID;

import backend.domain.Item;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface ItemRepository extends RestfulJpaRepository<Item,UUID>{

}
