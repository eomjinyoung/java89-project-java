# v2.9
####구현할 기능 및 산출물:
DBMS를 이용하여 데이터를 관리한다.
- JDBC 드라이버 준비
  - 프로젝트 폴더 아래에 libs 폴더를 생성한 후 
    해당 폴더에 JDBC 드라이버 파일을 둔다.
  - Java Build Path에 이 드라이버의 경로를 추가한다.
- DAO 클래스 이름 변경: 향후 다양한 형태의 데이터 저장을 지원하기 위해
  - AbstractDao.java 파일을 AbstractFileDao.java로 변경
  - ContactDao.java 파일을 ContactFileDao.java로 변경
  - StudentDao.java 파일을 StudentFileDao.java로 변경
- MySQL DBMS를 사용하는 DAO 생성  
  - ContactDao.java 인터페이스 생성: 컨트롤러와 DAO 사이의 호출 규칙을 정의.
  - ContactFileDao.java는 이 인터페이스를 구현한다.
  - ContactMySQLDao.java(생성)는 이 인터페이스를 구현한다.
- 클래스 관리를 효율적으로 하기 위해
  - dao 패키지 아래에 impl 패키지를 생성한다.
  - impl 패키지 아래에 ContactDao 인터페이스를 구현한 클래스를 둔다. 
- Controller 변경
  - Controller가 의지하는 Dao를 가리킬 때 클래스 이름 대신 인터페이스를 사용한다.
  - ContactController.java (변경)
   
####학습목표:
- JDBC 프로그래밍을 익힌다.


# v2.8
####구현할 기능 및 산출물:
파라미터 선언을 자유롭게 하자! 메서드의 파라미터 정보를 분석하여 필요한 값만 전달한다.
- 파라미터에 붙일 애노테이션을 생성한다.
  - RequestParam.java (생성)
- Controller 변경
  - 각 메서드 별로 파라미터에 RequestParam 애노테이션을 붙인다.
- RequestThread.java (변경)
  - 메서드를 호출할 때 파라미터 정보를 분석하여 값을 준비한다. 
  
####학습목표:
- 파라미터에 애노테이션을 적용할 수 있다.
- 파라미터에 적용된 애노테이션의 값을 추출할 수 있다.
 

# v2.7
####구현할 기능 및 산출물:
메서드 대통합! 유사 업무를 처리하는 메서드끼리 한 클래스에서 관리한다.
- ContactController.java (생성)
  - Contact 관련 클래스들의 메서드를 모두 이 클래스에 넣는다.
- StudentController.java (생성)
  - Student 관련 클래스들의 메서드를 모두 이 클래스에 넣는다.
- HelloController.java (생성)
  - Hello 관련 클래스들의 메서드를 모두 이 클래스에 넣는다.
- RequestHandlerMapping.java (생성)
  - 명령어를 처리할 메서드를 관리한다.
- RequestHandler.java (중첩 클래스 생성)
  - 메서드와 인스턴스를 보관한다.
- EduAppServer.java (변경)
  - 객체 준비 뿐만 아니라 명령어를 처리할 메서드 연결 정보를 생성한다.
- RequestThread.java (변경)
  - RequestHandlerMapping을 이용하여 명령어를 찾는다.
  - RequestHandler를 이용하여 메서드를 호출한다.  
  
####학습목표:
- 애노테이션 활용법을 익힌다.
- HashMap 활용법을 익힌다.
- 중첩 클래스 활용법을 익힌다.
- Reflection API 활용법을 익힌다.
 


# v2.6
####구현할 기능 및 산출물:
메서드에 애노테이션 적용하기 
- RequestMapping.java 애노테이션을 정의한다.
- Command.java (삭제)
- AbstractCommand.java (삭제)
- Controller 클래스 변경
  - AbstractCommand 상속을 제거한다.
  - doResponse() 메서드의 이름을 적절히 바꾼다.
  - @RequestMapping 애노테이션을 메서드 앞에 붙인다.
- RequestThread.java (변경)
  - 명령어를 처리할 때 @RequestMapping 애노테이션이 붙은 메서드를 호출한다.
    
####학습목표:
- 메서드에 애노테이션을 붙여 활용하는 방법을 익힌다. 

