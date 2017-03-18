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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.aomdoa.partmanager.repository.RetailerRepository;

@Component
public class UniqueRetailerValidator implements ConstraintValidator<UniqueRetailer, Retailer> {

    @Autowired
    private RetailerRepository repository;
    
    @Override
    public void initialize(UniqueRetailer constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(Retailer value, ConstraintValidatorContext context) {
        if(repository == null || value == null) {
            return true;
        }
        Retailer found = repository.findByName(value.getName());
        if(found != null && found.getId() != value.getId()) {
            return false;
        }
        return true;
    }

}
