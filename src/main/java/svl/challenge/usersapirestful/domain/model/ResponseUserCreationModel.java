package svl.challenge.usersapirestful.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserCreationModel {
    private String id;
    private Date created;
    private Date modified;
    private Date last_login;
    private String token;
    private boolean isactive;

}
