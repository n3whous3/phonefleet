package n3whous3.fleet.db.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import n3whous3.fleet.db.entities.StaticInfo;

public class StaticInfoDAO extends BaseDAO {
	
	public static List<StaticInfo> getAllStaticInfos() throws Exception {
		// TODO get last info before this date
		try {
			return singleTableQuery(StaticInfo.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	public static StaticInfo getLastStaticInfo(Date atOrBeforeDate) throws Exception {
		try {
			List<StaticInfo> staticInfoResult = singleTableQuery(StaticInfo.class, new SingleTableQueryCondition<StaticInfo>() {
				public void body(CriteriaBuilder critBuilder, CriteriaQuery<StaticInfo> criteria, Root<StaticInfo> root) {
					criteria.where(critBuilder.lessThanOrEqualTo(root.get("validity_date"), atOrBeforeDate));
					criteria.orderBy(critBuilder.desc(root.get("validity_date")));
				}
			});
			return staticInfoResult.isEmpty() ? null : staticInfoResult.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	public static void addStaticInfo(StaticInfo staticInfo) throws Exception {
		try {
			persistObject(staticInfo);
		} catch (Exception e) {
			// TODO logging
			throw e;
		}
	}
	
	public static void removeStaticInfo(StaticInfo staticInfo) throws Exception {
		try {
			removeObject(staticInfo);
		} catch (Exception e) {
			// TODO logging
			throw e;
		}
	}
	
}