# v2.5
####구현할 기능 및 산출물:
애노테이션을 이용하여 객체 생성을 관리한다.
- Component.java 애노테이션을 정의한다.
- ApplicationContext가 관리하는 대상 클래스에 대해 Component 애노케이션을 적용한다.
  - DAO에 적용
  - Controller에 적용. 단 Controller의 명령 문자열을 애노테이션에 등록한다.
    그리고 getCommandString() 메서드를 없앤다.
- AbstractCommand.java (변경)
  - getCommandString() 추상 메서드 제거
- ApplicationContext.java (변경)
  - @Component 가 붙은 클래스에 대해서만 객체를 생성한다.
    
####학습목표:
- 애노테이션의 용도를 이해한다.
- 애노테이션을 정의하고 적용할 수 있다. 

# v2.4
####구현할 기능 및 산출물:
빈 컨테이너 도입 : DAO, Controller 객체 관리를 자동화시킨다. 
- EduAppServer 클래스로부터 객체 관리 기능을 별도의 클래스로 분리한다.
  - ApplicationContext.java (생성)
  - ReflectionUtil 클래스와 ApplicationContext 클래스의 기능을 합친다.
  - ReflectionUtil.java (제거)
  - StudentDao, ContactDao 클래스 변경
    - 생성자 호출 시 load() 메서드를 호출하도록 바꾼다.
- EduAppServer 클래스는 ApplicationContext 클래스를 사용하여 객체를 다룬다.
  - EduAppServer.java (변경)
  - RequestThread.java (변경)
    
####학습목표:
- 빈 컨테이너의 목적을 이해한다.
- 빈 컨테이너의 내부 구동 원리를 이해한다.


# v2.3
####구현할 기능 및 산출물:
의존 객체 주입(Dependency Injection) 적용 
- DAO 클래스에 적용된 Singleton 패턴을 제거한다.
  - AbstractDao.java (변경)
    - 파일명 주입받기: setFilename() 추가 
    - 파일명 입력 받는 생성자 제거
  - ContactDao.java (변경)
    - 생성자 제거
    - Singleton 관련 스태틱 변수와 스태틱 메서드 제거
  - StudentDao.java (변경)
    - 생성자 제거
    - Singleton 관련 스태틱 변수와 스태틱 메서드 제거
- Controller 클래스에 DAO를 주입할 수 있도록 인스턴스 변수와 셋터를 추가한다.
  - ContactXxxController.java에 ContactDao 객체를 저장할 변수와 셋터 추가
  - StudentXxxController.java에 StudentDao 객체를 저장할 변수와 셋터 추가
- EduAppServer에서 DAO를 만들어 Controller에 주입한다.
  - EduAppServer.java (변경)
    
####학습목표:
- 의존 객체 주입을 이해한다.
- 리플랙션 API를 사용하여 셋터 메서드를 찾아서 의존 객체 주입할 수 있다.


# v2.2
####구현할 기능 및 산출물:
리플랙션 API를 활용하여 객체 생성 자동화하기 
- ReflectionUtil.java (추가)
  - AbstractCommand의 서브 클래스를 찾아 그 클래스 목록을 리턴한다.
- AbstractCommand.java (변경)
  - 명령어를 리턴하는 겟터 멧서드 추가. getCommandString()
- XxxController.java (변경)
  - 추상 메서드 getCommandString()을 구현 
- EduAppServer.java (변경)
  - ReflectionUtil 클래스를 사용하여 커맨드 객체 생성 자동화.
  
####학습목표:
- Reflection API의 용도를 이해한다.
- 객체 생성을 자동화하는 방법을 이해한다.
- 추상 메서드(getCommandString())의 용도를 이해한다.


# v2.1
####구현할 기능 및 산출물:
추상 클래스의 활용
- AbstractCommand.java (추가)
  - Command 구현체를 추상클래스로 만든다.
  - 이 구현체에서 service()를 구현한다.
    예외 처리 코드를 미리 넣는다.
  - 서브 클래스에서는 이 클래스가 제공하는 다른 메서드를 오버라이딩 한다.
  - 이유? XxxController가 반복적으로 하는 예외 처리 코드를 없애기 위해.
  
####학습목표:
- 인터페이스를 구현하는데 추상 클래스 문법을 활용할 수 있다.


