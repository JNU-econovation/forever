package com.example.forever.repository;

import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Item;
import com.example.forever.domain.ItemType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByOrderValueAsc();
    Optional<Item> findByTypeAndRefId(ItemType type, Long refId);
    List<Item> findByFolderOrderByOrderValueAsc(Folder folder);
}
