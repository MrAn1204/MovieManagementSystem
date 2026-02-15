package com.mms.mms_api.data;

import com.mms.mms_api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    List<Genre> findByNameIn(List<String> names);

    int countByIdIn(Iterable<UUID> ids);

    default boolean existsAllByIdIn(List<UUID> ids) {
        return countByIdIn(ids) == ids.size();
    }
}