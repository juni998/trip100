package trip100.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.BaseTimeEntity;
import trip100.domain.address.Address;
import trip100.domain.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private String picture;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String name, String email, String picture, Role role, Address address, List<Order> orders) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.address = address;
        this.orders = orders;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
