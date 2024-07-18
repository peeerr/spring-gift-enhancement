package gift.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    /* Validation */
    VALIDATION_ERROR("입력 데이터의 유효성을 검사하던 중 문제가 발생했습니다.", HttpStatus.BAD_REQUEST),

    /* Product */
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND),

    /* Member */
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND),

    /* JWT */
    INVALID_TOKEN("유효하지 않거나 만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    MISSING_TOKEN("헤더에 토큰이 존재하지 않거나 잘못된 형식입니다.", HttpStatus.UNAUTHORIZED),

    /* MemberService */
    DUPLICATED_EMAIL("중복된 이메일입니다.", HttpStatus.CONFLICT),
    LOGIN_FAILURE("이메일 또는 비밀번호가 일치하지 않습니다.", HttpStatus.FORBIDDEN),

    /* WishlistService */
    PRODUCT_ALREADY_IN_WISHLIST("이미 위시리스트에 추가된 상품입니다.", HttpStatus.CONFLICT),
    PRODUCT_NOT_IN_WISHLIST("이미 위시리스트에 존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND),

    /* CategoryService */
    CATEGORY_NOT_FOUND("존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_NOT_DUPLICATES("이미 존재하는 카테고리 이름입니다.", HttpStatus.CONFLICT),

    /* ProductService */
    DUPLICATE_OPTION("중복된 옵션입니다.", HttpStatus.CONFLICT),
    OPTION_NOT_FOUND("존재하지 않는 옵션입니다.", HttpStatus.NOT_FOUND),
    AT_LEAST_ONE_OPTION_REQUIRED("상품에는 반드시 하나 이상의 옵션이 있어야 합니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getStatus() {
        return status;
    }

}