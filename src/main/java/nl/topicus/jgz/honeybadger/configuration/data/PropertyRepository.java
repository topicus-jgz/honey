/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.topicus.jgz.honeybadger.configuration.data;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nl.topicus.jgz.honeybadger.configuration.model.Property;

@ApplicationScoped
public class PropertyRepository {

	@Inject
	private EntityManager em;

	public Property findById(Long id) {
		return em.find(Property.class, id);
	}

	public Property findByService(String service) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Property> criteria = cb.createQuery(Property.class);
		Root<Property> member = criteria.from(Property.class);
		criteria.select(member).where(cb.equal(member.get("service"), service));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<Property> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Property> criteria = cb.createQuery(Property.class);
		Root<Property> member = criteria.from(Property.class);
		criteria.select(member).orderBy(cb.asc(member.get("service")));
		return em.createQuery(criteria).getResultList();
	}
}
