package com.unitedvision.tvkabel.service;

import com.unitedvision.tvkabel.entity.Domain;
import com.unitedvision.tvkabel.exception.ApplicationException;
import com.unitedvision.tvkabel.exception.EntityNotExistException;

/**
 * Root Service.
 * 
 * @author Deddy Christoper Kakunsi
 *
 * @param <T> any domain type
 */
public interface Service<T extends Domain> {
	/**
	 * Save the given object to database. If it is a new object then insert it, if it is existing object then update it.
	 * @param domain
	 * @return Saved object.
	 * @throws ApplicationException any error that prevent the save process. It can be any child exception.
	 */
	T save(T domain) throws ApplicationException;
	
	/**
	 * Delete the given object from database.
	 * @param domain
	 * @throws ApplicationException 
	 */
	void delete(T domain) throws ApplicationException;

	void delete(Integer id) throws ApplicationException;

	/**
	 * Get object from database identified by id.
	 * @param id
	 * @return Object from database identified by id.
	 * @throws EntityNotExistException no object in database identified by id.
	 */
	T getOne(int id)  throws EntityNotExistException;
}
