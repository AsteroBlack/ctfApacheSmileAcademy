package ci.smile.simswaporange.dao.repository;

import ci.smile.simswaporange.dao.entity.ZIPCodes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipCOdeRepository  extends CrudRepository<ZIPCodes,Long> {
}