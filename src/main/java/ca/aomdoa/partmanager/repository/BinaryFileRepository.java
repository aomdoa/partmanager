package ca.aomdoa.partmanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.aomdoa.partmanager.domain.BinaryFile;

@RepositoryRestResource(collectionResourceRel = "files", path = "file")
public interface BinaryFileRepository extends CrudRepository<BinaryFile, Long> {
	@Query("SELECT f FROM PartDocument p " +
		   "JOIN p.file f " +
		   "WHERE p.id = :document")
	public BinaryFile getDocumentFile(@Param("document") long document);
}
