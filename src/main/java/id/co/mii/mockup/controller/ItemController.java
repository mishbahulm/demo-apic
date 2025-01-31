package id.co.mii.mockup.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.mockup.dto.request.Item;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final Map<Long, Item> itemStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    // Create Item
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        Long id = idCounter.incrementAndGet();
        item.setId(Long.toString(id));
        itemStorage.put(id, item);
        return item;
    }

    // Read All Items
    @GetMapping
    public Collection<Item> getAllItems() {
        return itemStorage.values();
    }

    // Read Item by ID
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemStorage.get(id);
    }

    // Update Item
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        if (itemStorage.containsKey(id)) {
            updatedItem.setId(Long.toString(id));
            itemStorage.put(id, updatedItem);
            return updatedItem;
        }
        throw new NoSuchElementException("Item not found");
    }

    // Delete Item
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        if (itemStorage.remove(id) != null) {
            return "Item deleted successfully";
        }
        throw new NoSuchElementException("Item not found");
    }
}
