package neordinary.dofarming.utils.s3.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class S3UploadRequest {

    private Long userId;
    private String dirName;
}
