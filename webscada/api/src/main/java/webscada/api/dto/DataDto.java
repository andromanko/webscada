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
public class DataDto {

    private long id;
    private int value; //допустим, пусть для начала будет int
    private String dataName; //наименование данных
    private String dataUnit;// единицы измерения;
    //устройство, адрес, формат, формат записи (##.###),  - еще предстоит добавить
}
