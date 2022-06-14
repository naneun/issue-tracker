package com.team03.issuetracker.milestone.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team03.issuetracker.milestone.dto.MilestoneResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MilestoneRepositoryImpl implements MilestoneRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<MilestoneResponse> findAllMilestones() {
		//		queryFactory.select(new QMilestoneResponse(
		//				milestone.title,
		//
		//				))
		//
		//			.from(milestone)
		//			.leftJoin(milestone.issues, issue)
		//			.
		return null;
	}

}
/*```
	.select(new QBnbSimpleDto(
				bnb.id,
				(select(BnbImage.imageUrl).from(BnbImage).where(BnbImage.bnb_id.eqauls(bnb.id).limit(1))
				bnb.rating,
				bnb.reviewCount,
				bnb.name,
				bnb.fee))
```*/