# v2.0
####구현할 기능 및 산출물:
상속의 일반화(generalization) 기법 적용
- AbstractDao.java (추가)
  - ContactDao와 StudentDao의 공통 멤버를 추출하여 상위 클래스 정의
  - ContactDao와 StudentDao는 AbstractDao를 상속 받는다.
- ContactXxxController와 StudentXxxController 변경
  - ContactDao와 StudentDao 변경 사항에 맞춰 호출 코드 변경
  
####학습목표:
- 상속의 generalization을 이해하고 적용할 수 있다.
- 추상 클래스를 적용할 수 있다.
- try...catch...의 동작원리를 이해한다.
- 예외를 처리하거나 상위 호출자에게 예외를 던질 수 있다.

# v1.8
####구현할 기능 및 산출물:
command 패턴 적용 
- Command.java 인터페이스 정의
  - 클라이언트 요청을 들어왔을 때 EduAppServer가 호출하는 메서드 규칙
  - 클라이언트 요청을 처리하는 클래스는 반드시 이 규칙을 따라야 한다. 
- ContactController.java에 Command 패턴 적용
  - ContactListController.java : doList()를 객체화
  - ContactViewController.java : doView()를 객체화
  - ContactAddController.java : doAdd()를 객체화
  - ContactUpdateController.java : doUpdate()를 객체화
  - ContactDeleteController.java : doDelete()를 객체화
- StudentController.java에 Command 패턴 적용
  - StudentListController.java : doList()를 객체화
  - StudentViewController.java : doView()를 객체화
  - StudentAddController.java : doAdd()를 객체화
  - StudentUpdateController.java : doUpdate()를 객체화
  - StudentDeleteController.java : doDelete()를 객체화

####학습목표:
- 커맨드 패턴 이해하며 또한 적용할 수 있다.
- 인터페이스의 용도를 이해한다.

# v1.7
####구현할 기능 및 산출물:
- step1 
  - EduAppServer.java.01
    - static 멤버 대신 instance 멤버로 전환
- step2
  - EduAppServer.java.02
    - 클라이언트의 요청을 처리하는 부분을 스레드로 분리
  - RequestThread.java (생성)  
- step3
  - StudentDao.java 생성
    - StudentController.java 에서 데이터 처리 부분을 분리
    - Singleton 패턴 적용
    - insert(), update(), delete() 메서드에 동기화 적용
  - ContactDao.java 생성
    - ContactController.java 에서 데이터 처리 부분을 분리
    - Singleton 패턴 적용
    - insert(), update(), delete() 메서드에 동기화 적용
  - Controller들이 DAO를 공유하도록 변경 

####학습목표:
- 자바의 다양한 문법을 활용하여 유지보수가 좋은 구조로 코드를 변경할 수 있다.
- DAO(Data Access Object) 객체의 역할을 이해한다.
- Singleton 패턴을 이해하고 사용할 수 있다.


# v1.6
####구현할 기능:
- 클라이언트/서버 구조로 아키텍처를 변경 

####산출물:
- bitcamp/java89/ems/server 패키지 생성
  - EduApp.java를 이 패키지로 옮긴다.
    이름을 EduAppServer.java로 변경한다.
- bitcamp/java89/ems/server/vo 패키지 생성
  - Classroom.java, Contact.java, Curriculum.java, 
    Student.java, TextBook.java를 이 패키지로 옮긴다.
- bitcamp/java89/ems/server/controller 패키지 생성
  - ClassroomController.java, ContactController.java, 
    CurriculumController.java, StudentController.java, 
    TextBookController.java를 이 패키지로 옮긴다.
- bitcamp/java89/ems/client 패키지 생성
  - EduAppClient.java (생성)

####학습목표:
- 소켓 프로그래밍 훈련
- 역할 별로 클래스를 나누어 패키지로 관리하는 방법 훈련 

# v1.5
####구현할 기능:
- 연락처 관리 기능 

####산출물:
- Student.java
- StudentController.java
- EduApp.java (변경)
- Contact.java (추가)
- ContactConroller.java (추가)

####학습목표:
- 직렬화 프로그래밍 및 유지보수 연습

# v1.4
####구현할 기능:
- 직렬화 방식을 사용하여 데이터를 저장하고 읽는다.

####산출물:
- Student.java (변경)
- EduApp.java
- XxxController.java (변경)

####학습목표:
- 직렬화 기법을 이용하여 인스턴스의 값을 저장하고 읽을 수 있다.

