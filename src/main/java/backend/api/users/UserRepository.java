package backend.api.users;

import backend.domain.User;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface UserRepository extends RestfulJpaRepository<User,String>{

}
