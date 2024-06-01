package neordinary.dofarming.utils.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neordinary.dofarming.common.exceptions.BaseException;
import neordinary.dofarming.utils.s3.dto.S3UploadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static neordinary.dofarming.common.code.status.ErrorStatus.FILE_CONVERT;
import static neordinary.dofarming.common.code.status.ErrorStatus.S3_UPLOAD;


@Slf4j
@RequiredArgsConstructor
@Component
public class S3Provider {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String multipartFileUpload(MultipartFile file, S3UploadRequest request) {
        String fileName = request.getUserId() + File.separator + request.getDirName() +  File.separator + UUID.randomUUID() + "_" + file.getOriginalFilename();

        TransferManager tm = TransferManagerBuilder.standard()
                .withS3Client(amazonS3Client)
                .build();
        try {
            InputStream is = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            Upload upload = tm.upload(bucket, fileName, is, objectMetadata);
            try {
                upload.waitForCompletion();
            } catch (InterruptedException e) {
                log.error("S3 multipartFileUpload error", e);
                throw new BaseException(S3_UPLOAD);
            }
        } catch (IOException e) {
            log.error("File convert error", e);
            throw new BaseException(FILE_CONVERT);
        } finally {
            tm.shutdownNow();
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

}