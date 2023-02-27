package trip100.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.item.Item;
import trip100.domain.item.ItemEditor;
import trip100.domain.item.ItemRepository;
import trip100.exception.ItemNotFoundException;
import trip100.web.dto.item.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public void save(ItemSaveRequestDto requestDto) {

        itemRepository.save(requestDto.toEntity()).getId();
    }

    public void update(Long id, ItemUpdateRequestDto requestDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);

        item.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getPrice(), requestDto.getStockQuantity());

    }

    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long id) {
        Item entity = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        return new ItemResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ItemListResponseDto> findAll(ItemSearch itemSearch) {
        return itemRepository.getListDESC(itemSearch).stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        itemRepository.delete(item);
    }
}
