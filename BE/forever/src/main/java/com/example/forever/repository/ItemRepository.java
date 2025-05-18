package com.example.forever.repository;

import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Item;
import com.example.forever.domain.ItemType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByOrderValueAsc();
    Optional<Item> findByTypeAndRefId(ItemType type, Long refId);
    List<Item> findByFolderOrderByOrderValueAsc(Folder folder);

    // ItemRepository
    @Query("""
    SELECT i FROM Item i
    WHERE
      (i.type = 'FILE' AND EXISTS (
        SELECT 1 FROM Document d WHERE d.id = i.refId AND d.member.id = :memberId AND d.isDeleted = false
      ))
      OR
      (i.type = 'FOLDER' AND EXISTS (
        SELECT 1 FROM Folder f WHERE f.id = i.refId AND f.createdBy = :memberId AND f.isDeleted = false
      ))
    ORDER BY i.orderValue ASC
""")
    List<Item> findAllByOwner(@Param("memberId") Long memberId);

    @Query("SELECT MIN(i.orderValue) FROM Item i")
    Optional<Integer> findMinOrderValue();
}
