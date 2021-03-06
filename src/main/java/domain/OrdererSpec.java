package domain;

import jpasepc.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OrdererSpec implements Specification<Order> {
	private String ordererId;

	public OrdererSpec(String ordererId) {
		this.ordererId = ordererId;
	}

	@Override
	public Predicate toPredicate(Root<Order> root, CriteriaBuilder cb) {
		return cb.equal(root.get(Order_.orderer)
				.get(Orderer_.memberId).get(MemberId_.id), ordererId);

	}
}