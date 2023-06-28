package com.bestseller.coffee.store.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bestseller.coffee.store.entity.Toppings;

@Repository
public interface ToppingsRepository extends JpaRepository<Toppings, Long> {

	Optional<Toppings> findByIdAndIsDeleted(Long id, String stringNo);

	Optional<Toppings> findByNameIgnoreCase(String name);

	@Query("select t.cost from Toppings t where t.id in :toppingIdList")
	List<String> findPriceForEachTopping(@Param("toppingIdList") List<Long> toppingIdList);

	@Modifying
	@Transactional
	@Query("update Toppings t set t.usedCount = t.usedCount + 1 where t.id = :toppingId")
	void updateToppingUsedCount(@Param("toppingId") Long toppingId);

	@Query("select name from Toppings  where usedCount =(select max(usedCount) from Toppings where usedCount>0)")
	List<String> maxUsedToppings();
}
