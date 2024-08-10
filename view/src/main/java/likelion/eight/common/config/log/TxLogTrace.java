package likelion.eight.common.config.log;

import likelion.eight.common.service.LogService;
import likelion.eight.common.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TxLogTrace {

    private final LogService logService;
    private final ClockHolder clockHolder;
    private final PointcutCollector pointcutCollector;

    @Pointcut("execution(* likelion.eight.domain..*Service.get(..))")
    private void allServiceGetMethod(){}

    @Pointcut("execution(* likelion.eight.domain..*Service.find*(..))")
    private void allServiceFindMethod(){}

    @Pointcut("execution(* likelion.eight.common.config.s3.*(..))")
    private void s3Service(){}

    @Pointcut("!execution(* likelion.eight.domain.chat.. *(..))")
    private void excludeChart(){}

    //@Pointcut("execution(* likelion.eight.domain.user.service.UserService.*(..))")
    //@Pointcut("execution(* likelion.eight.common.service.CookieService.*(..))")
    @Pointcut("execution(* likelion.eight.domain..*(..))")
    public void test(){}

    //@Around("allServiceGetMethod() || allServiceFindMethod()")
    //@Around("execution(* likelion.eight.domain..*(..))")
    //@Around("pointcutCollector.allServiceGetMethod()")
    @Around("execution(* likelion.eight.domain..*Service.*(..))")
    public Object transactionLog(ProceedingJoinPoint joinPoint) throws Throwable{
        //long startTime = System.currentTimeMillis();

        long startTime = clockHolder.systemMillis();

        try{
            log.info("[Transaction start] {}", joinPoint.getSignature());

            Object result = joinPoint.proceed();

            log.info("[Transaction commit] {}", joinPoint.getSignature());
            return result;
        }catch(Exception e){
            log.info("[Transaction rollback] {}", joinPoint.getSignature());
            throw e;
        }finally {

            String methodName = joinPoint.getSignature().toShortString();
            long executionTime = clockHolder.systemMillis() - startTime; //TODO +100L TestTime
            log.info("long executionTime={}", executionTime);
            logService.saveTxLog(methodName, executionTime);
        }
    }
}
