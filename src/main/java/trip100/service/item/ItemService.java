package trip100.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.web.dto.item.ItemListResponseDto;
import trip100.web.dto.item.ItemResponseDto;
import trip100.web.dto.item.ItemSaveRequestDto;
import trip100.web.dto.item.ItemUpdateRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public Long save(ItemSaveRequestDto requestDto) {

        return itemRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, ItemUpdateRequestDto requestDto) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이템이 없습니다.")
        );

        item.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getPrice(), requestDto.getAddress());

        return id;
    }

    public ItemResponseDto findById(Long id) {
        Item entity = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이템이 없습니다")
        );

        return new ItemResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ItemListResponseDto> findAllDesc() {
        return itemRepository.findAllDesc().stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이템이 없습니다")
        );

        itemRepository.delete(item);
    }
}
