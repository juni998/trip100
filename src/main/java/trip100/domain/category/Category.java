//package trip100.domain.category;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import trip100.domain.item.Item;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class Category {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = " category_id")
//    private Long id;
//
//    private String name;
//
//    @OneToMany(mappedBy = "item")
//    private List<Item> items = new ArrayList<>();
//
//    @OneToMany(mappedBy = "parent")
//    private List<Category> child = new ArrayList<>();
//
//
//
//
//}
