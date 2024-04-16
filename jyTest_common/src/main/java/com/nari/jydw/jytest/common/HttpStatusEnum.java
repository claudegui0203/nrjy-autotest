package com.nari.jydw.jytest.common;

public enum HttpStatusEnum {
    //1xxInformational1xx信息类的
    CONTINUE(100,"Continue"),
    //交换协议
    SWITCHING_PROTOCOLS(101,"SwitchingProtocols"),
    //正在处理中
    PROCESSING(102,"Processing"),

    CHECKPOINT(103,"Checkpoint"),

    //2xxSuccess
    //成功
    OK(200,"OK"),
    //创建
    CREATED(201,"Created"),
    //接受
    ACCEPTED(202,"Accepted"),

    NON_AUTHORITATIVE_INFORMATION(203,"Non-AuthoritativeInformation"),

    NO_CONTENT(204,"NoContent"),

    RESET_CONTENT(205,"ResetContent"),

    PARTIAL_CONTENT(206,"PartialContent"),

    MULTI_STATUS(207,"Multi-Status"),

    ALREADY_REPORTED(208,"AlreadyReported"),

    IM_USED(226,"IMUsed"),

    //3xxRedirection重定向

    MULTIPLE_CHOICES(300,"MultipleChoices"),
    //永久移除掉了
    MOVED_PERMANENTLY(301,"MovedPermanently"),

    FOUND(302,"Found"),
    //暂时移除掉了已经废弃了
    @Deprecated
    MOVED_TEMPORARILY(302,"MovedTemporarily"),

    SEE_OTHER(303,"SeeOther"),

    NOT_MODIFIED(304,"NotModified"),
    //使用代理已经废除掉了
    @Deprecated
    USE_PROXY(305,"UseProxy"),
    //暂时的重定向了
    TEMPORARY_REDIRECT(307,"TemporaryRedirect"),
    //永久的重定向了
    PERMANENT_REDIRECT(308,"PermanentRedirect"),

    //---4xxClientError---4xx客户端错误

    BAD_REQUEST(400,"BadRequest"),
    //没有认证权限
    UNAUTHORIZED(401,"Unauthorized"),

    PAYMENT_REQUIRED(402,"PaymentRequired"),
    //禁止
    FORBIDDEN(403,"Forbidden"),

    NOT_FOUND(404,"NotFound"),
    //方法不允许
    METHOD_NOT_ALLOWED(405,"MethodNotAllowed"),

    NOT_ACCEPTABLE(406,"NotAcceptable"),

    PROXY_AUTHENTICATION_REQUIRED(407,"ProxyAuthenticationRequired"),
    //请求超时
    REQUEST_TIMEOUT(408,"RequestTimeout"),
    //冲突
    CONFLICT(409,"Conflict"),

    GONE(410,"Gone"),

    LENGTH_REQUIRED(411,"LengthRequired"),

    PRECONDITION_FAILED(412,"PreconditionFailed"),

    PAYLOAD_TOO_LARGE(413,"PayloadTooLarge"),

    @Deprecated
    REQUEST_ENTITY_TOO_LARGE(413,"RequestEntityTooLarge"),

    URI_TOO_LONG(414,"URITooLong"),

    @Deprecated
    REQUEST_URI_TOO_LONG(414,"Request-URITooLong"),

    UNSUPPORTED_MEDIA_TYPE(415,"UnsupportedMediaType"),

    REQUESTED_RANGE_NOT_SATISFIABLE(416,"Requestedrangenotsatisfiable"),

    EXPECTATION_FAILED(417,"ExpectationFailed"),

    I_AM_A_TEAPOT(418,"I'mateapot"),

    @Deprecated
    INSUFFICIENT_SPACE_ON_RESOURCE(419,"InsufficientSpaceOnResource"),

    @Deprecated
    METHOD_FAILURE(420,"MethodFailure"),

    @Deprecated
    DESTINATION_LOCKED(421,"DestinationLocked"),

    UNPROCESSABLE_ENTITY(422,"UnprocessableEntity"),

    LOCKED(423,"Locked"),

    FAILED_DEPENDENCY(424,"FailedDependency"),

    UPGRADE_REQUIRED(426,"UpgradeRequired"),

    PRECONDITION_REQUIRED(428,"PreconditionRequired"),

    TOO_MANY_REQUESTS(429,"TooManyRequests"),

    REQUEST_HEADER_FIELDS_TOO_LARGE(431,"RequestHeaderFieldsTooLarge"),

    UNAVAILABLE_FOR_LEGAL_REASONS(451,"UnavailableForLegalReasons"),


    INTERNAL_SERVER_ERROR(500,"InternalServerError"),

    NOT_IMPLEMENTED(501,"NotImplemented"),

    BAD_GATEWAY(502,"BadGateway"),

    SERVICE_UNAVAILABLE(503,"ServiceUnavailable"),

    GATEWAY_TIMEOUT(504,"GatewayTimeout"),

    HTTP_VERSION_NOT_SUPPORTED(505,"HTTPVersionnotsupported"),

    VARIANT_ALSO_NEGOTIATES(506,"VariantAlsoNegotiates"),

    INSUFFICIENT_STORAGE(507,"InsufficientStorage"),

    LOOP_DETECTED(508,"LoopDetected"),

    BANDWIDTH_LIMIT_EXCEEDED(509,"BandwidthLimitExceeded"),

    NOT_EXTENDED(510,"NotExtended"),

    NETWORK_AUTHENTICATION_REQUIRED(511,"NetworkAuthenticationRequired");

    private HttpStatusEnum(int code,String name)
    {
        this.code=code;
        this.name=name;
    }
    private int code;
    private String name;
    public int getCode(){
        return code;
    }
    public void setCode(int code){
        this.code=code;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

}
