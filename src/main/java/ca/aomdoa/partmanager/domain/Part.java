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
package ca.aomdoa.partmanager.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class Part {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = false, unique = true, length = 255)
	@NotNull
    @NotEmpty
    @Length(max = 255)
    private String mfgPartNumber;
	
	@Column(length = 255)
	@Length(max = 255)
    private String retailerPartNumber;
	
	@Column(length = 255)
	@Length(max = 255)
    private String description;
    
	@ManyToOne
    private Footprint footprint;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="part")
	private Collection<PartDocument> documents;
	
	@ManyToMany
    private Collection<Keyword> keywords;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="part")
	private Collection<PartQuantity> quantities;
}
