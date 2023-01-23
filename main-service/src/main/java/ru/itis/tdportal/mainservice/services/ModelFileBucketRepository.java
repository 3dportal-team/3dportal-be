package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.models.entities.ModelFileBucket;
import ru.itis.tdportal.mainservice.properties.VkCloudBucketAccountProperty;
import ru.itis.tdportal.mainservice.properties.VkCloudBucketProperty;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelFileBucketRepository {

    private final VkCloudBucketProperty bucketProperties;
    private final VkCloudBucketAccountProperty bucketAccountProperties;

    private AwsBasicCredentials awsBasicCredentials;
    private S3Client s3Client;
    private S3Presigner s3Presigner;

    private void setUp() {
        if (awsBasicCredentials == null) {
            awsBasicCredentials = AwsBasicCredentials.create(
                    bucketAccountProperties.getAccessKey(),
                    bucketAccountProperties.getSecretKey()
            );
        }

        if (s3Client == null) {
            try {
                s3Client = S3Client.builder()
                        .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                        .region(Region.of(bucketProperties.getRegion()))
                        .endpointOverride(new URI(bucketProperties.getHotboxUrl()))
                        .build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if (s3Presigner == null) {
            try {
                s3Presigner = S3Presigner.builder()
                        .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                        .region(Region.of(bucketProperties.getRegion()))
                        .endpointOverride(new URI(bucketProperties.getHotboxUrl()))
                        .build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private List<S3Object> createListRequest(String prefix) {
        setUp();

        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .prefix(prefix)
                .bucket(bucketProperties.getName())
                .build();

        ListObjectsResponse res = s3Client.listObjects(listObjects);

        return res.contents();
    }

    public List<S3Object> findS3Files() {
        return createListRequest(null);
    }

    public ModelFileBucket saveFile(ModelFileBucket bucket) {
        setUp();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketProperties.getName())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(bucket.getOwnerEmail() + "/" + bucket.getGeneratedName())
                .build();

        try {
            PutObjectResponse response = s3Client.putObject(
                    objectRequest,
                    RequestBody.fromBytes(bucket.getFile())
            );

            bucket.setEntityTag(response.eTag());
            return bucket;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Incorrect file");
        }
    }

    public String findPreSignedUrlFromModelFile(ModelFile modelFile) {
        setUp();

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketProperties.getName())
                        .key(
                                modelFile.getOwner().getEmail() +
                                        "/" +
                                        modelFile.getGeneratedName()
                        )
                        .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(8))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest =
                s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }

    public String findPreSignedUrlFromS3Object(S3Object s3Object) {
        setUp();

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketProperties.getName())
                        .key(s3Object.key())
                        .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(8))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest =
                s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }

}
