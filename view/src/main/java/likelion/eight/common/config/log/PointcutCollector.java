package likelion.eight.common.config.log;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class PointcutCollector {

    @Pointcut("execution(* likelion.eight.domain..*Service.get(..))")
    public void allServiceGetMethod(){}

    @Pointcut("execution(* likelion.eight.domain..*Service.find*(..))")
    public void allServiceFindMethod(){}

    @Pointcut("execution(* likelion.eight.common.config.s3.*(..))")
    public void s3Service(){}

    @Pointcut("!execution(* likelion.eight.domain.chat.. *(..))")
    public void excludeChart(){}

}
