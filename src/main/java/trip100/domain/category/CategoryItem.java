//package trip100.domain.category;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import trip100.domain.item.Item;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class CategoryItem {
//
//    @Id @GeneratedValue
//    @Column(name = "category_item_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id")
//    private Item item;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;
//
//
//}
