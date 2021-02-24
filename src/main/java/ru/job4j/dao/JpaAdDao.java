package ru.job4j.dao;

import org.hibernate.jpa.QueryHints;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaAdDao<Ad> extends JpaGenericDao<Ad> {
    private final Class<Ad> type;

    public JpaAdDao(Class<Ad> type) {
        super(type);
        this.type = type;
    }

    @Override
    public Optional<Ad> read(long id) {
        return Optional.of(returnResult(entityManager ->
                entityManager.createQuery("select distinct a from Ad a"
                        + " join fetch a.user"
                        + " left join fetch a.photos"
                        + " join fetch a.car c"
                        + " join fetch c.engine"
                        + " join fetch c.model m"
                        + " join fetch m.brand"
                        + " join fetch c.rudder"
                        + " join fetch c.transmission"
                        + " join fetch c.body"
                        + " where a.id = :id", type)
                        .setParameter("id", id).getResultList().get(0)));
    }

    @Override
    public List<Ad> findWhereKey(String nameKey, String valueKey) {
        return returnResult(entityManager ->
                entityManager.createQuery("select distinct a from Ad a"
                        + " join fetch a.user u"
                        + " left join fetch a.photos p"
                        + " join fetch a.car c"
                        + " join fetch c.engine e"
                        + " join fetch c.model m"
                        + " join fetch m.brand b"
                        + " join fetch c.rudder r"
                        + " join fetch c.transmission t"
                        + " join fetch c.body "
                        + " where u.id = :id", type)
                        .setParameter("id", Long.parseLong(valueKey)).getResultList());
    }

    @Override
    public List<Ad> findAllLimit(long startPosition, int maxRow, Map<String, String> param) {
        LocalDateTime localDateTime = LocalDateTime.parse(param.get("date"));
        return returnResult(entityManager -> {
            List<Long> adIds = entityManager.createQuery(
                    "select distinct a.id from Ad a"
                            + param.get("left join") + " join a.photos p"
                            + " join a.car c"
                            + " join c.model m"
                            + " join m.brand b"
                            + " where a.id >= :id AND b.name LIKE :brand AND a.createdAd >= :date "
                            + param.get("photo") + " order by a.id", Long.class)
                    .setParameter("id", startPosition)
                    .setParameter("brand", param.get("brand"))
                    .setParameter("date", localDateTime)
                    .setMaxResults(maxRow)
                    .getResultList();
            return entityManager.createQuery("select distinct a from Ad a"
                    + " left join fetch a.photos p"
                    + " join fetch a.car c"
                    + " join fetch c.model m"
                    + " join fetch m.brand b"
                    + " where a.id IN (:id)", type)
                    .setParameter("id", adIds)
                    .setHint(
                            QueryHints.HINT_PASS_DISTINCT_THROUGH,
                            false
                    )
                    .getResultList();
        });
    }
}







