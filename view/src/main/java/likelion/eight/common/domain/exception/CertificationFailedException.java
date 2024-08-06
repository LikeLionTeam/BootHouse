package likelion.eight.common.domain.exception;

public class CertificationFailedException extends RuntimeException {

    public CertificationFailedException() {
        super("자격 증명에 실패하였습니다.");
    }

    public CertificationFailedException(String description){
        super(description);
    }
}
