/* 역할: 객체의 이름을 보관하여 컴파일러나 JVM에게 전달한다.*/
package bitcamp.java89.ems.server.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 애노테이션 유지 정책
// => 애노테이션을 어느 수준으로 유지할 것인가?
// => 이 애노테이션을 컴파일할 때 .class 파일에 포함시키고,
//    실행할 때 이 애노케이션 값을 꺼낼 수 있게 한다.
@Retention(RetentionPolicy.RUNTIME)

public @interface Component {
  // 객체 이름을 저장할 프로퍼티를 선언한다.
  // 값을 설정하지 않으면 기본 값으로 빈 문자열을 자동 저장한다.
  String value() default "";
}
