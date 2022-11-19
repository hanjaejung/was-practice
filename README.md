# was-practice
소켓통신(톰캣을 직접 생성)을 이용해 메인Thread가 사용자 요청을 처리

﻿
강의내용을 보고 정리한 내용입니다.

소켓통신(톰캣을 직접 생성,was를 직접 생성)으로 서버가 클라이언트의 요청을 받아 queryString 에 있는 값으로 사칙연산을 만들었습니다.


http프로토콜의 규칙에 맞게 httpRequest를 만들고

httpResponse 규칙에 맞게끔 클라이언트의 요청을 전달해주는 프로그램을 만들었습니다.


예) 서버가 가동되는 상태에서

클라이언트가 http://localhost:8080/calculate?operand1=10&operator=-&operand2=55 이런 요청을 보낼 시


operand1 이 첫번쨰 값

operator이 연산자 구분

operand2가 두번쨰 값으로 설정하여


10-55 의 결과값으로 -45가 나오게 하였습니다.


먼저 CustomWebApplicationServer 클래스에서 서버 구동시

ServerSocket serverSocket = new ServerSocket(port)

서버 소켓 객체 생성


try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);
