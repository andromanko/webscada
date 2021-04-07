package webscada.api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevDto {

    private long id;
    private String devName;
    private String IP;
    private byte addr;
    private short port;
    private DevTypeDto devType;
}
