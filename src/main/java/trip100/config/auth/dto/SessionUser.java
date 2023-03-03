package trip100.config.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.user.User;

import java.io.Serializable;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionUser implements Serializable {

    private Long id;
    private String name;
    private String email;

    private String picture;

    @Builder
    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
