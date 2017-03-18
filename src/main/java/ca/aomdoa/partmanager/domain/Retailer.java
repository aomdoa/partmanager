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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import lombok.Data;

/**
 * Represents a part retailer like digikey or mouser
 * ca.aomdoa.partmanager.persistance.domain
 */
@Entity
@Data
@UniqueRetailer
public class Retailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @NotEmpty
    @Length(max = 255)
    private String name;
    
    @Column(nullable = false, length = 255)
    @NotNull
    @NotEmpty
    @URL
    private String url;
    
    @Column(length = 2048)
    @Length(max = 2048)
    private String notes;
}
