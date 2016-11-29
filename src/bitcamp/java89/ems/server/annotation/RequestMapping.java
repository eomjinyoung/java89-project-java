package bitcamp.java89.ems.server.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 실행할 때 이 애노테이션의 값을 꺼낼 수 있게 하자!
@Retention(RetentionPolicy.RUNTIME)

public @interface RequestMapping {
  String value() default "";
}
