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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.aomdoa.partmanager.domain.Part;

@RepositoryRestResource(collectionResourceRel = "parts", path = "part")
public interface PartRepository extends PagingAndSortingRepository<Part, Long> {
	@Query("SELECT DISTINCT p FROM Part p " + 
           "LEFT JOIN p.keywords k " +
		   "WHERE lower(p.mfgPartNumber) LIKE CONCAT('%',lower(:keyword),'%') OR " +
		   "lower(p.description) LIKE CONCAT('%',lower(:keyword),'%') OR " +
		   "lower(p.retailerPartNumber) LIKE CONCAT('%',lower(:keyword),'%') OR " + 
		   "lower(k.name) = lower(:keyword)")
	public Page<Part> fullSearch(@Param("keyword") Collection<String> keyword, Pageable pageable);

	@Query("SELECT DISTINCT p FROM Part p " +
		   "JOIN p.quantities pq " + 
		   "JOIN pq.storage pb " +
		   "WHERE lower(pb.bin) LIKE CONCAT('%',lower(:bin),'%')")
	public Page<Part> binSearch(@Param("bin") String bin, Pageable pageable);
}