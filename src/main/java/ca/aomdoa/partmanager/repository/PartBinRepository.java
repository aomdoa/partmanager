/**
 * This file is part of PartManager.
 * Copyright 2014 David Shurgold <aomdoa@gmail.com>
 *
 * PartManager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * PartManager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PartManager.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.aomdoa.partmanager.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import ca.aomdoa.partmanager.domain.PartBin;

@RepositoryRestResource(collectionResourceRel = "bins", path = "bin")
public interface PartBinRepository extends PagingAndSortingRepository<PartBin, Long> {
	@Query("SELECT p FROM PartBin p WHERE lower(location) LIKE CONCAT('%',lower(:location),'%')")
	Page<PartBin> findByPartialLocation(@Param("location") String location, Pageable pageable);
	
	@Query("SELECT p FROM PartBin p WHERE lower(location) = lower(:location)")
	Collection<PartBin> findByExactLocation(@Param("location") String location);
	
	@Query("DELETE FROM PartBin WHERE lower(location) LIKE CONCAT('%',lower(:location),'%')")
	@Modifying
	@Transactional
	@RestResource(exported = false)
	int deleteByLocation(@Param("location") String location);
}
