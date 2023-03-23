package svl.challenge.usersapirestrful.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import svl.challenge.usersapirestrful.domain.entity.Phones;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResposeUserModel {

    private String id;
    private String name;
    private String email;
    private List<Phones> phones;
    private Date created;
    private Date modified;
    private Date last_login;
    private String token;
    private boolean isactive;
}
