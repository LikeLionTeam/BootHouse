package likelion.eight.domain.user.service;

import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.common.service.port.S3Service;
import likelion.eight.mock.FakeS3Service;
import likelion.eight.mock.TestMultiPartFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class S3ServiceTest {

    private S3Service s3Service;

    @BeforeEach
    void init(){
        s3Service = new FakeS3Service();
    }

    @Test
    void saveFile메서드는_jpg_png_jpeg_확장자_파일만_저장할_수_있다(){

        //then
        Assertions.assertThatCode( ()->
                s3Service.saveFile(new TestMultiPartFile("sample", "test.jpg",
                        "image/jpeg", "Dummy Image Content".getBytes()))
        ).doesNotThrowAnyException();

        Assertions.assertThatCode( ()->
                s3Service.saveFile(new TestMultiPartFile("sample", "test.png",
                        "image/jpeg", "Dummy Image Content".getBytes()))
        ).doesNotThrowAnyException();

        Assertions.assertThatCode( ()->
                s3Service.saveFile(new TestMultiPartFile("sample", "test.jpeg",
                        "image/jpeg", "Dummy Image Content".getBytes()))
        ).doesNotThrowAnyException();
    }


    @Test
    void saveFile메서드는_jpg_png_jpeg_확장자_파일을_제외한_파일은_저장할_수_없다(){

        //then
        Assertions.assertThatThrownBy( ()->
                s3Service.saveFile(new TestMultiPartFile("sample", "test.txt",
                        "image/jpeg", "Dummy Image Content".getBytes()))
        ).isInstanceOf(CertificationFailedException.class);
    }
}