# v1.3
####구현할 기능:
- 파일 저장 기능 추가
- 실행 예:
~~~~
명령> quit
학생 정보가 변경되었습니다. 그래도 종료하시겠습니까?(y/n) y
학생 정보가 변경된 것을 취소하고 종료합니다.
Good bye!
명령> quit
학생 정보가 변경되었습니다. 그래도 종료하시겠습니까?(y/n) n
명령> save
저장하였습니다.
명령> quit
Good bye!
~~~~
####산출물:
- Student.java
- EduApp.java (변경)
- XxxController.java (변경)

####학습목표:
- File I/O 클래스를 사용하여 데이터 저장과 로딩을 할 수 있다.

# v1.2
####구현할 기능:
- 우리가 만든 LinkedList 대신 자바에서 제공하는 ArrayList 사용하여 데이터 목록을 다룬다.

####산출물:
- Student.java
- EduApp.java
- XxxController.java (변경)
- LinkedList.java (제거)

####학습목표:
- 자바에서 제공하는 ArrayList를 사용할 수 있다. 


# v1.1
####구현할 기능:
- LinkedList에 예외처리 코드 추가
- StudentController에서 예외 다루기

####산출물:
- Student.java
- EduApp.java
- XxxController.java (변경)
- LinkedList.java (변경)

####학습목표:
- 메서드에서 예외를 던질 수 있다.
- 메서드를 호출할 때, 메서드가 던지는 예외를 다룰 수 있다.
- 예외를 활용해 오류가 발생하더라도 시스템을 멈추지 않고 실행할 수 있다. 

# v1.0
####구현할 기능:
- LinkedList에 제네릭을 적용한다.
- LinkedList를 사용하는 모든 코드에서 타입 캐스팅을 제거한다.

####산출물:
- Student.java
- EduApp.java
- XxxController.java (변경)
- LinkedList.java (변경)

####학습목표:
- 제네릭을 클래스에 적용하고 제네릭이 적용된 클래스를 사용할 수 있다.


# v0.9
####구현할 기능:
- LinkedList 클래스를 만든다.
- LinkedList를 StudentController에 적용한다.

####산출물:
- Student.java
- EduApp.java
- XxxController.java (변경)
- Box.java (삭제)
- LinkedList.java (추가)

####학습목표:
- LinkedList를 만들 수 있다.
- LinkedList를 사용하여 데이터를 저장,조회,변경,삭제할 수 있다.


# v0.8
####구현할 기능:
- Linked List 기법을 이용하여 학생, 강사 등의 데이터를 관리한다.
- 일단 StudentController만 적용한다.

####산출물:
- Student.java
- EduApp.java
- XxxController.java (변경)
- Box.java (추가)

####학습목표:
- Linked List의 사용법을 익힌다.
- Linked List를 구현하고 프로젝트에 적용할 수 있다.


# v0.7
####구현할 기능:
- EduApp에 있던 학생관리 명령어 처리를
   학생관리 전문가인 StudentController로 이전한다.
- EduApp은 대신 메뉴 선택 기능을 추가한다.

####산출물:
- Student.java
- EduApp.java (변경)
- StudentController.java (변경)

####학습목표:
- 리팩토링(refactoring)을 통해 명령어 코드를 관련된 클래스로 분류시키기.
- 객체지향 설계 방법론 중에서 "응집력 높이기(hign cohesion)"을 이해한다.
   High Cohesion? 한 클래스는 한 가지 역할에 집중해야 한다.
   - 한 클래스가 여러 가지 일을 하면 클래스 코드가 커지고, 유지보수에 안좋다.
- 메서드나 변수에 대해 접근을 제어하는 방법과 이유를 이해한다.
  - StudentController의 doXxx() 메서드들을 private 으로 접근을 제한한다.
  - 이 클래스의 내부 변수들도 private으로 접근을 제한한다.


# v0.6
####구현할 기능:
- 삭제 기능 추가
~~~~
명령> delete
삭제할 학생의 아이디는? hong
hong 학생 정보를 삭제하였습니다.
명령>
명령> delete
삭제할 학생의 아이디는? hong2
hong이라는 학생이 없습니다.
명령>

