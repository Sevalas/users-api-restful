package svl.challenge.usersapirestful.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import svl.challenge.usersapirestful.domain.entity.Phones;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUserUpdateModel {
    
    private String name;
    
    private String email;
    
    private String password;

    private List<Phones> phones;
}