- 변경 기능 추가
명령> update
변경할 학생의 아이디는? hong
암호? 1111
이름? 임꺽정
전화? 111-1212
이메일? leem@test.com
재직중(y/n)? y
태어난해? 1980
최종학교? 1
저장하시겠습니까(y/n)? y
저장하였습니다.
명령>
...
저장하시겠습니까(y/n)? n
변경을 취소하였습니다.
명령>
명령> update
변경할 학생의 아이디는? hong
hong이라는 학생이 없습니다.
명령>
~~~~
####산출물:
- Student.java
- EduApp.java (변경)
- StudentController.java (변경)

####학습목표:
- 삭제와 변경을 추가하여 CRUD(Create/Read/Update/Delete) 기본 기능을 완성한다.
- 기존의 코드에 새 기능을 추가하는 경험을 해본다.
   이것이 유지보수 개발업무이다.

# v0.5
####구현할 기능:
- StudentController 클래스를 확장해서 사용할 수 있도록,
   학생 목록을 스태틱 변수로 저장하지 말고, 인스턴스 변수로 저장한다.
- 그에 따라 명령어를 처리하는 메서드도 인스턴스 메서드로 전환한다.

####산출물:
- Student.java
- EduApp.java (변경)
- StudentController.java (변경)

####학습목표:
- 인스턴스 변수와 인스턴스 메서드의 사용법을 익힌다.
- 생성자의 용도를 다시 한 번 확인한다.

# v0.4
####구현할 기능:
- 소스 코드 리팩토링(refactoring)
   - 유지보수하기 좋게 소스 코드를 좀 더 객체지향적으로 정리한다.
- EduApp에 있는 Student에 대해 명령을 다루는 데 관련된 메서드를
   따로 다른 클래스로 분류한다.

####산출물:
- Student.java
- EduApp.java
- StudentController.java (추가)

####학습목표:
- 클래스 문법의 용도를 다시 한 번 익힌다.
   - 클래스는 특정 작업과 관련된 메서드를 관리하기 좋게 분류할 때 사용한다.


# v0.3
####구현할 기능:
- 사용자에게 명령어를 입력 받는 프롬프트 기능을 추가한다.
예) 프로그램을 시작시키면 다음과 같이 동작한다.
```
명령> add
아이디(:hong)? 1
암호(예:1111)? 1
이름(예:홍길동)? 1
전화(예:010-1111-2222)? 1
이메일(예:hong@test.com)? 1
재직중(y/n)? y
태어난해(예:1980)? 1980
최종학교(예:비트고등학교)? 1
계속 입력하시겠습니까(y/n)? n
명령> list
1,1,1,1,1,yes,1980,1
명령> view
학생 아이디? hong
----------------------------
아이디: 1
암호: 1
이름: 1
전화: 1
이메일: 1
재직중: y
태어난해: 1980
최종학교: 1
명령>
```
####산출물:

####학습목표:
- 반복문 중첩해서 사용하는 방법을 익힌다.
- 입력 코드를 별도의 메서드로 분리하여 유지보수 하기 좋은 코드를 만드는 것을 익힌다.


# v0.2
####구현할 기능:
- 여러 명의 학생 정보를 입력 받아 저장하기

####산출물:
- Student.java
- EduApp.java (코드 변경)

####학습목표:
- 배열을 사용하여 여러 개의 인스턴스를 다루는 방법을 익힌다.
- 여러 번 입력 받기 위해 반복문을 사용하는 방법을 익힌다.
- 인스턴스와 레퍼런스의 관계를 이해한다.
- 배열 레퍼런스 변수와 배열 인스턴스 객체를 이해한다.
- 여러 명의 학생 정보를 출력하기 위해
   한 학생 정보를 출력하는 코드를 별도의 메서드로 분리한다.
   메서드의 용도와 사용법을 익힌다.

# v0.1
####구현할 기능:
- 학생 정보를 다룰 때 사용할 Student 데이터 타입을 정의한다.
- 학생 정보를 입력 받아 출력해본다.

####산출물:
- Student.java
- EduApp.java

####학습목표:
- Class 문법을 사용하여 사용자 정의 데이터 타입을 만드는 방법을 익힌다.
- 키보드를 통해 사용자로부터 입력 받는 방법을 배운다.
- 인스턴스를 생성하고 인스턴스에 값을 넣는 방법을 익힌다.
- 문자열 데이터를 int, boolean 등 다른 타입의 값으로 바꾸는 방법을 배운다.
